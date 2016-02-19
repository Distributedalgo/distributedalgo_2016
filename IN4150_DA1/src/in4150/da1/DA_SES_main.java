/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in4150.da1;

/* Import java RMI package */
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Main class for Schiper-Eggli-Sandoz (SES) Algorithm
 * @author xflash
 */
public class DA_SES_main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // RMI registry start
        try {
            java.rmi.registry.LocateRegistry.createRegistry(1099);
        } catch(RemoteException e) {
            e.printStackTrace();
        }
        
        // Create and install a security manager
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
        
        // Start process manager
       // new ProcessManager().startServer();
    }
    
}
