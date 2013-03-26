package com.game.engine.binarydatatree;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**A Node that contains a signed Byte.**/
public class BDTNodeSByte extends BDTNode{
	private byte value;
	
	public BDTNodeSByte(String name){
		super(name,BDTNode.NT_SBYTE);
		this.value = 0;
	}
	
	public BDTNodeSByte(byte b){
		this("");
		this.value = b;
	}
	
	public BDTNodeSByte(String name,byte b){
		this(name);
		this.value = b;
	}
	
	@Override
	public int getPayloadSize() {
		return 1;
	}

	@Override
	public void readNodeContent(IBDTNodeReader reader, DataInputStream stream)
			throws IOException {
		this.value = stream.readByte();
	}

	@Override
	public void writeNodeContent(IBDTNodeWriter writer, DataOutputStream stream)
			throws IOException {
		stream.writeByte(this.value);
	}

	@Override
	public void printTextualNodeRepresentationToPrintStream(
			PrintStream printer, int depth, boolean b) {
		printer.println(makeLine(" ",depth) + " Signed Byte '" + this.getName() + "', Value: " + this.value);
	}

	public byte getValue() {
		return this.value;
	}
	
	public void setValue(byte value){
		this.value = value;
	}

	@Override
	public BDTNode copy() {
		return new BDTNodeSByte(getName(),this.value);
	}
	
	@Override
	public String getNodeTypeLabel() {
		return "NT_SBYTE";
	}
	
	@Override
	public String getContentStringRepresentation() {
		return "" + value;
	}
}
