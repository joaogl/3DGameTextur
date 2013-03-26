package com.game.engine.binarydatatree;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**A Node that contains a Boolean.**/
public class BDTNodeBoolean extends BDTNode {
	private boolean value = false;
	
	public BDTNodeBoolean(String name){
		super(name,BDTNode.NT_BOOL);
		this.value = false;
	}
	
	public BDTNodeBoolean(String name, boolean value){
		this(name);
		this.value = value;
	}
	
	public BDTNodeBoolean(boolean value){
		this("");
		this.value = value;
	}

	@Override
	public int getPayloadSize() {
		return 1;
	}
	
	@Override
	public void readNodeContent(IBDTNodeReader reader, DataInputStream stream)
			throws IOException {
		this.value = stream.readBoolean();
	}
	
	@Override
	public void writeNodeContent(IBDTNodeWriter writer, DataOutputStream stream)
			throws IOException {
		stream.writeBoolean(this.value);
	}
	
	@Override
	public void printTextualNodeRepresentationToPrintStream(
			PrintStream printer, int depth, boolean b) {
		printer.println(makeLine(" ",depth) + " Boolean '" + this.getName() + "', Value:" + this.value);
		
	}
	
	public void setValue(boolean b){
		this.value = b;
	}
	
	public boolean getValue(){
		return this.value;
	}

	@Override
	public BDTNode copy() {
		return new BDTNodeBoolean(getName(),value);
	}

	@Override
	public String getNodeTypeLabel() {
		return "NT_BOOL";
	}

	@Override
	public String getContentStringRepresentation() {
		return "" + value;
	}
}
