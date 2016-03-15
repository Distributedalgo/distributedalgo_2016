package nl.tudelft.distalgo.da2.test;

import java.rmi.RemoteException;

import nl.tudelft.distalgo.da2.process.Singhal_RMI;

public class TestThread implements Runnable {

    private Singhal_RMI process;
    
    public TestThread(Singhal_RMI process){
        this.process = process;
    }
    
	@Override
	public void run() {
		try {
			process.execute();
		} catch(RemoteException e) {
			throw new RuntimeException(e);
		}
	}

}
