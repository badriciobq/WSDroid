#!/usr/bin/env python
#coding: utf-8

from settings import *
import application
import sys
from flask import Flask, request, jsonify

class webService():
    """
    Classe responsável por iniciar o webService.
    """

    def __init__(self):
        self.app = Flask(__name__)
        self.link_url_method()
        self.app.error_handler_spec[None][404] = self.not_found
        
        if AUTENTICATION:
            self.app.before_request_funcs[None] = [self.requires_auth]
        
        self.app.run(IP, PORT, DEBUG)

        
    def link_url_method(self):
        try:
            for i in MAPURL:
                self.app.add_url_rule(i[0], view_func=getattr(application, i[1]), methods=['GET', 'POST'])
    
        except AttributeError:
            self.app.logger.error('URL: "{url}" Não pode ser mapeada para a função: "{attr}"\n'.format(attr=i[1], url=i[0]))
            exit(1)


    def not_found(self, error=None):
        message = {
            'status': 404,
            'message': 'Not Found: ' + request.url,
        }
        resp = jsonify(message)
        resp.status_code = 404

        return resp


    def check_auth(self, username, password):
        return username == 'admin' and password == 'secret'


    def authenticate(self):
        message = {'message': "Authenticate."}
        resp = jsonify(message)
        resp.status_code = 401
        resp.headers['WWW-Authenticate'] = u'Basic realm="WebService requer autenticação"'

        return resp


    def requires_auth(self):
        auth = request.authorization
        
        if not auth: 
            return self.authenticate()

        elif not self.check_auth(auth.username, auth.password):
            return self.authenticate()
           

if __name__ == '__main__':
    wb = webService()



