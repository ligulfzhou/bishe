# -*- coding: utf-8 -*-

import json
from base import BaseHandler
from tornado.escape import json_encode
from tornado.httpclient import HTTPError
from utils import admin_required, login_required

class UsersOrdersHandler(BaseHandler):

    @admin_required
    def get(self, id, orders=True):
        userid = self.current_user['nid']
        cursor = self.conn.cursor()
        try:
            cursor.execute("select nid, dcreate_at, dtotal from tborders where nuser_id={0}".format(id))
        except:
            raise HTTPError(500)
        if cursor.rowcount > 0:
            orders = cursor.fetchall()
            orders_json = [{'nid':order[0], 'dcreate_at':time.mktime(order[1].timetuple()),'dtotal':order[2]} for order in orders]
            # return self.write(json_encode({
            #   'orders':orders_json
            #   }))
            return self.write(json.dumps(orders_json))
        else:
            # return self.write(json_encode({
            #   'orders':None
            #   }))
            return self.write(json.dumps([]))