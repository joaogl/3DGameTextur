package com.game.engine.binarydatatree;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

/**A Node that contains an Array of Signed Double-Precision Floating-Point Values.**/
public class BDTNodeDoubleArray extends BDTNode {
	private double[] value;
	
	public BDTNodeDoubleArray(String name){
		super(name,BDTNode.NT_DOUBLE_ARRAY);
		this.value = new double[0];
	}
	
	public BDTNodeDoubleArray(double[] b){
		this("");
		this.value = b;
	}
	
	public BDTNodeDoubleArray(String name,double[] b){
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
		this.value = new double[size];
		
		for(int i = 0; i < size; i++)
			this.value[i] = stream.readDouble();
	}

	@Override
	public void writeNodeContent(IBDTNodeWriter writer, DataOutputStream stream)
			throws IOException {
		stream.writeInt(this.value.length);
		for(int i = 0; i < this.value.length; i++)
			stream.writeDouble(this.value[i]);
	}

	@Override
	public void printTextualNodeRepresentationToPrintStream(
			PrintStream printer, int depth, boolean b) {
		printer.println(makeLine(" ",depth) + " Signed Double-Precision Floating Point Number Array '" + this.getName() + "', Array Size: " + this.value.length);
	}
	
	public double[] getValue() {
		return this.value;
	}
	
	public void setValue(double[] value){
		this.value = value;
	}
	
	public void setValue(int i ,double v){
		this.value[i] = v;
	}
	
	public double getValue(int i){
		return this.value[i];
	}
	
	@Override
	public BDTNode copy() {
		BDTNodeDoubleArray array = new BDTNodeDoubleArray(getName());
		double[] v = new double[this.value.length];
		System.arraycopy(this.value, 0, v, 0, this.value.length);
		array.setValue(v);
		return array;
	}

	@Override
	public String getNodeTypeLabel() {
		return "NT_DOUBLE_ARRAY";
	}
	
	@Override
	public String getContentStringRepresentation() {
		return Arrays.toString(value);
	}
}
