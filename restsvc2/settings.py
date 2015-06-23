# -*- coding: utf-8 -*-

import os.path

from tornado.options import define


define("port", default=443, help="run on the given port", type=int)
define("config", default=None, help="tornado config file")
define("debug", default=False, help="debug mode")
define("dbhost", default="127.0.0.1", help="blog database host")
define("dbname", default="bishe", help="blog database name")
define("dbuser", default="postgres", help="blog database user")
define("dbpassword", default="postgres", help="blog database password")

define("pagesize", default=20, help="basically for tbgoods, one page size", type=int)

settings = {}

settings["debug"] = True