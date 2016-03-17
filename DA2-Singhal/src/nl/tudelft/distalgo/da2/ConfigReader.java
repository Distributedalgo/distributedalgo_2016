package nl.tudelft.distalgo.da2;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConfigReader {
	
	private final Properties addrsConfig;
	
	public ConfigReader(String filename) throws IOException {
		addrsConfig = new Properties();
		try {
			InputStream inputConfigFile = new FileInputStream(filename);
			addrsConfig.load(inputConfigFile);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}	
	
	public List<String> getAddrs() {
		List<String> addrs = new ArrayList<String>();
		String addr = null;
		int i = 0;
		do {
			addr = addrsConfig.getProperty("addr." + i);
			if(addr != null) {
				addrs.add(addr);
			}
			i++;
		} while(addr != null);
		
		return addrs;
	}
}
