# -*- coding: utf-8 -*-

from base import BaseHandler
from tornado.escape import json_encode
from tornado.httpclient import HTTPError

class CategorysHandler(BaseHandler):

	def get(self):
		cursor = self.conn.cursor()
		try:
			cursor.execute("select nid, nname from categorys")
		except:
			raise HTTPError(500, "db error")
		categorys = cursor.fetchall()
		categorys_json = [{"nid":category[0], "cname":category[1]} for category in categorys]
		cursor.close()
		return json_encode({
				"categorys":categorys_json
			})

	#def post(self):
	#	pass

class CategoryHandler(BaseHandler):

	def get(self, id):
		cursor = self.conn.cursor()
		try:
			cursor.execute("select nid, cname, ncount from categorys where nid={0}".format(id))
		except:
			raise HTTPError(500, "db error")
		if cursor.rowcount > 0:
			category = cursor.fetchone()
			cursor.close()
			return self.write(json_encode({
				"id":category[0],
				"cname":category[1]
				"ncount":category[2]
				}))

	def put(self, id):
		category = json.loads(self.request.body)
		cname, ncount = category
		cursor = self.conn.cursor()

		'''should check the id is available'''
		try:
			cursor.execute("update category set cname='{0}' and ncount='{1}'".format(cname, ncount))
		except:
			raise HTTPError(500, "db error")
		self.conn.commit()
		cursor.close()

	def delete(self, id):
		pass