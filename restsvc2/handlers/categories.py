# -*- coding: utf-8 -*-

import json
from base import BaseHandler
from tornado.escape import json_encode
from tornado.httpclient import HTTPError
from utils import admin_required, login_required

class CategoriesHandler(BaseHandler):

	def get(self):
		cursor = self.conn.cursor()
		try:
			cursor.execute("select nid, cname from tbcategories")
		except:
			raise HTTPError(500, "db error")
		categories = cursor.fetchall()
		categories_json = [{"nid":category[0], "cname":category[1]} for category in categories]
		cursor.close()
		return self.write(json_encode({
				"categories":categories_json
			}))

	@admin_required
	def post(self):
		#category_json = json.loads(self.request.body)
		#category = category_json.get("category")
		category = json.loads(self.request.body)
		cname = category.get('cname')

		cursor = self.conn.cursor()
		try:
			cursor.execute("insert into tbcategories (cname) values ('{0}')".format(cname))
		except:
			raise HTTPError(500)
		self.conn.commit()
		cursor.close()
		return 


class CategoryHandler(BaseHandler):

	def get(self, id):
		cursor = self.conn.cursor()
		try:
			cursor.execute("select nid, cname from tbcategories where nid={0}".format(id))
		except:
			raise HTTPError(500, "db error")
		if cursor.rowcount > 0:
			category = cursor.fetchone()
			cursor.close()
			return self.write(json_encode({
				"category":{
					"id":category[0],
					"cname":category[1]}
				}))

	@admin_required
	def put(self, id):
		#category_json = json.loads(self.request.body)
		#category = category_json.get('category')
		
		category = json.loads(self.request.body)
		cname = category.get('cname')
		nid = category.get('nid')
		cursor = self.conn.cursor()

		'''should check the id is available'''
		try:
			cursor.execute("update tbcategories set cname='{0}' where nid={1}".format(cname, nid))
		except:
			raise HTTPError(500, "db error")
		self.conn.commit()
		cursor.close()
		return

	@admin_required
	def delete(self, id):
		cursor = self.conn.cursor()
		''' check the id first? '''
		try:
			cursor.execute("delete from tbcategories where nid={0}".format(id))
		except:
			raise HTTPError(500, "db error")
		self.conn.commit()
		cursor.close()
		return