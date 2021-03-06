# coding: utf-8

from flask import request, jsonify, abort
import psutil
import subprocess
import re


def usage():
    """
    Retorna todas as informações do computador no formato JSON, caso a
    requisição não seja to tipo GET, ele retorna uma página mensagem
    informando que a página não foi encontrada.
    """
    if request.method == 'GET':
        resp = jsonify(get_index())
        resp.status_code = 200
        return resp

    else:
        abort(404)


def list_hd(index=None):
    """
    Retorna informações do HD por completo se a url terminar com 'hardDisk'
    ou retorna as informação de uma partição especifica se a url terminar
    com o ponto de montagem da partição. As informações são retornadas no
    formato JSON e contém os seguintes campos::

    "hardDisk": {
    "sda5": {
    "used": 113527726080,
    "percent": 56.5,
    "free": 77170294784,
    "fstype": "ext4",
    "device": "/dev/sda5",
    "mountpoint": "/home",
    "total": 200903335936,
    "opts": "rw"}

    """
    if request.method == 'GET':
        if index:
            dic = get_index().get('hardDisk').get(index)
            if not dic:
                abort(404)
        else:
            dic = get_index().get('hardDisk')

        dic.update({'status': 200})
        resp = jsonify(dic)
        resp.status_code = 200

        return resp

    else:
        abort(404)


def list_mem():
    """
    Retorna informações da Memória. As informações são retornadas no
    formato JSON e contém os seguintes campos::

    "memory": {
    "total": 3938222080,
    "available": 2748809216,
    "percent": 30.2,
    "used": 2607411200,
    "free": 1330810880,
    "active": 1424625664,
    "inactive": 882995200,
    "buffers": 333889536,
    "cached": 1084108800 }

    """
    if request.method == 'GET':
        dic = get_index().get('memory')
        dic.update({'status': 200})

        resp = jsonify(dic)
        resp.status_code = 200

        return resp
    else:
        abort(404)


def list_cpu():
    """
    Retorna informações do processador. As informações são retornadas no
    formato JSON e contém os seguintes campos::

    "cpu": {
    "idle": 38719.49,
    "softirq": 6.36,
    "irq": 0.0,
    "iowait": 347.69,
    "nice": 26.94,
    "percent": 28.2,
    "system": 264.54,
    "user": 922.46 }

    """
    if request.method == 'GET':
        dic = get_index().get('cpu')
        dic.update({'status': 200})

        resp = jsonify(dic)
        resp.status_code = 200

        return resp
    else:
        abort(404)


def list_network():
    """
    Retorna informações de rede no formato JSON::

    "eth1": {
    "packets_sent": 40252,
    "packets_recv": 41426,
    "bytes_recv": 36079799,
    "dropout": 0,
    "bytes_sent": 6338471,
    "errout": 18,
    "ipv4": "192.168.1.100",
    "errin": 0,
    "dropin": 0,
    "masc": "255.255.255.0" }

    """
    if request.method == 'GET':
        dic = get_index().get('netw')
        dic.update({'status': 200})

        resp = jsonify(dic)
        resp.status_code = 200

        return resp
    else:
        abort(404)


def list_network(index=None):
    """
    Retorna informações da interface de rede específicada na url
    no formato JSON::

    "eth1": {
    "packets_sent": 40252,
    "packets_recv": 41426,
    "bytes_recv": 36079799,
    "dropout": 0,
    "bytes_sent": 6338471,
    "errout": 18,
    "ipv4": "192.168.1.100",
    "errin": 0,
    "dropin": 0,
    "masc": "255.255.255.0"}

    """
    if request.method == 'GET':
        if index:
            dic = get_index().get('netw').get(index)
            if not dic:
                abort(404)
        else:
            dic = get_index().get('netw')

        dic.update({'status': 200})
        resp = jsonify(dic)
        resp.status_code = 200

        return resp

    elif request.method == 'POST':
        return 'Metodo Post\n'

    else:
        abort(404)


def get_index():
    """
    Coleta as informações da Memória, HD, Rede e Processadores do computador
    e retorna um dicionário contendo todas as informações::
    """
    dic_ret = {}
    dic_ret.update({'status': 200})

    # Coleta informação de memória
    mem = psutil.virtual_memory()
    dic_ret.update({'memory': mem._asdict()})

    # Coleta informação do HD
    hd = {}
    for i in psutil.disk_partitions():
        aux = {}
        aux.update(i._asdict())
        aux.update(psutil.disk_usage(i.mountpoint)._asdict())
        hd.update({i.device.rsplit('/', 1)[1]: aux})
    dic_ret.update({'hardDisk': hd})

    # Coleta informação do CPU
    cpu = {}
    cpu.update(psutil.cpu_times()._asdict())
    cpu.update({'percent': psutil.cpu_percent()})
    dic_ret.update({'cpu': cpu})

    # Coleta so dados da rede
    dic_main = {}
    net = psutil.network_io_counters(pernic=True)

    for i in net.iterkeys():
        dic = {}
        dic.update(net.get(i)._asdict())

        try:
            addr = subprocess.check_output(["/sbin/ifconfig", i])
            padrao = re.compile(r'inet end.: (.*?) .*Masc:(.*)', re.M)
            ip = padrao.search(addr).group(1)
            mask = padrao.search(addr).group(2)
            dic.update({'ipv4': ip, 'masc': mask})

        except:
            pass

        dic_main.update({i: dic})

    dic_ret.update({'netw': dic_main})
    return dic_ret
