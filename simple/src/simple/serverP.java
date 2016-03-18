/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simple;
import java.rmi.*;import java.io.*;
import java.util.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 *
 * @author Lenovo
 */
public class serverP extends UnicastRemoteObject implements firstRMI{
    
     //MSG[] msgs = new MSG [5];
    int MGS_ID=0;
     List<MSG> msgList = new ArrayList<MSG>(15);
     //layer[] thePlayers = new Player[playerCount + 1];
    //msgs().dest_id();
   // String [] MSG=new String[50];
    public serverP() throws RemoteException {
        super();
    }
    
     
    
    @Override
    public int[] send(String input,String[] Buffer,int [] vec_clk,int pro_id,int dest_id) throws RemoteException {
       // MSG[] msgs = new MSG [5];
       int i=0;MSG msgs= new MSG();
    //System.out.println("Dest Id::"+dest_id);
              vec_clk[pro_id]=vec_clk[pro_id]+1;
         //     System.out.println("Dest Id::"+Arrays.toString(vec_clk));
                   // MGS_ID=dest_id;
              msgs.setdest_id(dest_id);
              msgs.vec_clk=vec_clk;
              System.out.println("msg_id::"+MGS_ID);
              msgs.input=input; 
              msgList.add(MGS_ID,msgs);
             //MSG[MGS_ID]=dest_id+","+input+","+Arrays.toString(vec_clk);
             MGS_ID=+1;
             //System.out.println("Dest Id::"+msgs.getdest_id());
             //System.out.println("Input::"+msgs.getinput());
        return vec_clk; //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
     public String receive(int pro_id) throws RemoteException {
        int i=0; String mess = null; MSG temp;
    /*   for (i=0;msgs[i]!=null;i++)
       {
          
        
        System.out.println("here:");
           if(msgs[i].dest_id==pro_id)
           { 
               mess=msgs[i].input;
           }
                   
           return mess;
       }
      */  
    int j=msgList.size();
    System.out.println("Size::"+j);
   
    for(i=0;i<=j-1;i++)
    {
        temp=msgList.get(i);
        System.out.println("iteration::"+i);
        System.out.println("Dest Id in temp::"+temp.getdest_id());
        System.out.println("Input in temp::"+temp.getinput());
        
    if (temp.getdest_id()==pro_id)
         {
             mess=temp.input;
        //     System.out.println("Dest Id in temp::"+temp.getdest_id());
          //   System.out.println("Input in temp::"+temp.getinput());
         }
    
    }
    
    //System.out.println("Dest Id::"+msgs.getdest_id());
           //  System.out.println("Input::"+msgs.getinput());
       /*  if (temp.getdest_id()==pro_id)
         {
             mess=msgs.input;
         }
        //vec_clk[pro_id]=vec_clk[pro_id]+1;
        
        */
        return mess; //To change body of generated methods, choose Tools | Templates.
    }
     public String Deliver(String input,int [] vec_clk,int pro_id) throws RemoteException {
        
       //String [] MSG=new String[50];
        
        //vec_clk[pro_id]=vec_clk[pro_id]+1;
        
        
        return ""; //To change body of generated methods, choose Tools | Templates.
    }
     
    
}
