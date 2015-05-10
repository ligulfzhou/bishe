# -*- coding: utf-8 -*-

import json
from base import BaseHandler
from tornado.escape import json_encode
from tornado.httpclient import HTTPError
from utils import admin_required, login_required


class CustomersHandler:

	def post(self):
		customer = json.loads(self.request.body)
		username = customer.get("username")
		password = customer.get("password")
		if username is not None and password is not None:
			cursor = self.conn.cursor()
			'''
			try:
				cursor.execute("select nid from customer where cname='{0}' \
					and cpassword='{1}'".format(username, password))
			except:
				raise HTTPError(500, "db error")
			'''
			try:
				cursor.execute("insert into customer (cname, cpassword) \
					values ('{0}', '{1}') returning nid".format(username, password))
			except:
				raise HTTPError(500, "db error")
			if cursor.rowcount > 0:
				nid = cursor.fetchone()
				pass


class CustomerHandler(BaseHandler):

	def get(self, id):
		pass

	def put(self, id):
		pass

	def delete(self, id):
		pass