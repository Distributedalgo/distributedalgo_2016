/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simple;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Lenovo
 */
public class serverP extends UnicastRemoteObject implements firstRMI{

    public serverP() throws RemoteException {
        super();
    }
    
    
    
    @Override
    public int[] send(String input,int [] vec_clk,int pro_id) throws RemoteException {
        
        vec_clk[pro_id]=vec_clk[pro_id]+1;
        
        
        return vec_clk; //To change body of generated methods, choose Tools | Templates.
    }
    
}
