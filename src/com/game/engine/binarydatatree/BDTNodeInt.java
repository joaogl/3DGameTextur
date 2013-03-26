package com.game.engine.binarydatatree;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**A node that contains a single Signed 4-Byte Integer Value.**/
public class BDTNodeInt extends BDTNode{
	private int value;
	
	public BDTNodeInt(String name){
		super(name,BDTNode.NT_INT);
		this.value = 0;
	}
	
	public BDTNodeInt(int b){
		this("");
		this.value = b;
	}
	
	public BDTNodeInt(String name,int b){
		this(name);
		this.value = b;
	}
	
	@Override
	public int getPayloadSize() {
		return 4;
	}

	@Override
	public void readNodeContent(IBDTNodeReader reader, DataInputStream stream)
			throws IOException {
		this.value = stream.readInt();
	}

	@Override
	public void writeNodeContent(IBDTNodeWriter writer, DataOutputStream stream)
			throws IOException {
		stream.writeInt(this.value);
	}

	@Override
	public void printTextualNodeRepresentationToPrintStream(
			PrintStream printer, int depth, boolean b) {
		printer.println(makeLine(" ",depth) + " Signed Integer '" + this.getName() + "', Value: " + this.value);
	}

	public int getValue() {
		return this.value;
	}
	
	public void setValue(int value){
		this.value = value;
	}

	@Override
	public BDTNode copy() {
		return new BDTNodeInt(getName(),this.value);
	}

	@Override
	public String getNodeTypeLabel() {
		return "NT_INT";
	}

	@Override
	public String getContentStringRepresentation() {
		return "" + value;
	}


}
