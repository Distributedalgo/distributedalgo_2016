package nl.tudelft.distalgo.da2.message;

public class RequestMsg extends Msg {
	
	private static final long serialVersionUID = -7260695467647226336L;

	private int reqSeq;		// request message sequence of a process
	
	public RequestMsg(int srcId, String srcAddr, int reqSeq) {
		super(srcId, srcAddr);
		this.reqSeq = reqSeq;
	}
	
	public int getReqSeq() {
		return reqSeq;
	}
}
