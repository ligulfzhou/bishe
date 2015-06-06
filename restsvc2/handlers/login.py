# -*- coding: utf-8 -*-

import json
import time
import base64
from base import BaseHandler
from tornado.escape import json_encode
from tornado.httpclient import HTTPError
from utils import admin_required, login_required

class LoginHandler(BaseHandler):

	def post(self):
		# userinfo_json = json.loads(self.request.body)
		# userinfo = userinfo_json.get("user")
		# userinfo = json.loads(self.request.body)
		# print userinfo
		# email = userinfo_json.get("email")
		# password = userinfo_json.get("password")

		email = self.get_argument('email', "lalala")
		password = self.get_argument('password', "lalalalala")

		cursor = self.conn.cursor()
		try:
			cursor.execute("select nid, cemail, cname, dcreate_at, nrole from tbusers \
				where cemail='{0}' and cpassword='{1}'".format(email, password))
		except:
			raise HTTPError(500, "db error")
		if cursor.rowcount > 0:
			user = cursor.fetchone()
			cursor.close()
			return self.write(json_encode({
					'nid' : user[0],
					'cemail' : user[1],
					'cname' : user[2],
					'dcreate_at' : time.mktime(user[3].timetuple()), 
					'nrole' : user[4],
					'token' : base64.encodestring("{0}:{1}".format(email, password))
				}))
		else:
			raise HTTPError(401, 'un authenrized')
