package nl.tudelft.distalgo.da2;

import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import nl.tudelft.distalgo.da2.process.ProcMan;

// This source code is adopted from https://github.com/dzzh/in4150

public class Main {
	
	public static void main(String[] args) {
	
		try {
			LocateRegistry.createRegistry(1099);
		} catch(RemoteException e){
			e.printStackTrace();
		}
  
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }

        new ProcMan().startServerRMI();
    }
	
}