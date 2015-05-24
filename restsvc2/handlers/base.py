# -*- coding: utf-8 -*-

from tornado.httpclient import HTTPError
import tornado.web
import base64

class BaseHandler(tornado.web.RequestHandler):

	@property
	def conn(self):
		return self.application.db

	# def set_default_header(self):
	# 	self.add_header("Access-Control-Expose-Headers", "*")

	def get_current_user(self):
		auth_header = self.request.headers.get('Authorization')

		if auth_header is None or not auth_header.startswith('Basic '):
			return None
		auth_decoded = base64.decodestring(auth_header[6:])
		email, password = auth_decoded.split(':', 2)
		cursor = self.conn.cursor()
		try:
			cursor.execute("select nid, cemail, cname, nrole from tbusers \
				where cemail='{0}' and cpassword='{1}'".format(email, password))
		except:
			raise HTTPError(500, "db error")
		if cursor.rowcount > 0:
			userinfo = cursor.fetchone()
			cursor.close()
			return {"nid":userinfo[0],
					"cemail":userinfo[1],
					"cname":userinfo[2],
					"nrole":userinfo[3]}
		else:
			return None