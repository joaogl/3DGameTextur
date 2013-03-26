package com.game.engine.binarydatatree;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**A Node that marks the End of Compounds.**/
public class BDTNodeEnd extends BDTNode {
	
	BDTNodeEnd(){
		super("",BDTNode.NT_END);
	}
	
	@Override
	public int getPayloadSize() {
		return 0;
	}
	
	@Override public void readNodeContent(IBDTNodeReader reader, DataInputStream stream) throws IOException {}
	@Override public void writeNodeContent(IBDTNodeWriter writer, DataOutputStream stream) throws IOException {}
	@Override public void printTextualNodeRepresentationToPrintStream(PrintStream printer,int depth, boolean b) {printer.append("/END\n");}

	@Override
	public BDTNode copy() {
		return new BDTNodeEnd();
	}

	@Override
	public String getNodeTypeLabel() {
		return "NT_END";
	}

	@Override
	public String getContentStringRepresentation() {
		return "";
	}
}
