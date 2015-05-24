import hashlib
from tornado.httpclient import HTTPError
from functools import wraps
import base64
import tornado.log


def login_required(method):
    
    @wraps(method)
    def wrapper(self, *args, **kwargs):
        if not self.current_user:
            raise HTTPError(401)
        return method(self, *args, **kwargs)
    return wrapper


def admin_required(method):

    @wraps(method)
    def wrapper(self, *args, **kwargs):
        ''' now that, this function need admin role, login error alse raise 403
        
        if not self.current_user or self.current_user.get('nrole') == 0:
            raise HTTPError(403)
        '''
        if not self.current_user:
            raise HTTPError(401)
        if self.current_user.get('nrole') == 0:
            raise HTTPError(403)
        return method(self, *args, **kwargs)
    return wrapper

'''
def _checkAuth(username, password):
    # Check user can access or not to this element
    if username == "admin" and password == "admin":
        return 1
    else:
        return 0


def login_required(handler_class):
    #Handle Tornado HTTP Basic Auth
    def wrap_execute(handler_execute):
        def require_auth(handler, kwargs):
            auth_header = handler.request.headers.get('Authorization')

            if auth_header is None or not auth_header.startswith('Basic '):
                handler.set_status(401)
                handler.set_header('WWW-Authenticate', 'Basic realm=Restricted')
                handler._transforms = []
                handler.finish()
                return False

            try:
                
                #base64.decodestring may go wrong
                #auth_split(":", 2) also will go wrong for wrong Authorization
                auth_decoded = base64.decodestring(auth_header[6:])
                username, password = auth_decoded.split(':', 2)
                auth_found      = _checkAuthorize(username, password)

            except:
                handler.set_status(401)
                handler.set_header('WWW-Authenticate', 'Basic realm=Restricted')
                handler._transforms = []
                handler.finish()
                return False

            if auth_found == 0:
                handler.set_status(401)
                handler.set_header('WWW-Authenticate', 'Basic realm=Restricted')
                handler._transforms = []
                handler.finish()
                return False
            else:
                return True

        def _execute(self, transforms, *args, **kwargs):
            if not require_auth(self, kwargs):
                return False
            return handler_execute(self, transforms, *args, **kwargs)

        return _execute

    handler_class._execute = wrap_execute(handler_class._execute)
    return handler_class


def _checkAuthorize(username, password):
    if username == "admin" and password == "admin": #and admin == True:
        return 1
    else:
        return 0

def admin_required(handler_class):
    #Handle Tornado HTTP Basic Auth 
    def wrap_execute(handler_execute):
        def require_auth(handler, kwargs):
            auth_header = handler.request.headers.get('Authorization')

            if auth_header is None or not auth_header.startswith('Basic '):
                handler.set_status(403)
                handler.set_header('WWW-Authenticate', 'Basic realm=Restricted')
                handler._transforms = []
                handler.finish()
                return False

            try:
                
                #base64.decodestring may go wrong
                #auth_split(":", 2) also will go wrong for wrong Authorization
                auth_decoded = base64.decodestring(auth_header[6:])
                username, password = auth_decoded.split(':', 2)
                auth_found      = _checkAuthorize(username, password)

            except:
                handler.set_status(403)
                handler.set_header('WWW-Authenticate', 'Basic realm=Restricted')
                handler._transforms = []
                handler.finish()
                return False

            if auth_found == 0:
                handler.set_status(403)
                handler.set_header('WWW-Authenticate', 'Basic realm=Restricted')
                handler._transforms = []
                handler.finish()
                return False
            else:
                return True

        def _execute(self, transforms, *args, **kwargs):
            if not require_auth(self, kwargs):
                return False
            return handler_execute(self, transforms, *args, **kwargs)

        return _execute

    handler_class._execute = wrap_execute(handler_class._execute)
    return handler_class
    '''