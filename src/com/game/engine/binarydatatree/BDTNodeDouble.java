package com.game.engine.binarydatatree;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**A Node that contains a single Signed Double-Precision Floating Point Value.**/
public class BDTNodeDouble extends BDTNode{
	private double value;
	
	public BDTNodeDouble(String name){
		super(name,BDTNode.NT_DOUBLE);
		this.value = 0;
	}
	
	public BDTNodeDouble(double b){
		this("");
		this.value = b;
	}
	
	public BDTNodeDouble(String name,double b){
		this(name);
		this.value = b;
	}
	
	@Override
	public int getPayloadSize() {
		return 8;
	}

	@Override
	public void readNodeContent(IBDTNodeReader reader, DataInputStream stream)
			throws IOException {
		this.value = stream.readDouble();
	}

	@Override
	public void writeNodeContent(IBDTNodeWriter writer, DataOutputStream stream)
			throws IOException {
		stream.writeDouble(this.value);
	}

	@Override
	public void printTextualNodeRepresentationToPrintStream(
			PrintStream printer, int depth, boolean b) {
		printer.println(makeLine(" ",depth) + " Signed Double-Precision Floating Point Value '" + this.getName() + "', Value: " + this.value);
	}

	public double getValue() {
		return this.value;
	}
	
	public void setValue(double value){
		this.value = value;
	}

	@Override
	public BDTNode copy() {
		return new BDTNodeDouble(getName(),this.value);
	}

	@Override
	public String getNodeTypeLabel() {
		return "NT_DOUBLE";
	}

	@Override
	public String getContentStringRepresentation() {
		return "" + value;
	}



}
