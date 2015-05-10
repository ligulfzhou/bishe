# -*- coding: utf-8 -*-

from base import BaseHandler
from utils import login_required, admin_required

#@login_required
class TestHandler(BaseHandler):

	@admin_required
	def get(self):
		return self.write("hello world:{0}".format(self.current_user.get("nid")))