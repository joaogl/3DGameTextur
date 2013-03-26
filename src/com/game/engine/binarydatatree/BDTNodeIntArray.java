package com.game.engine.binarydatatree;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

/**A node that contains an array of Signed 4-byte Integer Values.**/
public class BDTNodeIntArray extends BDTNode {
	private int[] value;
	
	public BDTNodeIntArray(String name){
		super(name,BDTNode.NT_INT);
		this.value = new int[0];
	}
	
	public BDTNodeIntArray(int[] b){
		this("");
		this.value = b;
	}
	
	public BDTNodeIntArray(String name,int[] b){
		this(name);
		this.value = b;
	}
	
	@Override
	public int getPayloadSize() {
		return 4 + value.length * 4;
	}

	@Override
	public void readNodeContent(IBDTNodeReader reader, DataInputStream stream)
			throws IOException {
		int size = stream.readInt();
		this.value = new int[size];
		
		for(int i = 0; i < size; i++)
			this.value[i] = stream.readInt();
	}

	@Override
	public void writeNodeContent(IBDTNodeWriter writer, DataOutputStream stream)
			throws IOException {
		stream.writeInt(this.value.length);
		for(int i = 0; i < this.value.length; i++)
			stream.writeInt(this.value[i]);
	}

	@Override
	public void printTextualNodeRepresentationToPrintStream(
			PrintStream printer, int depth, boolean b) {
		printer.println(makeLine(" ",depth) + " Signed Integer Array '" + this.getName() + "', Array Size: " + this.value.length);
	}
	
	public int[] getValue() {
		return this.value;
	}
	
	public void setValue(int[] value){
		this.value = value;
	}
	
	public void setValue(int i ,int v){
		this.value[i] = v;
	}
	
	public int getValue(int i){
		return this.value[i];
	}
	
	@Override
	public BDTNode copy() {
		BDTNodeIntArray array = new BDTNodeIntArray(getName());
		int[] v = new int[this.value.length];
		System.arraycopy(this.value, 0, v, 0, this.value.length);
		array.setValue(v);
		return array;
	}

	@Override
	public String getNodeTypeLabel() {
		return "NT_INT_ARRAY";
	}
	
	@Override
	public String getContentStringRepresentation() {
		return Arrays.toString(value);
	}
}
