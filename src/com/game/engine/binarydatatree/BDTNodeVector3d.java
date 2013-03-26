package com.game.engine.binarydatatree;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**A node that contains a vector.**/
public class BDTNodeVector3d extends BDTNode{
	private double x;
	private double y;
	private double z;
	
	public BDTNodeVector3d(String name){
		super(name,BDTNode.NT_VECTOR3D);
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	public BDTNodeVector3d(String name,double x,double y,double z){
		this(name);
		this.x = x;
		this.y = y;
		this.z = z;
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
	}
	
	@Override
	public void writeNodeContent(IBDTNodeWriter writer, DataOutputStream stream)
			throws IOException {
		stream.writeDouble(x);
		stream.writeDouble(y);
		stream.writeDouble(z);
	}
	
	@Override
	public void printTextualNodeRepresentationToPrintStream(
			PrintStream printer, int depth, boolean b) {
		printer.println(makeLine(" ",depth) + " Vector3d '" + this.getName() + "', ["+x+":"+y+":"+z+"]");
	}
	
	@Override
	public BDTNode copy() {
		return new BDTNodeVector3d(this.getName(),this.x,this.y,this.z);
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
	
	public void setX(double d){
		this.x = d;
	}
	public void setY(double d){
		this.y = d;
	}
	public void setZ(double d){
		this.z = d;
	}

	@Override
	public String getNodeTypeLabel() {
		return "NT_VECTOR3D";
	}
	
	@Override
	public String getContentStringRepresentation() {
		return "["+x+":"+y+":"+z+"]";
	}
}
