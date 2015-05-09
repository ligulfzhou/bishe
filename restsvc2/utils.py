import hashlib
from tornado.httpclient import HTTPError
from functools import wraps
import base64
def _checkAuth(login, password):
    ''' Check user can access or not to this element '''
    if login == "admin" and password == "admin":
    	return True
    else:
    	return False

def httpauth(handler_class):
    ''' Handle Tornado HTTP Basic Auth '''
    def wrap_execute(handler_execute):
        def require_auth(handler, kwargs):
            auth_header = handler.request.headers.get('Authorization')

            if auth_header is None or not auth_header.startswith('Basic '):
                handler.set_status(401)
                handler.set_header('WWW-Authenticate', 'Basic realm=Restricted')
                handler._transforms = []
                handler.finish()
                return False

            auth_decoded    = base64.decodestring(auth_header[6:])
            login, password = auth_decoded.split(':', 2)
            auth_found      = _checkAuth(login, password)

            if auth_found is None:
                handler.set_status(401)
                handler.set_header('WWW-Authenticate', 'Basic realm=Restricted')
                handler._transforms = []
                handler.finish()
                return False
            else:
                handler.request.headers.add('auth', auth_found)


        def _execute(self, transforms, *args, **kwargs):
            if not require_auth(self, kwargs):
                return False
            return handler_execute(self, transforms, *args, **kwargs)

        return _execute

    handler_class._execute = wrap_execute(handler_class._execute)
    return handler_class