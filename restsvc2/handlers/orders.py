# -*- coding: utf-8 -*-

import json
import time
from base import BaseHandler
from tornado.escape import json_encode
from tornado.httpclient import HTTPError
from utils import admin_required, login_required

class OrdersHandler(BaseHandler):

    @login_required
    def get(self):
        userid = self.current_user['nid']
        cursor = self.conn.cursor()
        try:
            cursor.execute("select nid, dcreate_at, nuser_id, dtotal, nhandlered \
                from tborders where nuser_id={0}".format(userid))
        except:
            raise HTTPError(500)
        if cursor.rowcount > 0:
            orders = cursor.fetchall()
            orders_json = [{'nid':order[0], 
                            'dcreate_at':time.mktime(order[1].timetuple()),
                            'nuser_id':order[2],
                            'dtotal':order[3],
                            'nhandlered':order[4]} 
                            for order in orders]
            # return self.write(json_encode({
            #   'orders':orders_json
            #   }))
            return self.write(json.dumps(orders_json))
        else:
            # self.set_status(404)
            # return self.write(json_encode({
            #   'orders':None
            #   }))
            return self.write(json.dumps([]))


    @login_required
    def post(self):
        '''
        orderinfo {
            'dtotal':
            'orderitems': [
                'ngood_id'
                'ncount':
            ]
        }
        dtotal maybe fake,
        omit the dtotal,

        just post [{ngoodid, ncount}, {ngoodid, ncount}, {ngoodid, ncount}]
        '''
        print self.request.body + "]}"
        order_json = json.loads(self.request.body + "]}")

        # dtotal = order_json.get("dtotal")
        orderitems = order_json.get("orderitems")

        # print dtotal
        print orderitems

        # print orderitems[0], "hello world"
        userid = self.current_user['nid']     # current user id

        # for orderitem in orderitems:
        #   print orderitem.get("ngoodid"), orderitem.get("ncount")

        # first insert to the order table,
        # set dtotal = 0 first
        cursor = self.conn.cursor()
        try:
            cursor.execute("insert into tborders (nuser_id, dtotal) values ({0}, {1}) \
                returning nid".format(userid, 0))
        except:
            raise HTTPError(500)
        orderid = cursor.fetchone()[0]
        self.conn.commit()

        # add orderitems to tborderitems
        # and calculate the total cost
        dtotal = 0
        for orderitem in orderitems:
            # print orderitem.get("ngoodid"), orderitem.get("ncount")

            try:
                cursor.execute("select dprice from tbgoods where nid={0}".format(\
                                orderitem.get("ngoodid")))
                dprice = cursor.fetchone()[0]
                dtotal += dprice * orderitem.get("ncount")

            except:
                raise HTTPError(500)

            try:
                cursor.execute("insert into tborderitems (ngood_id, ncount, norder_id) \
                                values ({0}, {1}, {2})".format(orderitem.get("ngoodid"),\
                                orderitem.get("ncount"), orderid))
            except:
                raise HTTPError(500)

        try:
            cursor.execute("update tborders set dtotal={0} where nid={1}".format(dtotal, orderid))
        except:
            raise HTTPError(500)

        self.conn.commit()
        cursor.close()

        # maybe should return the order info struct, but now, it is just empty
        return


class OrderHandler(BaseHandler):

    @login_required
    def get(self, id):
        if self.current_user['nrole'] == 0:     # user role
            userid = self.current_user['nid']
            cursor = self.conn.cursor()
            try:
                cursor.execute("select nid, dcreate_at, nuser_id, dtotal, nhandlered \
                    from tborders where nid={0}".format(id))
            except:
                raise HTTPError(500)

            orderinfo = cursor.fetchone()
            cursor.close()

            if orderinfo['nuser_id'] != userid:
                raise HTTPError(403)

            return self.write(json_encode({
                    'nid':orderinfo[0],
                    'dcreate_at':time.mktime(orderinfo[1].timetuple()),
                    'nuser_id':orderinfo[2],
                    'dtotal':orderinfo[3],
                    'nhandlered':orderinfo[4]
                }))
        else:
            try:
                cursor.execute("select nid, dcreate_at, nuser_id, dtotal, nhandlered \
                    from tborders where nid={0}".format(id))
            except:
                raise HTTPError(500)
            orderinfo = cursor.fetchone()
            cursor.close()

            return self.write(json_encode({
                    'nid':orderinfo[0],
                    'dcreate_at':time.mktime(orderinfo[1].timetuple()),
                    'nuser_id':orderinfo[2],
                    'dtotal':orderinfo[3],
                    'nhandlered':orderinfo[4]
                }))

    '''
    @login_required
    def put(self, id):
        #if the order is not handler,then the operation is permitted
        #   1: user: change the order info(eg: orderitems)
        #   2: admin: change the order status(change status from unhandlered to handlered)
        userid = self.current_user['nid']
        cursor = self.conn.cursor()
        try:
            cursor.execute("select nid, ncreate_at, nuser_id, dtotal, nhandlered \
                from tborders where nid={0}".format(id))
        except:
            raise HTTPError(500)
        if cursor.rowcount > 0:
            orderinfo = cursor.fetchone()
            if orderinfo[4] == 1:           # already handler
                raise HTTPError(400, 'bad request')
            else:
                if self.current_user['nrole'] == 0:     # user role
                    if orderinfo[3] != userid:
                        raise HTTPError(403)
                    order = json.loads(self.request.body)
    '''

    @login_required
    def delete(self, id):

        userid = self.current_user['nid']
        cursor = self.conn.cursor()
        try:
            cursor.execute("select nid, ncreate_at, nuser_id, dtotal, nhandlered \
                from tborders where nid={0}".format(id))
        except:
            raise HTTPError(500)
        if cursor.rowcount > 0:
            orderinfo = cursor.fetchone()
            if orderinfo[4] == 1:           # already handler
                raise HTTPError(400, 'bad request')
            else:
                if userid != orderinfo[2]:
                    raise HTTPError(403)
                else:
                    try:
                        cursor.execute("delete from tborders where nid={0}".format(id))
                    except:
                        raise HTTPError(500)
                    self.conn.commit()
                    cursor.close()

        return
