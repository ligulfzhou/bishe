# -*- coding: utf-8 -*-

import json
from base import BaseHandler
from tornado.escape import json_encode
from tornado.httpclient import HTTPError
from utils import admin_required, login_required

class CategoriedGoodsHandler(BaseHandler):

	def get(self, id, goods=True):
		cursor = self.conn.cursor()
		try:
			cursor.execute("select nid, cname, fprice, cdesc, ncategoryid, ncount \
				from tbgoods where ncategoryid={0}".format(id))
		except:
			raise HTTPError(500)
		goods = cursor.fetchall()
		goods_json = [{
			'nid':good[0],
			'cname':good[1],
			'fprice':good[2],
			'cdesc':good[3],
			'ncategoryid':good[4],
			'ncount':good[5]} 
			for good in goods]
		return self.write(json_encode({
			'goods':goods_json
			}))
'''
	@admin_required
	def post(self, id, goods=True):
		good = json.loads(self.request.body)
		cname = good.get('cname')
		fprice = good.get('fprice')
		cdesc = good.get('cdesc')
		ncategoryid = good.get('ncategoryid')
		nount = goods_json.get('ncount')

		cursor = self.conn.cursor()
		# check available is omitted 
		try:
			cursor.execute("insert into tbgoods (cname, fprice, cdesc, ncategoryid, ncount) \
				values ('{0}', {1}, '{2}', {3}, {4}) returning nid".format(cname, fprice, cdesc, ncategoryid, ncount))
		except:
			raise HTTPError(500)
		nid = cursor.fetchone()
		self.conn.commit()
		try:
			cursor.execute("select nid, cname, fprice, cdesc, ncategoryid, ncount \
				from tbgoods where nid={0}".format(nid))
		except:
			raise HTTPError(500)
		if cursor.rowcount > 0:
			goodinfo = cursor.fetchone()
			cursor.close()
			return self.write(json_encode({
				'nid':goodinfo[0],
				'cname':goodinfo[1],
				'fprice':goodinfo[2],
				'cdesc':goodinfo[3],
				'ncategoryid':goodinfo[4],
				'ncount':goodinfo[5]
				}))
'''

'''
class GoodHandler(BaseHandler):

	def get(self, id1, goods=True, id2):
		cursor = self.conn.cursor()
		try:
			cursor.execute("select nid, cname, fprice, cdesc, ncategoryid, ncount \
				from tbgoods where nid={0}".format(id))
		except:
			raise HTTPError(500)
		goodinfo = cursor.fetchone()
		cursor.close()
		return self.write(json_encode({
			'nid':good[0],
			'cname':good[1],
			'fprice':good[2],
			'cdesc':good[3],
			'ncategoryid':good[4],
			'nount':good[5]} 
			}))
'''
