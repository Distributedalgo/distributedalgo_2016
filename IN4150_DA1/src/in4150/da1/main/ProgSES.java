/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in4150.da1.main;

/* Import java RMI package */
import java.rmi.*;

/**
 * Host application SES DA
 * @author xflash
 */
public class ProgSES {

    /**
     * Run server for SES DA program 
     */
    public void run() {

        //read network configuration
        Configuration config = null;
        try {
            config = new PropertiesConfiguration("network.cfg");
        } catch (ConfigurationException e) {
            try {
                config = new PropertiesConfiguration("network.cfg.default");
            } catch (ConfigurationException e2) {
                e2.printStackTrace();
            }
        }
        
        //instantiating InetAddress to resolve local IP
        try{
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e){
            LOGGER.error("Cannot instantiate IP resolver");
            throw new RuntimeException(e);
        }

        assert config != null;
        String[] urls = config.getStringArray("node.url");
        processes = new ArrayList<DA_Schiper_Eggli_Sandoz_RMI>();

        //bind local processes and locate remote ones
        try {
            DA_Schiper_Eggli_Sandoz_RMI process;
            int processIndex = 0;
            for (String url : urls) {
                if (isProcessLocal(url)) {
                    process = new DA_Schiper_Eggli_Sandoz(urls.length, processIndex);
                    LOGGER.debug("Process " + processIndex + " is local.");
                    new Thread((DA_Schiper_Eggli_Sandoz) process).start();
                    Naming.bind(url, process);
                    processes.add(process);
                }
                processIndex++;

            }

            //sleep is needed to instantiate local processes at all machines
            LOGGER.debug("Waiting for remote processes to instantiate...");
            try {
                Thread.sleep(INSTANTIATION_DELAY);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            LOGGER.debug("And looking them up.");

            for (String url : urls) {
                if (!isProcessLocal(url)) {
                    process = (DA_Schiper_Eggli_Sandoz_RMI) Naming.lookup(url);
                    LOGGER.debug("Found remote process with URL " + url);
                    processes.add(process);
                }
            }

        } catch (RemoteException e1) {
            e1.printStackTrace();
        } catch (AlreadyBoundException e2) {
            e2.printStackTrace();
        } catch (NotBoundException e3) {
            e3.printStackTrace();
        } catch (MalformedURLException e4) {
            e4.printStackTrace();
        }
    }

    private boolean isProcessLocal(String url){    
    	
    	boolean isLocal = false;
    	
    	//LOGGER.debug("======================================================================");
    	for (String ipAddress: this.getIpAddresses()){
    		isLocal = isLocal || url.startsWith(RMI_PREFIX + ipAddress);
    	//	LOGGER.debug(ipAddress.toString());
    	}
    	//LOGGER.debug(inetAddress.getHostAddress());
    	//LOGGER.debug("----------------------------------------------------------------------");
    	
    	// special cases.
    	isLocal = isLocal || url.startsWith(RMI_PREFIX + "localhost");
    	isLocal = isLocal || url.startsWith(RMI_PREFIX + "127.0.0.1");
    	isLocal = isLocal || url.startsWith(RMI_PREFIX + "127.0.1.1");
    	isLocal = isLocal || url.startsWith(RMI_PREFIX + inetAddress.getHostAddress());
    	
    	LOGGER.debug("url: " + url + " isLocal: " + isLocal);
    	//LOGGER.debug("======================================================================");

        return isLocal;
    }

    private String[] getIpAddresses() {
        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e5) {
            LOGGER.error("Cannot instantiate IP resolver");
            throw new RuntimeException(e5);
        }

        ArrayList<String> ipAddresses = new ArrayList<String>();

        while (networkInterfaces.hasMoreElements()) {
        	NetworkInterface networkInterface = networkInterfaces.nextElement();

			for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses())
			{
				//if (ipNetworkPrefixLength == interfaceAddress.getNetworkPrefixLength())
				//{
					ipAddresses.add( interfaceAddress.getAddress().getHostAddress().toString() );						
				//}
			}
        }

        String[] result = new String[ipAddresses.size()];

        return ipAddresses.toArray(result);
    }

}

}
