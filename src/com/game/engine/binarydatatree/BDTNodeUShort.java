package com.game.engine.binarydatatree;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**A node that contains an unsigned 2-Byte Short-Integer Value.**/
public class BDTNodeUShort extends BDTNode {

	private char value;
	
	public BDTNodeUShort(String name){
		super(name,BDTNode.NT_USHORT);
		this.value = 0;
	}
	
	public BDTNodeUShort(char b){
		this("");
		this.value = b;
	}
	
	public BDTNodeUShort(String name,char b){
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
		this.value = stream.readChar();
	}

	@Override
	public void writeNodeContent(IBDTNodeWriter writer, DataOutputStream stream)
			throws IOException {
		stream.writeChar(this.value);
	}

	@Override
	public void printTextualNodeRepresentationToPrintStream(
			PrintStream printer, int depth, boolean b) {
		printer.println(makeLine(" ",depth) + " Unsigned Short '" + this.getName() + "', Value: " + (int)this.value);
	}

	public char getValue() {
		return this.value;
	}
	
	public void setValue(char value){
		this.value = value;
	}
	
	@Override
	public BDTNode copy() {
		return new BDTNodeUShort(getName(),this.value);
	}

	@Override
	public String getNodeTypeLabel() {
		return "NT_USHORT";
	}
	
	@Override
	public String getContentStringRepresentation() {
		return "" + (int)value;
	}
}
