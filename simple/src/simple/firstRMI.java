/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simple;
import java.rmi.*;
/**
 *
 * @author Lenovo
 */
public interface firstRMI extends Remote{
    public int[] send (String input, String[] Buffer,int[] vec_clk,int pro_id,int dest_id) throws RemoteException;
    public String receive (int pro_id) throws RemoteException;
    public String Deliver (String input, int[] vec_clk,int pro_id) throws RemoteException;
    //public void MSG();
}
