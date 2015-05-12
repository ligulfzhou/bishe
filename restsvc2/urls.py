# -*- coding: utf-8 -*-

from handlers import test

url_patterns = [
    (r"/", test.TestHandler),
    (r"/(\d+)/(orders)$", test.OrdersHandler)
]