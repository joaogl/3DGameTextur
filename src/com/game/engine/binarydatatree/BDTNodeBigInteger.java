package com.game.engine.binarydatatree;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;

/**A node that can contain a BigInteger.**/
public class BDTNodeBigInteger extends BDTNode{
	private BigInteger value;
	
	public BDTNodeBigInteger(String name){
		super(name,NT_BIGINT);
		this.value = BigInteger.valueOf(0);
	}
	
	public BDTNodeBigInteger(String name,long value){
		this(name);
		this.value = BigInteger.valueOf(value);
	}
	
	public BDTNodeBigInteger(String name,byte[] value){
		this(name);
		this.value = new BigInteger(value);
	}
	
	@Override
	public int getPayloadSize() {
		return 2 + value.toByteArray().length;
	}
	
	@Override
	public void readNodeContent(IBDTNodeReader reader, DataInputStream stream)
			throws IOException, Exception {
		
		int len = stream.readChar();
		byte[] val = new byte[len];
		
		stream.readFully(val);
		
		this.value = new BigInteger(val);
	}
	
	@Override
	public void writeNodeContent(IBDTNodeWriter writer, DataOutputStream stream)
			throws IOException {
		
		byte[] val = value.toByteArray();
		stream.writeChar(val.length);
		stream.write(val);
		
	}
	
	@Override
	public void printTextualNodeRepresentationToPrintStream(
			PrintStream printer, int depth, boolean sorted) {
		
		printer.println(makeLine(" ",depth) + " BigInteger '" + this.getName() + "', Value:" + this.value.toString(16));
	}
	
	@Override
	public BDTNode copy() {
		return new BDTNodeBigInteger(getName(),this.value.toByteArray());
	}
	
	@Override
	public String getNodeTypeLabel() {
		return "NT_BIGINT";
	}
	
	@Override
	public String getContentStringRepresentation() {
		return this.value.toString(16);
	}
	
	/**@return The wrapped BigInteger.**/
	public BigInteger getValue(){
		return this.value;
	}
	
	/**Replaces the wrapped BigInteger with the given BigInteger.**/
	public void setValue(BigInteger val){
		this.value = val;
	}
}
