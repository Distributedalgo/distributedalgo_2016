/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in4150.da1.main;

/* Import java RMI package */
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author xflash
 */
public class DA_SES extends UnicastRemoteObject 
        implements  DA_SES_RMI {
    
    public void send(Message m, Buffer S, VectorClock V) {
        
    }
    
    public void receive() {
        
    }
    
    public void deliver() {
        
    }
            
        
}
