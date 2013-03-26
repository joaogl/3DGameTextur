package com.game.engine.binarydatatree;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**A Node that contains a single Signed Single-Precision Floating Point Value.**/
public class BDTNodeFloat extends BDTNode{
	private float value;
	
	public BDTNodeFloat(String name){
		super(name,BDTNode.NT_FLOAT);
		this.value = 0;
	}
	
	public BDTNodeFloat(float b){
		this("");
		this.value = b;
	}
	
	public BDTNodeFloat(String name,float b){
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
		this.value = stream.readFloat();
	}

	@Override
	public void writeNodeContent(IBDTNodeWriter writer, DataOutputStream stream)
			throws IOException {
		stream.writeFloat(this.value);
	}

	@Override
	public void printTextualNodeRepresentationToPrintStream(
			PrintStream printer, int depth, boolean b) {
		printer.println(makeLine(" ",depth) + " Signed Single-Precision Floating Point Value '" + this.getName() + "', Value: " + this.value);
	}

	public float getValue() {
		return this.value;
	}
	
	public void setValue(float value){
		this.value = value;
	}

	@Override
	public BDTNode copy() {
		return new BDTNodeFloat(getName(),this.value);
	}

	@Override
	public String getNodeTypeLabel() {
		return "NT_FLOAT";
	}


	@Override
	public String getContentStringRepresentation() {
		return "" + value;
	}


}
