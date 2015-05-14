# -*- coding: utf-8 -*-

import time
import json
from base import BaseHandler
from tornado.escape import json_encode
from tornado.httpclient import HTTPError
from utils import admin_required, login_required


class UsersHandler(BaseHandler):

	@admin_required
	def get(self):
		cursor = self.conn.cursor()
		try:
			cursor.execute("select nid, cname, dcreate_at, nrole from tbusers")
		except:
			raise HTTPError(500)
		users = cursor.fetchall()
		users_json = [{'nid':user[0], 
						'cname':user[1], 
						#'dcreate_at':user[2],
						'dcreate_at':time.mktime(user[2].timetuple()),
						'nrole':user[3]} for user in users]
		return self.write(json_encode({
			'users':users_json
			}))


	''' register user '''
	def post(self):
		user = json.loads(self.request.body)
		username = user.get("username")
		password = user.get("password")
		if username is not None and password is not None:
			cursor = self.conn.cursor()
			'''
			try:
				cursor.execute("select nid from users where cname='{0}' \
					and cpassword='{1}'".format(username, password))
			except:
				raise HTTPError(500, "db error")
			'''
			try:
				cursor.execute("insert into users (cname, cpassword) \
					values ('{0}', '{1}') returning nid".format(username, password))
			except:
				raise HTTPError(500, "db error")
			if cursor.rowcount > 0:
				nid = cursor.fetchone()
				self.conn.commit()
				try:
					cursor.execute("select nid, cname, dcreate_at \
						from tbusers where nid={0}".format(0))
				except:
					raise HTTPError(500)
				user = cursor.fetchone()
				return self.write(json_encode({
					'nid':user[0],
					'cname':user[1],
					#'dcreate_at':user[2].
					'dcreate_at':time.mktime(user[2].timetuple()),
					}))
			else:
				raise HTTPError(500)
		else:
			raise HTTPError(500)			# 400+


class UserHandler(BaseHandler):

	@login_required
	def get(self, id):

		cursor = self.conn.cursor()
		if self.current_user['nrole'] == 0: 		# user role
			if self.current_user['nid'] != int(id):
				raise HTTPError(403)

			try:
				cursor.execute("select nid, cname, dcreate_at \
					from tbusers where nid={0}".format(id))
			except:
				raise HTTPError(500)
			user = cursor.fetchone()
			cursor.close()		
			return self.write(json_encode({
				'nid':user[0],
				'cname':user[1],
				#'ccreate_at':user[2],
				'dcreate_at':time.mktime(user[2].timetuple()),
				}))
		else:										# admin role
			try:
				cursor.execute("select nid, cname, dcreate_at, nrole \
					from tbusers where nid={0}".format(id))
			except:
				raise HTTPError(500)
			user = cursor.fetchone()
			cursor.close()		
			return self.write(json_encode({
				'nid':user[0],
				'cname':user[1],
				#'ccreate_at':user[2],
				'dcreate_at':time.mktime(user[2].timetuple()),
				'nrole':user[3]
				}))


	@login_required
	def put(self, id):
		'''
		user 	: 	change its own info [cname]
		admin 	: 	change anyuser`s info [cname, nrole]
		'''
		if self.current_user['nrole'] == 0: 			# user role

			''' normal user can only change itself '''
			if self.current_user['nid'] != id:
				raise HTTPError(403)

			user = json.loads(self.request.body)
			nid, cname = user

			if nid != id:
				raise HTTPError(403)
			cursor = self.conn.cursor()
			''' check the id ?'''
			try:
				cursor.execute("update users set cname={0} where nid={1}".format(cname, nid))
			except:
				raise HTTPError(500)
			self.conn.commit()
			cursor.close()
			return
		else:											# admin role
			user = json.loads(self.request.body)
			nid, cname, nrole = user

			cursor = self.conn.cursor()
			''' check the id ?'''
			try:
				cursor.execute("update users set cname={0}, nrole={1} \
					where nid={2}".format(cname, nrole, nid))
			except:
				raise HTTPError(500)
			self.conn.commit()
			cursor.close()
			return


