/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simple;

/**
 * MSG[MGS_ID]=dest_id+","+input+","+Arrays.toString(vec_clk);
 * @author Lenovo
 */


public class MSG {
       public int dest_id;
       public String input;
       public int[] vec_clk;
public MSG() {
int dest_id;
String input; 
int[] vec_clk;      

}

public MSG(int dest_id,String input, int[] vec_clk) {
      this.dest_id = dest_id;
      this.input = input;
      this.vec_clk=vec_clk;
}
//public MSG(){}
   

       // getter
       public int getdest_id() { return dest_id; }
       public String getinput() { return input; }
       public int[] getvec_clk() { return vec_clk; }
       // setter

       public void setdest_id(int dest_id) { this.dest_id = dest_id; }
       public void setinput(String dest_id) { this.input = input; }
       public void setvec_clk(int[] vec_clk) { this.vec_clk = vec_clk; }
    }