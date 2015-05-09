# -*- coding: utf-8 -*-

import tornado.web


class BaseHandler(tornado.web.RequestHandler):

	@property
	def conn(self):
		return self.application.db