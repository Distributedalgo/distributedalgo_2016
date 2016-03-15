package nl.tudelft.distalgo.da2.test;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import nl.tudelft.distalgo.da2.process.*;

public class TestSetup {

    private List<Singhal_RMI> processes;
    private String[] addrs;

    public TestSetup() { }
    
    public void initTest() {
		
    	List<String> addrs = new ArrayList<String>(10);
		addrs.add("rmi://localhost/Singhal1");
		addrs.add("rmi://localhost/Singhal2");
		addrs.add("rmi://localhost/Singhal3");
		
		processes = new ArrayList<Singhal_RMI>();
		
		for(String addr : addrs) {
			try {
				Singhal_RMI process = (Singhal_RMI) Naming.lookup(addr);
				process.resetState();
				processes.add(process);
			} catch(RemoteException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (NotBoundException e) {
				e.printStackTrace();
			} 
		}
		
    }

    public List<Singhal_RMI> getAllProcesses() {
        return processes;
    }

    public String[] getAddrs() {
        return addrs;
    }
}
