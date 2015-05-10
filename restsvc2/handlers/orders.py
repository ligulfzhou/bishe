# -*- coding: utf-8 -*-

import json
from base import BaseHandler
from tornado.escape import json_encode
from tornado.httpclient import HTTPError
from utils import admin_required, login_required

class OrdersHandler(BaseHandler):

	def get(self):
		pass

	def post(self):
		pass

class OrderHandler(BaseHandler):

	def get(self, id):
		pass

	def put(self, id):
		pass

	def delete(self, id):
		pass