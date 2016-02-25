/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simple;
import java.net.MalformedURLException;
import java.rmi.*;
import static java.rmi.Naming.lookup;
import java.util.Arrays;
/**
 *
 * @author Lenovo
 */
public class client1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException {
        int[] vec_clk = {0,0,0};
        int pro_id=1;
        firstRMI service = (firstRMI) Naming.lookup ("rmi://localhost:5099/hello");
        System.out.println("vec_clk before "+ Arrays.toString(vec_clk) + "  vec_clk after :" +Arrays.toString(service.send("hey serv",vec_clk,pro_id)));
// TODO code application logic here
    }
    
}
