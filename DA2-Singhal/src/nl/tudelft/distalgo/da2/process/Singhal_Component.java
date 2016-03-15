package nl.tudelft.distalgo.da2.process;

import nl.tudelft.distalgo.da2.message.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Singhal_Component extends UnicastRemoteObject implements Singhal_RMI, Runnable {

	private static final long serialVersionUID = 3870380839572348854L;
	
	private static final int MAX_EXEC_DELAY = 2000;
	private static final int TOKEN_CHECK_PERIOD = 10;
	
	private int pId;		// process index
	private int nProc;		// number of processes
	private boolean execFinished = false;
	private String[] remoteAddrs;
	private Token token = null;
	private Random random = new Random();		// random delay
	private List<Integer> N;	// Sequence of last known request from every process
	private List<String> S;
	
	// Constructor
	protected Singhal_Component(int pId, String[] remoteAddrs) throws RemoteException {
		this.pId = pId;
		this.remoteAddrs = remoteAddrs;
		this.nProc = remoteAddrs.length;
		
		resetState();
		
    	printLog("------------------------------------------------------");
        printLog("Process-" + pId + " init --> N: " + N);
        printLog("Process-" + pId + " init --> S: " + S);
        printLog("------------------------------------------------------");	
	}
	
	/* Implements remote functions */
	@Override
	public void run() {
		printLog("Process-" + pId + " started");
	}
	
	@Override
	public void execute() throws RemoteException {
		long tStart = System.currentTimeMillis();
		printLog("Start executing thread Process-" + pId);
		
		try {
			Thread.sleep(random.nextInt(MAX_EXEC_DELAY));
		} catch(InterruptedException e) {
			throw new RuntimeException(e);
		}

		if(token == null) sendFeasibleRequest();
		checkToken();
		criticalSection();
		releaseToken();
		
		try {
			Thread.sleep(random.nextInt(MAX_EXEC_DELAY));
		} catch(InterruptedException e) {
			throw new RuntimeException(e);
		}
		
		long tFinish = System.currentTimeMillis();
		execFinished = true;
		printLog("Finished executing thread Process-" + pId + ", " + (tFinish - tStart) + " ms.");
	}

	@Override
	public void receiveToken(TokenMsg tokenMsg) throws RemoteException {
        printLog("Process-" + pId + " received token");
        token = tokenMsg.getToken();
	}
	
	// Receive request from other processes
	@Override
	public synchronized void receiveReq(RequestMsg reqMsg) throws RemoteException {
		int srcId = reqMsg.getSrcId();
		int reqSeq = reqMsg.getReqSeq();
        List<Integer> TN = token.getTN();
        List<String> TS = token.getTS();
		
		printLog("Process-" + pId + " received request from process-" + srcId
						+ " with sequence " + reqSeq);
        printLog("Process-" + pId + " --> N: " + N);
        printLog("Process-" + pId + " --> S: " + S);
		
		N.set(srcId, reqSeq);
		
		printLog("Process-" + pId + " updated on receive request --> N: " + N);
		
		switch(S.get(pId)) {	// Check local state
			case "E":			// record process as requesting
			case "O":
				S.set(srcId,"R");
				printLog("Set process-" + srcId + " on Process-" + pId + " to R --> S: " + S);
				break;
			case "R":			// record process as requesting
				if(S.get(srcId) != "R") {
					S.set(srcId,"R");
		            Singhal_RMI dest = getProcess(remoteAddrs[srcId]);
		            try {
		                RequestMsg reqNewMsg = new RequestMsg(pId, remoteAddrs[pId], N.get(pId));
						printLog("Process-" + pId + " send request to Process-" + srcId);
				        printLog("Sending request from process-" + pId + " --> N: " + N);
				        printLog("Sending request from process-" + pId + " --> S: " + S);
		                dest.receiveReq(reqNewMsg);
		            } catch (RemoteException e) {
		                throw new RuntimeException(e);    
		            }
				}
				break;
			case "H":			// if holding the token, send it to the requesting process
				S.set(srcId, "R");
				S.set(pId, "O");
				TS.set(srcId, "R");
				TN.set(srcId, reqSeq);
				printLog("Process-" + pId + " sends token (case S=H) to Process-" + srcId);
		        printLog("Sending token from process-" + pId + " --> TN: " + TN);
		        printLog("Sending token from process-" + pId + " --> TS: " + TS);
				sendToken(reqMsg.getSrcAddr());
				break;
		}
	}
	
	@Override
	public void resetState() throws RemoteException {
		this.S = new ArrayList<String>(nProc);
		this.N = new ArrayList<Integer>(nProc);
		token = null;
		execFinished = false;
		
		for(int i = 0; i < nProc; i++) {
			N.add(0);
			S.add("O");
		}
			
		if(pId != 0) {
			for(int i = 0; i < pId; i++) {	// Process before i
				S.set(i, "R");
			}
		} else {
			S.set(0, "H");	// Process 1		
		}
	}
	
	@Override
	public int getProcId() throws RemoteException {
		return pId;
	}
	
	@Override
	public boolean isExecFinished() throws RemoteException {
		return execFinished;
	}
	
	/* Local functions */
	// Send request to those processes that may have the token
	private void sendFeasibleRequest() {
		S.set(pId, "R");
        N.set(pId, N.get(pId) + 1);
  
        for (int i = 0; i < nProc; i++) {
        	if(i == pId) continue;		// Skip current process
        	if(S.get(i) == "R") {
	            Singhal_RMI dest = getProcess(remoteAddrs[i]);
	            try {
	                RequestMsg reqMsg = new RequestMsg(pId, remoteAddrs[pId], N.get(pId));
	                printLog("Process-" + pId + " requesting access to Process-" + i);
	                printLog("Process-" + pId + " --> N: " + N);
	                printLog("Process-" + pId + " --> S: " + S);
	                dest.receiveReq(reqMsg);
	            } catch (RemoteException e) {
	                throw new RuntimeException(e);
	            }
        	}
        }
	}
	

	// Release token after execution
	public void releaseToken() {
        List<Integer> TN = token.getTN();
        List<String> TS = token.getTS();
        int counterO = 0;
        
        TS.set(pId, "O");
       
        for(int i = 0; i < nProc; i++) {	// Update information on other processes
        	if(S.get(i) == "O") counterO++;
        	if(i == pId) continue;
        	if(N.get(i) > TN.get(i)) {
        		TN.set(i, N.get(i));
        		TS.set(i, S.get(i));
        	} else {
        		N.set(i, TN.get(i));
        		S.set(i, TS.get(i));
        	}
        	
        }
    	printLog("------------------------------------------------------");
        printLog("Releasing token from process-" + pId + " --> TN: " + TN);
        printLog("Releasing token from process-" + pId + " --> TS: " + TS);
        printLog("Process-" + pId + " updated --> N: " + N);
        printLog("Process-" + pId + " updated --> S: " + S);
        printLog("------------------------------------------------------");
        
        if(counterO == nProc) {		// Nobody requesting
        	S.set(pId, "H");
        	printLog("Process-" + pId + " set to H, nobody requesting");
        } else {
        	for(int i = 0; i < nProc; i++) {
        		if(i == pId) continue;
        		if(S.get(i) == "R") {
        			printLog("Process-" + pId + " sends token (after release) to Process-" + i);
        			sendToken(remoteAddrs[i]);
        			break;		// only one token sent
        		}
        	}
        }        	
	}
	
	
	// Critical section part, using dummy sleep
	private void criticalSection() {
        int execTime = random.nextInt(MAX_EXEC_DELAY);
        printLog("Entering critical section on Process-" + pId + ", execution time (random) " + execTime + " ms.");

        S.set(pId,"E");		// Process is doing execution
        printLog("Process-" + pId + " executing --> N: " + N);
        printLog("Process-" + pId + " executing --> S: " + S);
        
        try {
            Thread.sleep(execTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        S.set(pId, "O");	// Process finished its execution
        printLog("Process-" + pId + " finished --> N: " + N);
        printLog("Process-" + pId + " finished --> S: " + S);
        
        printLog("Leaving critical section on Process-" + pId);
	}
	
	// Send token to another process
	private void sendToken(String addr) {
        assert token != null;
        Singhal_RMI dest = getProcess(addr);

        try {
            printLog("Process-" + pId + " is sending token to Process-" + dest.getProcId());
            TokenMsg tokenMsg = new TokenMsg(pId, remoteAddrs[pId], token);
            dest.receiveToken(tokenMsg);
            token = null;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

	}
	
	// Check token periodically
	private void checkToken() {
		int counter = 0;
        while (token == null) {
            counter++;
            try {
                Thread.sleep(TOKEN_CHECK_PERIOD);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (counter % 100 == 0) {
                printLog("Process-" + pId + " checking token");
            }
        }
	}
		
	// Get a process from remote or local address
    private Singhal_RMI getProcess(String addr) {
    	Singhal_RMI result = null;
    	try {    		
    		result = (Singhal_RMI) Naming.lookup(addr);    		
    	} catch (RemoteException e) {
    		e.printStackTrace();
    	} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		return result;
    }
    
    // Logging output to console
    public void printLog(String message) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        System.out.println("[" + strDate + "] " + message);
    }
}
