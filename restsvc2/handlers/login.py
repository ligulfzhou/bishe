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
		#userinfo_json = json.loads(self.request.body)
		#userinfo = userinfo_json.get("user")
		# userinfo = json.loads(self.request.body)
		# username = userinfo.get("username")
		# password = userinfo.get("password")

		username = self.get_argument('username', "lalala")
		password = self.get_argument('password', "lalalalala")
		print username, password

		body = self.request.body
		print body
		cursor = self.conn.cursor()
		try:
			cursor.execute("select nid, cname, dcreate_at, nrole from tbusers \
				where cname='{0}' and cpassword='{1}'".format(username, password))
		except:
			raise HTTPError(500, "db error")
		if cursor.rowcount > 0:
			user = cursor.fetchone()
			cursor.close()
			return self.write(json_encode({
				"user":{
					'nid' : user[0],
					'cname' : user[1],
					'dcreate_at' : time.mktime(user[2].timetuple()), 
					'nrole' : user[3],
					'token' : base64.encodestring("{0}:{1}".format(username, password))}
				}))
		else:
			raise HTTPError(401, 'un authenrized')