# -*- coding: utf-8 -*-

from handlers import test, categories, goods, login, users, \
    orders, categoriedgoods, usersOrders, ordersorderitems, \
    favorites

url_patterns = [
    #(r"/api/v1", test.TestHandler),
    # (r"/(\d+)/(orders)$", test.OrdersHandler),
    (r"/api/v1/favorites", test.TestHandler),

    #get categories list, add one category, get one category info 
    (r"/api/v1/categories", categories.CategoriesHandler),
    (r"/api/v1/categories/(\d+)", categories.CategoryHandler),

    # get goods list, add one good, get one good info 
    (r"/api/v1/goods", goods.GoodsHandler),
    (r"/api/v1/goods/(\d+)", goods.GoodHandler),

    # get goods of this kind of category
    (r"/api/v1/categories/(\d+)/goods", categoriedgoods.CategoriedGoodsHandler),

    # for user to log in, and user get token from here[post]
    (r"/api/v1/session", login.LoginHandler),

    # get user list, add one user(used to register one user), get one user info 
    (r"/api/v1/users", users.UsersHandler),
    (r"/api/v1/users/(\d+)", users.UserHandler),

    # get orders list, make order, get one order info
    (r"/api/v1/orders", orders.OrdersHandler),
    (r"/api/v1/orders/(\d+)", orders.OrderHandler),

    # get orderitems for this order
    (r"/api/v1/orders/(\d+)/orderitems", ordersorderitems.OrdersOrderitemsHandler),
    (r"/api/v1/orders/(\d+)/orderitems/(\d+)", ordersorderitems.OrdersOrderitemHandler),

    # get user orders list
    (r"/api/v1/users/(\d+)/orders", usersOrders.UsersOrdersHandler),

    # the goods user likes
    # (r"/api/v1/favorites", favorites.FavoritesHandler),
    (r"/api/v1/favorites/(\d+)", favorites.FavoriteHandler),
]
