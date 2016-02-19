/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in4150.da1.main;

/* Import java RMI package */
import java.rmi.*;

/**
 * RMI Interface class for Schiper-Eggli-Sandoz (SES) Algorithm
 * @author xflash
 */
public interface DA_SES_RMI extends Remote {
    
    /**
     * 
     * @param dest
     * @param msg
     * @throws RemoteException 
     */
    public void send(String dest, Message msg) throws RemoteException;
    
    /**
     * 
     * @param msg
     * @throws RemoteException 
     */
    public void receive(Message msg) throws RemoteException;
    
    public void deliver(Message msg) throws RemoteException;
}
