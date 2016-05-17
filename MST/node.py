#!/usr/bin/env python2
# saved as greeting-client.py
from __future__ import with_statement

import string
import sys
import Pyro4
from Pyro4 import threadutil


if sys.version_info < (3, 0):
    input = raw_input

class node (object):
	def __init__(self):
		 self.mst = Pyro4.Proxy("PYRONAME:mst.greeting")  
	 
	def join_net(self):
		self.name=input('Name for new node: ').strip()
		self.lst=self.mst.initiate(self.name)
		print('Nodes: %s' %  (', '.join(self.lst)))
		self.index=self.mst.getnum()
		self.nei=self.mst.getnei(self.index)
		self.s=map(str,self.nei)
		print self.nei

	def leave_net(self):
		while True:
			self.n = raw_input("Please enter L to leave:")
			if self.n.strip() == 'L':
				self.lst=self.mst.leave(self.name)
				print('Nodes: %s' %  (', '.join(self.lst)))
				break
	 
	def wait_net(self):
		print "waiting for others to start"
		while True:
		 self.memnum=self.mst.getnum()
		 if int (self.memnum) == 3:
		  break;
		print "MST initiated......."


	def wake_up(self):
		
		self.SE = [None] * 3
		while True:
			SN='sleeping'
			if int(self.index)==1:
				break
		print "node waking up......"
		self.minwt=100
		self.candi=100
		for x in range(0, len(self.nei)):
			if int(self.nei[x]) > 0:
				if int(self.nei[x]) < int(self.minwt):
				 self.minwt=self.nei[x]
				 self.candi=x
		self.find_count=0
		self.LN=0
		self.SN=0
		self.SE[self.candi]='in_MST'
		print "node is awake.sending connect to......"
		#print self.SE
		self.mst.send(self.candi)	



N1=node()
N1.join_net()
N1.wait_net()

N1.wake_up()
#N1.leave_net()
print "Uitgang."
