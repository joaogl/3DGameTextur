package com.game.engine.binarydatatree;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

/**A node that contains an array of signed 8-Byte Long-Integer Values.**/
public class BDTNodeLongArray extends BDTNode {
	private long[] value;
	
	public BDTNodeLongArray(String name){
		super(name,BDTNode.NT_LONG_ARRAY);
		this.value = new long[0];
	}
	
	public BDTNodeLongArray(long[] b){
		this("");
		this.value = b;
	}
	
	public BDTNodeLongArray(String name,long[] b){
		this(name);
		this.value = b;
	}
	
	@Override
	public int getPayloadSize() {
		return 4 + value.length * 8;
	}

	@Override
	public void readNodeContent(IBDTNodeReader reader, DataInputStream stream)
			throws IOException {
		int size = stream.readInt();
		this.value = new long[size];
		
		for(int i = 0; i < size; i++)
			this.value[i] = stream.readLong();
	}

	@Override
	public void writeNodeContent(IBDTNodeWriter writer, DataOutputStream stream)
			throws IOException {
		stream.writeInt(this.value.length);
		for(int i = 0; i < this.value.length; i++)
			stream.writeLong(this.value[i]);
	}

	@Override
	public void printTextualNodeRepresentationToPrintStream(
			PrintStream printer, int depth, boolean b) {
		printer.println(makeLine(" ",depth) + " Signed Long Integer Array '" + this.getName() + "', Array Size: " + this.value.length);
	}
	
	public long[] getValue() {
		return this.value;
	}
	
	public void setValue(long[] value){
		this.value = value;
	}
	
	public void setValue(int i ,long v){
		this.value[i] = v;
	}
	
	public long getValue(int i){
		return this.value[i];
	}
	
	@Override
	public BDTNode copy() {
		BDTNodeLongArray array = new BDTNodeLongArray(getName());
		long[] v = new long[this.value.length];
		System.arraycopy(this.value, 0, v, 0, this.value.length);
		array.setValue(v);
		return array;
	}

	@Override
	public String getNodeTypeLabel() {
		return "NT_LONG_ARRAY";
	}
	
	@Override
	public String getContentStringRepresentation() {
		return Arrays.toString(value);
	}
}
