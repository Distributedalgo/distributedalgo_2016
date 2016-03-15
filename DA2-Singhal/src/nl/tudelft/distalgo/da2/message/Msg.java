package nl.tudelft.distalgo.da2.message;

import java.io.Serializable;

public abstract class Msg implements Serializable {

	private static final long serialVersionUID = -893700662264818780L;

	private int srcId;
	private String srcAddr;
	
	public Msg(int srcId, String srcAddr) {
		this.srcId = srcId;
		this.srcAddr = srcAddr;
	}

	public int getSrcId() {
		return srcId;
	}

	public String getSrcAddr() {
		return srcAddr;
	}
	
}
