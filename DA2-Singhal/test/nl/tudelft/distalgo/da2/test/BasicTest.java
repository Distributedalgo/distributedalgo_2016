package nl.tudelft.distalgo.da2.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import nl.tudelft.distalgo.da2.message.TokenMsg;
import nl.tudelft.distalgo.da2.process.Singhal_RMI;
import nl.tudelft.distalgo.da2.process.Token;

public class BasicTest {
	
	private TestSetup testSetup;
	
	@Before
	public void initTest() {
		testSetup = new TestSetup();
		testSetup.initTest();
	}
	
	@Test
	public void test() {
		Singhal_RMI p1 = testSetup.getAllProcesses().get(0);
		TestThread th1 = new TestThread(p1);
		Singhal_RMI p2 = testSetup.getAllProcesses().get(1);
		TestThread th2 = new TestThread(p2);
		Singhal_RMI p3 = testSetup.getAllProcesses().get(2);
		TestThread th3 = new TestThread(p3);
		
		try {
			p1.resetState();
			p2.resetState();
			p3.resetState();
			new Thread(th1).start();
			Token token = Token.init(3);
			if(token != null) {
				TokenMsg tokenMsg = new TokenMsg(0,"",token);	// Assign token to process 0
				p1.receiveToken(tokenMsg);
			}
			new Thread(th2).start();
			new Thread(th3).start();
			
			Thread.sleep(15000);
			Assert.assertTrue(p1.isExecFinished());
			Assert.assertTrue(p2.isExecFinished());
			Assert.assertTrue(p3.isExecFinished());
			
		} catch(Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

}
