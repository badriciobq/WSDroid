# coding: utf-8

# Configura o IP que rodará o servidor
IP = '127.0.0.1'

# Porta que o webService escutará
PORT = 8001

# Habilita o modo debug (CUIDADO)
DEBUG = True

# Mapeamento entre URLs e funções
MAPURL = ( ('/', 'usage'),
           ('/hd', 'list_hd'),
           ('/mem', 'list_mem'),
           ('/cpu', 'list_cpu'),
           ('/netw','list_network'),
           ('/hd/<index>', 'list_hd'),
           ('/netw/<index>','list_network'),
)

# Configura se o servidor terá autenticação
AUTENTICATION = False

# Informações sobre criptografia
ENCRIPT = False

