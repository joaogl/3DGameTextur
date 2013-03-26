package com.game.engine.binarydatatree;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**A node that contains a UTF-8 String.**/
public class BDTNodeString extends BDTNode {
	private String value;
	
	public BDTNodeString(String name){
		super(name,BDTNode.NT_STRING);
		this.value = "";
	}
	
	public BDTNodeString(String name,String b){
		this(name);
		this.value = b;
	}
	
	@Override
	public int getPayloadSize() {
		return 2 + value.getBytes().length;
	}

	@Override
	public void readNodeContent(IBDTNodeReader reader, DataInputStream stream)
			throws IOException {
		this.value = stream.readUTF();
	}

	@Override
	public void writeNodeContent(IBDTNodeWriter writer, DataOutputStream stream)
			throws IOException {
		stream.writeUTF(this.value);
	}

	@Override
	public void printTextualNodeRepresentationToPrintStream(
			PrintStream printer, int depth, boolean b) {
		printer.println(makeLine(" ",depth) + " String '" + this.getName() + "', Value: " + this.value);
	}

	public String getValue() {
		return this.value;
	}
	
	public void setValue(String value){
		this.value = value;
	}
	
	@Override
	public BDTNode copy() {
		return new BDTNodeString(getName(),this.value);
	}
	
	@Override
	public String getNodeTypeLabel() {
		return "NT_STRING";
	}
	
	@Override
	public String getContentStringRepresentation() {
		return value;
	}
}
