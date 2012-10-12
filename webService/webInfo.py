#!/usr/bin/env python
#coding: utf-8

from settings import *
import application
import sys
from flask import Flask, request, jsonify


class webService():
    """
    Classe responsável para criar o webService.
    """

    def __init__(self):
        """
        Construtor que inicia o servidor web e configura os serviços
        necessários de acordo com as informações obtidas no arquivo
        settings.py. O construtor é responsável por iniciar o flask no IP e
        PORTA especificados e configurar os erros de autenticação e de página
        não encontrada.
        """
        self.app = Flask(__name__)
        self.link_url_method()
        self.app.error_handler_spec[None][404] = self.not_found

        if AUTENTICATION:
            self.app.before_request_funcs[None] = [self.requires_auth]

        self.app.run(IP, PORT, DEBUG)

    def link_url_method(self):
        """
        Método utilizado para mapear as urls configuradas no arquivo settins
        com os funções criadas no arquivo applicaion.py
        """
        try:
            for i in MAPURL:
                self.app.add_url_rule(i[0],
                view_func=getattr(application, i[1]), methods=['GET', 'POST'])

        except AttributeError:
            msg = """URL não pode ser mapeada para a função:
            Verifique as configurações no arquivo settings.py
            """
            sys.stderr.write(msg)
            exit(1)

    def not_found(self, error=None):
        """
        Cria uma resposta para quando uma página não for econtrada.
        """
        message = {
            'status': 404,
            'message': 'Not Found',
        }
        resp = jsonify(message)
        resp.status_code = 404

        return resp

    def check_auth(self, username, password):
        """
        Checa pela autenticação.
        (Método a implementar) A implementação corrente faz autenticação
        somente do usuário admin com a senha secret, para alterar a forma de
        autenticação este método deve ser implementado
        """
        return username == 'admin' and password == 'secret'

    def authenticate(self):
        """
        Cria uma resposta para quando uma página de autenticação e insere o
        cabeçalho responsável por solicitar a autenticação aos agentes HTTP
        """
        message = {
            'status': 401,
            'message': 'Not Autenticated',
        }

        resp = jsonify(message)
        resp.status_code = 401
        resp.headers['WWW-Authenticate'] = u'Basic realm="Autentique-se"'

        return resp

    def requires_auth(self):
        """
        Método que verifica se o usuário e a senha recebidos via http são
        válidos, caso a autenticação seja rejeitada ele solicita nova
        autenticação via cabeçalho http.
        """
        auth = request.authorization

        if not auth:
            return self.authenticate()

        elif not self.check_auth(auth.username, auth.password):
            return self.authenticate()


if __name__ == '__main__':
    wb = webService()
