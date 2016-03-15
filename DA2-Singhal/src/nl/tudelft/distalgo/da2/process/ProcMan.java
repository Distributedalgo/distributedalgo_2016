package nl.tudelft.distalgo.da2.process;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ProcMan {

	private ArrayList<Singhal_Component> processes;
	
	public void startServerRMI() {
		
		List<String> addrs = new ArrayList<String>(10);
		addrs.add("rmi://localhost/Singhal1");
		addrs.add("rmi://localhost/Singhal2");
		addrs.add("rmi://localhost/Singhal3");
		
		int pId = 0;
		
		for(String addr : addrs) {
			try {
				Singhal_Component process = new Singhal_Component(pId, addrs.toArray(new String[0]));
				Naming.bind(addr, process);
				pId++;
			} catch(RemoteException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (AlreadyBoundException e) {
				e.printStackTrace();
			}
		}
	}
	
    public List<Singhal_Component> getAllProcesses() {
        return processes;
    }
}
