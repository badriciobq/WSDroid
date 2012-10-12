# coding: utf-8

# Configura o IP do WebService
IP = '192.168.1.100'

# Configura a porta do webService
PORT = 8001

# Habilita o modo debug (CUIDADO)
DEBUG = True

# Mapeamento entre URLs e funções
MAPURL = (('/', 'usage'),
          ('/hd', 'list_hd'),
          ('/mem', 'list_mem'),
          ('/cpu', 'list_cpu'),
          ('/netw', 'list_network'),
          ('/hd/<index>', 'list_hd'),
          ('/netw/<index>', 'list_network'))

# Configura se o servidor terá autenticação
AUTENTICATION = True
