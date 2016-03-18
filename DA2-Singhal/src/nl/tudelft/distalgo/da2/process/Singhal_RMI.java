package nl.tudelft.distalgo.da2.process;

import nl.tudelft.distalgo.da2.message.*;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Singhal_RMI extends Remote {
	
	public void execute() throws RemoteException;
	
	public void receiveToken(TokenMsg tokenMsg) throws RemoteException;
	
	public void receiveReq(RequestMsg reqMsg) throws RemoteException;
	
	public void resetState() throws RemoteException;
	
	public int getProcId() throws RemoteException;
	
	public boolean isExecFinished() throws RemoteException;
	
}
