#!/usr/bin/env python2
from __future__ import with_statement
import Pyro4



class MST(object):
	#def __init__(self):
	#	self.nodeslist={}
	#	self.nodes=['one']
	
	
	def initiate(self,n):
		 nodes.append(n);
		 #print "inside server" 
		 print "node added: {0}.".format(n)
		 print('Nodes: %s' %  (', '.join(nodes)))
		 return nodes
		 
	def getnei (self,index):
		if int(index) == 1:
			return [0,1,2] #getting neighbor info can be done by external file
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
		
	def send (self,candi):
		return len (nodes)
		
nodeslist={}
nodes=[]
#num=0
		
daemon = Pyro4.Daemon()                # make a Pyro daemon
ns = Pyro4.locateNS()                  # find the name server
uri = daemon.register(MST)   # register the greeting maker as a Pyro object
ns.register("mst.greeting", uri)   # register the object with a name in the name server

print("Ready.")
daemon.requestLoop() 
