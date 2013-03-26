package com.game.engine.binarydatatree;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

/**A Node that contains an Array of Signed Single-Precision Floating-Point Values.**/
public class BDTNodeFloatArray extends BDTNode {
	private float[] value;
	
	public BDTNodeFloatArray(String name){
		super(name,BDTNode.NT_FLOAT_ARRAY);
		this.value = new float[0];
	}
	
	public BDTNodeFloatArray(float[] b){
		this("");
		this.value = b;
	}
	
	public BDTNodeFloatArray(String name,float[] b){
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
		this.value = new float[size];
		
		for(int i = 0; i < size; i++)
			this.value[i] = stream.readFloat();
	}

	@Override
	public void writeNodeContent(IBDTNodeWriter writer, DataOutputStream stream)
			throws IOException {
		stream.writeInt(this.value.length);
		for(int i = 0; i < this.value.length; i++)
			stream.writeFloat(this.value[i]);
	}

	@Override
	public void printTextualNodeRepresentationToPrintStream(
			PrintStream printer, int depth, boolean b) {
		printer.println(makeLine(" ",depth) + " Signed Single-Precision Floating Point Number Array '" + this.getName() + "', Array Size: " + this.value.length);
	}
	
	public float[] getValue() {
		return this.value;
	}
	
	public void setValue(float[] value){
		this.value = value;
	}
	
	public void setValue(int i ,float v){
		this.value[i] = v;
	}
	
	public float getValue(int i){
		return this.value[i];
	}
	
	@Override
	public BDTNode copy() {
		BDTNodeFloatArray array = new BDTNodeFloatArray(getName());
		float[] v = new float[this.value.length];
		System.arraycopy(this.value, 0, v, 0, this.value.length);
		array.setValue(v);
		return array;
	}

	@Override
	public String getNodeTypeLabel() {
		return "NT_FLOAT_ARRAY";
	}
	
	@Override
	public String getContentStringRepresentation() {
		return Arrays.toString(value);
	}
}
