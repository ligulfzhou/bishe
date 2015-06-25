# -*- coding: utf-8 -*-

import json
from base import BaseHandler
from tornado.options import options
from tornado.escape import json_encode
from tornado.httpclient import HTTPError
from utils import admin_required, login_required

class FavoritesHandler(BaseHandler):

    @login_required
    def get(self):
        '''
        get favorite goods, return goods list
        '''
        # return self.write("hello world:{0}".format(self.current_user.get("nid")))
        userid = self.current_user['nid']
        cursor = self.conn.cursor()
        try:
            cursor.execute("select a.nid, a.cname, a.dprice, a.cdesc, a.ncategoryid, a.ncount \
                            from tbgoods as a, tbfavorites as b \
                            where a.nid=b.ngood_id and b.nuser_id={0}".format(userid))
        except:
            raise HTTPError(500)

        if cursor.rowcount > 0:
            goods = cursor.fetchall()
            cursor.close()
            goods_json = [{"nid":good[0],
                            "cname": good[1],
                            "dprice": good[2],
                            "cdesc": good[3],
                            "ncategoryid": good[4],
                            "count": good[5]}
                            for good in goods]
            return self.write(json.dumps(goods_json))
        else:
            return self.write(json.dumps([]))

    @login_required
    def post(self):
        goodid = self.get_argument('goodid', 0)
        if goodid == 0:
            raise HTTPError(501)

        print goodid
        userid = self.current_user['nid']

        cursor = self.conn.cursor()
        try:
            cursor.execute("insert into tbfavorites (ngood_id, nuser_id) \
                            values ({0}, {1})".format(goodid, userid))
        except:
            raise HTTPError(500)

        self.conn.commit()

        self.finish()
        return

class FavoriteHandler(BaseHandler):

    @login_required
    def delete(self, id):
        pass