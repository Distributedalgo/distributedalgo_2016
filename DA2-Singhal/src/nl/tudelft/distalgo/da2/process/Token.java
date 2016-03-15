package nl.tudelft.distalgo.da2.process;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Token implements Serializable {

	private static final long serialVersionUID = 6827759047863339052L;
	
	List<Integer> TN;
	List<String> TS;
	private static boolean isCreated = false;
	
    private Token(int nProc) {
        TN = new ArrayList<Integer>();
        TS = new ArrayList<String>();
        
        for (int i = 0; i < nProc; i++){
            TN.add(0);
            TS.add("O");
        }
    }

    // Initialized once, only one token in the system
    public static Token init(int nProc){
        if (!isCreated){
            isCreated = true;
            return new Token(nProc);
        }
        return null;
    }

    public List<Integer> getTN() {
        return TN;
    }
    
    public List<String> getTS() {
    	return TS;
    }
}
