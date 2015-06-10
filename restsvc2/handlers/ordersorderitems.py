# -*- coding: utf-8 -*-

import json
from base import BaseHandler
from tornado.escape import json_encode
from tornado.httpclient import HTTPError
from utils import admin_required, login_required

class OrdersOrderitemsHandler(BaseHandler):

	# get https://api_base/api/v1/orders/<int:orderid>/orderitems
	@login_required
	def get(self, orderid, orderitems=True):
		cursor = self.conn.cursor()
		try:         # a tborders, b tborderitems
			cursor.execute("select b.nid, b.ngood_id, b.ncount \
							from tborders as a, \
							tborderitems as b \
							where b.norder_id = a.nid \
							and a.nuser_id={0}".format(self.current_user.get("nid")))
		except:
			raise HTTPError(500)

		if cursor.rowcount > 0:
			orderitems = cursor.fetchall()
			cursor.close()
			orderitems_json = [{'nid':orderitem[0],
								'ngood_id':orderitem[1],
								'ncount':orderitem[2]}
								for orderitem in orderitems]
			# return self.write(json_encode({
			# 		'orderitems':orderitems_json
			# 	}))
			return self.write(json.dumps(orderitems_json))
		else:
			# return self.write(json_encode({
			# 		'orderitems': None
			# 	}))
			return self.write(json.dumps([]))


class OrdersOrderitemHandler(BaseHandler):

	# get https://api_base/api/v1/orders/<int:orderid>/orderitems/<int:orderitemid>
	@login_required
	def get(self, orderid, orderitems, orderitemid):
		cursor = self.conn.cursor()
		try:         # a tborders, b tborderitems
			cursor.execute("select b.nid, b.ngood_id, b.ncount \
							from tborders as a, \
							tborderitems as b \
							where b.norder_id=a.nid \
							and a.user_id={0} and b.nid={1}".format(self.current_user.get("nid"), orderitemid))
		except:
			raise HTTPError(500)
		if cursor.rowcount > 0:
			orderitems = cursor.fetchone()
			cursor.clone()
			return self.write(json_encode({
						'nid':orderitem[0],
						'ngood_id':orderitem[1],
						'ncount':orderitem[2]
					}))
		else:
			# return self.write(json_encode({
			# 		'orderitem': None
			# 	}))
			return self.write(json.dumps([]))