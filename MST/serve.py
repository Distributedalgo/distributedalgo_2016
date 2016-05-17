#!/usr/bin/env python2
from __future__ import with_statement
import Pyro4
import socket
import select
import sys


class MST(object):
	#def __init__(self):
	#	self.nodeslist={}
	#	self.nodes=['one']
	
	
	def initiate(self,n):
		nodes.append(n);
		 
		ready_to_read,ready_to_write,in_error = select.select(SOCKET_LIST,[],[],0)
		#maitaining socket list .... to be used while sending msg
		for sock in SOCKET_LIST:
		 if sock == server_socket: 
		  sockfd, addr = server_socket.accept()
		  SOCKET_LIST.append(sockfd)
		  print SOCKET_LIST
                 
		print "node added: {0}.".format(n)
		print('Nodes: %s' %  (', '.join(nodes)))
		return nodes
		 
	def getnei (self,index):
		if int(index) == 1:
			return [0,1,2] #getting neighbor info......this can be done by external file
		if int(index) == 2:
			return [1,0,3]
		if int(index) == 3:
			return [2,3,0]
	
	def leave (self,n):
		nodes.remove(n);
		print "node removed: {0}.".format(n)
		print('Updated Nodes: %s' %  (', '.join(nodes)))
		return nodes
	
	def getnum (self):
		return len (nodes)
		
	def send (self,candi,msg):
		#sending msg to destination socket
		dest=SOCKET_LIST[candi+1]
		dest.send(msg)
		
		
nodeslist={}
nodes=[]
#num=0
HOST = '' 
SOCKET_LIST = []
RECV_BUFFER = 4096 
PORT = 9009
server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
server_socket.bind((HOST, PORT))
server_socket.listen(10)
SOCKET_LIST.append(server_socket)
		
daemon = Pyro4.Daemon()                # make a Pyro daemon
ns = Pyro4.locateNS()                  # find the name server
uri = daemon.register(MST)   # register the greeting maker as a Pyro object
ns.register("mst.greeting", uri)   # register the object with a name in the name server

print("Ready.")
daemon.requestLoop() 
