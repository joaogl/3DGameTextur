package com.game.engine.binarydatatree;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

/**A node that can contain a small bytearray up to 65535 bytes.**/
public class BDTNodeByteArray extends BDTNode{
	private byte[] value;

	public BDTNodeByteArray(String name){
		super(name,BDTNode.NT_SMALL_BYTEARRAY);
		this.value = new byte[0];
	}
	
	public BDTNodeByteArray(byte[] b){
		this("");
		this.value = b;
	}
	
	public BDTNodeByteArray(String name,byte[] b){
		this(name);
		this.value = b;
	}
	
	@Override
	public int getPayloadSize() {
		return 2 + value.length;
	}

	@Override
	public void readNodeContent(IBDTNodeReader reader, DataInputStream stream)
			throws IOException {
		int size = stream.readChar();
		this.value = new byte[size];
		stream.readFully(value);
	}

	@Override
	public void writeNodeContent(IBDTNodeWriter writer, DataOutputStream stream)
			throws IOException {
		stream.writeChar(value.length);
		stream.write(value);
	}

	@Override
	public void printTextualNodeRepresentationToPrintStream(
			PrintStream printer, int depth, boolean b) {
		printer.println(makeLine(" ",depth) + " Small Byte Array '" + this.getName() + "', Array Size: " + this.value.length);
	}
	
	/**@return The wrapped bytearray in this node.**/
	public byte[] getArray() {
		return this.value;
	}
	
	/**Replaces the wrapped array in this node with the given one.**/
	public void setArray(byte[] value){
		this.value = value;
	}
	
	/**Set's an value in the bytearray to the given Value.**/
	public void setArrayValue(int i,byte b){
		this.value[i] = b;
	}
	
	/**@return The length of the wrapped bytearray.**/
	public int getArrayLength(){
		return this.value.length;
	}
	
	@Override
	public BDTNode copy() {
		BDTNodeByteArray array = new BDTNodeByteArray(getName());
		byte[] v = new byte[this.value.length];
		System.arraycopy(this.value, 0, v, 0, this.value.length);
		array.setArray(v);
		return array;
	}

	@Override
	public String getNodeTypeLabel() {
		return "NT_SMALL_BYTEARRAY";
	}

	@Override
	public String getContentStringRepresentation() {
		return Arrays.toString(value);
	}


}
