# -*- coding: utf-8 -*-

from base import BaseHandler
from utils import login_required, admin_required

#@login_required
class TestHandler(BaseHandler):

	@login_required
	def get(self):
		return self.write("hello world:{0}".format(self.current_user.get("nid")))

class OrdersHandler(BaseHandler):

	@login_required
	def get(self, id, orders=True):
		return self.write("userid: {0}, orders{1}, orders{2}".format(id, id, "userid orders"))