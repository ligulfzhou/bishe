# -*- coding: utf-8 -*-

import json
from base import BaseHandler
from tornado.escape import json_encode
from tornado.httpclient import HTTPError
from utils import admin_required, login_required


class CustomersHandler(BaseHandler):

	def get(self):
		cursor = self.conn.cursor()
		try:
			cursor.execute("select nid, cname from customers")
		except:
			raise HTTPError(500, "db error")
		count = cursor.rowcount
		customers = cursor.fetchall()
		cursor.close()
		json_customers = [{"nid":customer[0], "cname":customer[1]} for customer in customers]
		return json_encode({
			"customers":json_customers
			})

	def post(self):
		pass

class CustomerHandler(BaseHandler):

	def get(self, id):
		pass

	def put(self, id):
		pass

	def delete(self, id):
		pass

