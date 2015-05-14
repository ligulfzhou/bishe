# -*- coding: utf-8 -*-

from handlers import test, categories, goods, login, users, orders, categoriedgoods, usersOrders

url_patterns = [
    (r"/api/v1", test.TestHandler),
    (r"/(\d+)/(orders)$", test.OrdersHandler),

    (r"/api/v1/categories", categories.CategoriesHandler),
    (r"/api/v1/categories/(\d+)", categories.CategoryHandler),

    (r"/api/v1/goods", goods.GoodsHandler),
    (r"/api/v1/goods/(\d+)", goods.GoodHandler),

    (r"/api/v1/categories/(\d+)/goods", categoriedgoods.CategoriedGoodsHandler),

    (r"/api/v1/login", login.LoginHandler),

    (r"/api/v1/users", users.UsersHandler),
    (r"/api/v1/users/(\d+)", users.UserHandler),

    (r"/api/v1/orders", orders.OrdersHandler),
    (r"/api/v1/orders/(\d+)", orders.OrderHandler),
    
    (r"/api/v1/users/(\d+)/orders", usersOrders.UsersOrdersHandler)
]