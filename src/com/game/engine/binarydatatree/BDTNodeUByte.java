package com.game.engine.binarydatatree;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**A Node that contains a unsigned Byte.**/
public class BDTNodeUByte extends BDTNode{
	private int value;
	
	public BDTNodeUByte(String name){
		super(name,BDTNode.NT_UBYTE);
		this.value = 0;
	}
	
	public BDTNodeUByte(int b){
		this("");
		this.value = b & 0xFF;
	}
	
	public BDTNodeUByte(String name,int b){
		this(name);
		this.value = b & 0xFF;
	}
	
	@Override
	public int getPayloadSize() {
		return 1;
	}

	@Override
	public void readNodeContent(IBDTNodeReader reader, DataInputStream stream)
			throws IOException {
		this.value = stream.read();
	}

	@Override
	public void writeNodeContent(IBDTNodeWriter writer, DataOutputStream stream)
			throws IOException {
		stream.write(this.value);
	}

	@Override
	public void printTextualNodeRepresentationToPrintStream(
			PrintStream printer, int depth, boolean b) {
		printer.println(makeLine(" ",depth) + " Unsigned Byte '" + this.getName() + "', Value: " + this.value);
	}
	
	@Override
	public BDTNode copy() {
		return new BDTNodeUByte(getName(),this.value);
	}
	
	public int getValue(){
		return this.value & 0xFF;
	}
	
	public void setValue(int value){
		this.value = value & 0xFF;
	}
	
	@Override
	public String getNodeTypeLabel() {
		return "NT_UBYTE";
	}
	
	@Override
	public String getContentStringRepresentation() {
		return "" + (value & 0xFF);
	}
}
