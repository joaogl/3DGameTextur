package com.game.engine.binarydatatree;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**A node that contains a single signed 8-Byte Long Integer Value.**/
public class BDTNodeLong extends BDTNode {
	private long value;
	
	public BDTNodeLong(String name){
		super(name,BDTNode.NT_LONG);
		this.value = 0;
	}
	
	public BDTNodeLong(long b){
		this("");
		this.value = b;
	}
	
	public BDTNodeLong(String name,long b){
		this(name);
		this.value = b;
	}
	
	@Override
	public int getPayloadSize() {
		return 8;
	}

	@Override
	public void readNodeContent(IBDTNodeReader reader, DataInputStream stream)
			throws IOException {
		this.value = stream.readLong();
	}

	@Override
	public void writeNodeContent(IBDTNodeWriter writer, DataOutputStream stream)
			throws IOException {
		stream.writeLong(this.value);
	}

	@Override
	public void printTextualNodeRepresentationToPrintStream(
			PrintStream printer, int depth, boolean b) {
		printer.println(makeLine(" ",depth) + " Signed Long Integer '" + this.getName() + "', Value: " + this.value);
	}

	public long getValue() {
		return this.value;
	}
	
	public void setValue(long value){
		this.value = value;
	}

	@Override
	public BDTNode copy() {
		return new BDTNodeLong(getName(),this.value);
	}

	@Override
	public String getNodeTypeLabel() {
		return "NT_LONG";
	}
	
	@Override
	public String getContentStringRepresentation() {
		return "" + value;
	}
}
