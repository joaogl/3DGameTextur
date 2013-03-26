package com.game.engine.binarydatatree;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**A node that contains a signed 2-Byte Short Integer.**/
public class BDTNodeSShort extends BDTNode {
	private short value;
	
	public BDTNodeSShort(String name){
		super(name,BDTNode.NT_SSHORT);
		this.value = 0;
	}
	
	public BDTNodeSShort(short b){
		this("");
		this.value = b;
	}
	
	public BDTNodeSShort(String name,short b){
		this(name);
		this.value = b;
	}
	
	@Override
	public int getPayloadSize() {
		return 2;
	}

	@Override
	public void readNodeContent(IBDTNodeReader reader, DataInputStream stream)
			throws IOException {
		this.value = stream.readShort();
	}

	@Override
	public void writeNodeContent(IBDTNodeWriter writer, DataOutputStream stream)
			throws IOException {
		stream.writeShort(this.value);
	}

	@Override
	public void printTextualNodeRepresentationToPrintStream(
			PrintStream printer, int depth, boolean b) {
		printer.println(makeLine(" ",depth) + " Signed Short '" + this.getName() + "', Value: " + this.value);
	}

	public short getValue() {
		return this.value;
	}
	
	public void setValue(short value){
		this.value = value;
	}

	@Override
	public BDTNode copy() {
		return new BDTNodeSShort(getName(),this.value);
	}
	
	@Override
	public String getNodeTypeLabel() {
		return "NT_SSHORT";
	}
	
	@Override
	public String getContentStringRepresentation() {
		return "" + value;
	}

}
