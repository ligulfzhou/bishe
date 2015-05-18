# -*- coding: utf-8 -*-

import json
from base import BaseHandler
from tornado.escape import json_encode
from tornado.httpclient import HTTPError
from utils import admin_required, login_required

class OrdersOrderitemsHandler(BaseHandler):

	@login_required
	def get(self, orderid, orderitems=True):
		pass

class OrdersOrderitemHandler(BaseHandler):

	@login_required
	def get(self, orderid, orderitems, orderitemid):
		pass