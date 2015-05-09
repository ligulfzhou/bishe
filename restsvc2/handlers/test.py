# -*- coding: utf-8 -*-

from base import BaseHandler

class TestHandler(BaseHandler):

	def get(self):
		return "hello world"