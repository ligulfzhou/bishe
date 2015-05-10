# -*- coding: utf-8 -*-

import json
import base64
from base import BaseHandler
from tornado.escape import json_encode
from tornado.httpclient import HTTPError
from utils import admin_required, login_required

class LoginHandler(BaseHandler):

	def post(self):
		userinfo = json.loads(self.request.body)
		username = userinfo.get("username")
		password = userinfo.get("password")

		cursor = self.conn.cursor()
		try:
			cursor.execute("select nid from customer where cname='{0}' and cpassword='{1}'".format(username, password))
		except:
			raise HTTPError(500, "db error")
		if cursor.rowcount > 0:
			return self.write(json_encode({
					"token" : base64.encodestring("{0}:{1}".format(username, password))
				}))
		else:
			raise httpclient(401, 'un authenrized')