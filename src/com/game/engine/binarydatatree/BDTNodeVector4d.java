package com.game.engine.binarydatatree;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**A node that contains a vector.**/
public class BDTNodeVector4d extends BDTNode{
	private double x;
	private double y;
	private double z;
	private double w;
	
	public BDTNodeVector4d(String name){
		super(name,BDTNode.NT_VECTOR4D);
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.w = 0;
	}
	
	public BDTNodeVector4d(String name,double x,double y,double z,double w){
		this(name);
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	@Override
	public int getPayloadSize() {
		return 32;
	}
	
	@Override
	public void readNodeContent(IBDTNodeReader reader, DataInputStream stream)
			throws IOException {
		x = stream.readDouble();
		y = stream.readDouble();
		z = stream.readDouble();
		w = stream.readDouble();
	}
	
	@Override
	public void writeNodeContent(IBDTNodeWriter writer, DataOutputStream stream)
			throws IOException {
		stream.writeDouble(x);
		stream.writeDouble(y);
		stream.writeDouble(z);
		stream.writeDouble(w);
	}
	
	@Override
	public void printTextualNodeRepresentationToPrintStream(
			PrintStream printer, int depth, boolean b) {
		printer.println(makeLine(" ",depth) + " Vector4d '" + this.getName() + "', ["+x+":"+y+":"+z+":"+w+"]");
	}
	
	@Override
	public BDTNode copy() {
		return new BDTNodeVector4d(this.getName(),this.x,this.y,this.z,this.w);
	}
	
	public double getX(){
		return this.x;
	}
	public double getY(){
		return this.y;
	}
	public double getZ(){
		return this.z;
	}
	public double getW(){
		return this.w;
	}
	
	public void setX(double d){
		this.x = d;
	}
	public void setY(double d){
		this.y = d;
	}
	public void setZ(double d){
		this.z = d;
	}
	public void setW(double d){
		this.w = d;
	}

	@Override
	public String getNodeTypeLabel() {
		return "NT_VECTOR4D";
	}
	
	@Override
	public String getContentStringRepresentation() {
		return "["+x+":"+y+":"+z+":"+w+"]";
	}
}
