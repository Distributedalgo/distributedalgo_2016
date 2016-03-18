package nl.tudelft.distalgo.da2.message;

import nl.tudelft.distalgo.da2.process.Token;

public class TokenMsg extends Msg {

	private static final long serialVersionUID = -9184095428613618322L;
	
	private Token token;
	
	public TokenMsg(int srcId, String srcAddr, Token token) {
		super(srcId, srcAddr);
		this.token = token;
	}
	
	public Token getToken() {
		return token;
	}
}
