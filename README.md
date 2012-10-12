WSDroid
=======

Aplicação que disponibiliza informações do computador (carga da cpu, 
utilização de memória e utilização do HD) via WebService REST. As informações 
retornadas devem ser consumidas por uma aplicação android.

AUTOR
-----------------------------------------------------------------------------
Maurício José da Silva
badriciobq [at] gmail [dot] com


SOBRE
----------------------------------------------------------------------------
Aplicação Web desenvolvida pra disponibilizar via REST informações sobre o 
hardware do computador (HD, Memória, Processador e Rede). 
Desenvolvida como critério de avaliação para o curso de Python para
Administradores de redes linux oferecido através do projeto Laboratório de
redes do Conhecimento e ministrado pelo professor Rafael Jose de Alencar
rafjaa [at] gmail [dot] com
A aplicação tem como objetivo integra duas plataforma de desenvolvimento
completamente distintas, que é a disponibilização das informações de um
computador utilizando python via HTTP que devem ser consulmidas por um
dispositivo móvel com Android (a aplicação móvel está presente na pasta
tools).


REQUISITOS
----------------------------------------------------------------------------
Para satisfazer os requisitos necessário para a execução da aplicação você
pode utilizar o arquivo requirements.txt disponibilizado no respositório, para
isto basta digitar no terminal o seguinte comando

$sudo pip install -r requeriments.txt

Não se esqueça que é necessário ter o pacote python-pip instalado, que no
ubuntu pode ser instalado facilmente com o comando 

$sudo aptitude install python-pip


USO
---------------------------------------------------------------------------
Este WebService não foi desenvolvido com o objetivo de ser importado em um
terminal interativo do python, pois se trata de um serviço web que deve ficar
em execução. 
Para utilizar o WebService basta rodar o arquivo webInfo.py, que pode ser
feito com o seguinte comando:

$sudo <raiz do repositório>/webService/webInfo.py 

OBS: As configurações do servidor podem ser ajustadas no arquivo settings.py


OBSERVAÇÃO
----------------------------------------------------------------------------
Da forma que o webService foi desenvolvido, facilmente o usuári consegue
adaptá-lo para suas necessidades, pois ele utilizar um mapeamento entre URLs e
funções que permite ao usuario implementar sua própria função ser processada
caso o servidor receba determinada URL.

Para maiores detalhes consulte a documentação.





