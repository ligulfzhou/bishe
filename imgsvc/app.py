import os.path
import hashlib
import tornado.httpserver
import tornado.ioloop
import tornado.options
import tornado.web
import tornado.gen
import tornado.escape
from tornado.gen import coroutine
from tornado.options import define, options

from fdfsclient import fdfsclient
import psycopg2
import momoko
import time
from PIL import Image
import cStringIO, io

import logging
logging.basicConfig(filename='log.log',level=logging.DEBUG)

define('port', default=8888, help='run on the given port', type=int)
define('dbname', default=os.environ.get('DBNAME') or 'image', help='db name')
define('dbuser', default=os.environ.get('DBUSER') or 'postgres', help='db user')
define('dbpassword', default=os.environ.get('DBPASSWORD') or 'postgres', help='db password')
define('dbhost',default=os.environ.get('DBHOST') or 'localhost', help='db host')

class Application(tornado.web.Application):
    def __init__(self):
        handlers = [
            (r"/uploadimage", UploadImageHandler),
            (r"/queryimage", QueryImageHandler),
            (r"/downloadimage", DownloadImageHandler),
        ]
        settings = dict(
            template_path=os.path.join(os.path.dirname(__file__), "templates"),
            static_path=os.path.join(os.path.dirname(__file__), "static"),
            debug=True
        )
        tornado.web.Application.__init__(self, handlers, **settings)

        # Have one global connection to the blog DB across all handlers
        self.db = momoko.Pool(
                dsn='dbname=%s user=%s password=%s host=%s' \
                    % (options.dbname, options.dbuser, options.dbpassword, options.dbhost),
                size=1
            )
        self.salt = 'helloworld'


class BaseHandler(tornado.web.RequestHandler):
    @property
    def db(self):
        return self.application.db


class UploadImageHandler(BaseHandler):
    @coroutine
    def post(self):
        imgid   = self.get_argument('md5', '')
        sign    = self.get_argument('sign', '')
        img_content = self.request.body

        img_size    = len(img_content)
        if img_size > 1024000:
            self.set_status(403)
            logging.info('%s : upload file size is beyond limit: %s' % (time.ctime(), img_size))
            return

        #print self.request.body
        imgid_uploaded  = hashlib.md5(img_content).hexdigest()
        chk_sign        = hashlib.md5(imgid + self.application.salt).hexdigest()

        if imgid_uploaded != imgid or sign != chk_sign:
            self.set_status(403)
            logging.info('%s : %s imgid or sign check error' % (time.ctime(), imgid))
            
            json_data = {
                'error' : 'fail to imgid or sign check'
            }
            self.set_header('Content-Type', 'text/javascript')
            return self.write(tornado.escape.json_encode(json_data))

        else:
            # build the image class to get some statistic
            stringio        = cStringIO.StringIO(img_content)  
            img_build       = Image.open(stringio)
            length, width   = img_build.size
            img_format      = img_build.format

            try:
                ret = fdfsclient.upload_by_buffer(img_content)
            except:
                self.set_status(403)
                logging.info('%s : upload error' % (time.ctime()))
                return

            else:
                remote_file_id = ret['Remote file_id']
                try:
                    cursor = yield momoko.Op(self.db.execute, "select size, length, width, fmat from image where imgid = '%s'" % imgid)
                except:
                    self.set_status(500)
                    logging.info('%s : when check image is already exist, database lookup error' % (time.ctime()))
                    return
                else:
                    img_ft = cursor.fetchone()
                    try:
                        size, length, width, fmat = img_ft
                    except:
                        # upload success, then put the info into the table
                        try:
                            cursor = yield momoko.Op(self.db.execute, "insert into image(imgid,name,remote_file_id,size,length,width,fmat) values ('%s', '%s', '%s', %d, %d, %d,'%s')" % \
                                (imgid_uploaded, '', remote_file_id, img_size, length, width, img_format) )  
                        except:
                            self.set_status(403)

                            logging.info('%s : upload -- when insert into image table, error ocurs' % (time.ctime()))

                            json_data = {
                                'error' : 'insert to database error'
                            }
                            self.set_header('Content-Type', 'text/javascript')
                            return self.write(tornado.escape.json_encode(json_data))
                        else:
                            logging.info('%s : image: %s upload success,remote_file_id: %s' % (time.ctime(), '', remote_file_id))
                            json_data = {
                                'success' : 'image upload success'
                            }
                            self.set_header('Content-Type', 'text/javascript')
                            return self.write(tornado.escape.json_encode(json_data))
                    else:
                        logging.info('%s : image already exist' % (time.ctime()))
                        json_data = {
                            'success' : 'image upload success'
                        }
                        self.set_header('Content-Type', 'text/javascript')
                        return self.write(tornado.escape.json_encode(json_data))


class QueryImageHandler(BaseHandler):
    @coroutine
    def get(self):
        imgid           = self.get_argument('md5', '')
        sign            = self.get_argument('sign', '')
        if sign != hashlib.md5(imgid + self.application.salt).hexdigest():
            self.write('forbidden')
            self.set_status(403)
            self.finish()
        else:
            try:
                cursor = yield momoko.Op(self.db.execute, "select size, length, width, fmat from image where imgid = '%s'" % (imgid) )
            except (psycopg2.Warning, psycopg2.Error) as error:
                self.write(str(error))

            try:
                img     = cursor.fetchone()
                size, length, width, fmat = img
            except:
                json_data = {
                    'imgid' : imgid,
                    'result' : 'not found'
                }
                return self.write(tornado.escape.json_encode(json_data))
            else:
                json_data = {
                    'size' : size,
                    'length':length,
                    'width':width,
                    'fmat':fmat
                }
            self.set_header('Content-Type', 'text/javascript')
            return self.write(tornado.escape.json_encode(json_data))


class DownloadImageHandler(BaseHandler):
    @tornado.gen.coroutine
    def get(self):
        imgid   = self.get_argument('md5', '')
        sign    = self.get_argument('sign', '')
        
        if sign != hashlib.md5(imgid + self.application.salt).hexdigest():
            self.set_status(403)
            return self.write('forbidden')
        else:
            try:
                cursor = yield momoko.Op(self.db.execute, "select remote_file_id, fmat from image where imgid='%s'" % (imgid, ))
            except (psycopg2.Warning, psycopg2.Error) as error:
                self.set_status(500)
                logging.info('%s : download image: when lookup table, error ocurs' % (time.ctime()))
                return 
            try:
                img     = cursor.fetchone()
                remote_file_id, fmat = img
                ret = fdfsclient.download_to_buffer(remote_file_id)
            except:
                self.set_status(500)
                logging.info('%s : fdfs_client download image error' % (time.ctime()))
                return
            else:
                img_build = Image.open(cStringIO.StringIO(ret['Content']))
                o = io.BytesIO()
                img_build.save(o, format=fmat)
                s = o.getvalue()          
                self.set_header('Content-Type', 'image/%s' % fmat)
                self.set_header('Content-length', len(s))
                return self.write(s)


def main():
    tornado.options.parse_command_line()
    http_server = tornado.httpserver.HTTPServer(Application())
    http_server.listen(options.port)
    tornado.ioloop.IOLoop.instance().start()


if __name__ == "__main__":
    main()
