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
//import com.hazelcast.core.*;
//import com.hazelcast.config.*;
 

/**
 *
 * @author Lenovo
 */
public class client1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException {
        String[] Buffer=new String[10];
        String rev_msg="";
        //String input="";
        //int[] myIntArray = new int[3];
        int[] vec_clk = {0,0,0};
        int pro_id=1,msg_id=0,dest_id=0;
        //System.out.println("vec_clk before " +vec_clk);
        firstRMI service = (firstRMI) Naming.lookup ("rmi://localhost:5099/hello");
        //System.out.println("vec_clk before "+ Arrays.toString(vec_clk) + "  vec_clk after :" +Arrays.toString(service.send("hey serv",vec_clk,pro_id)));
       vec_clk =service.send("hey serv this is client 1",Buffer,vec_clk,pro_id,dest_id);
        //Buffer[msg_id]=Integer.toString(dest_id)+","+Arrays.toString(vec_clk);//saving buffer for node
        //System.out.println("My buffer:"+Buffer[msg_id]);
        //rev_msg=service.receive(pro_id);
        //System.out.println("Rev_msg:"+rev_msg);
        
        while(true)
        {
            rev_msg=service.receive(pro_id);
            if(rev_msg!=null)
            {
                System.out.println("message:"+rev_msg);
                break;
            }
        }
        


// TODO code application logic here
    }
    
}
