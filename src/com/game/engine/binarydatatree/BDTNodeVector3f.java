package com.game.engine.binarydatatree;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**A node that contains a vector.**/
public class BDTNodeVector3f extends BDTNode{
	private float x;
	private float y;
	private float z;
	
	public BDTNodeVector3f(String name){
		super(name,BDTNode.NT_VECTOR3F);
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	public BDTNodeVector3f(String name,float x,float y,float z){
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
		x = stream.readFloat();
		y = stream.readFloat();
		z = stream.readFloat();
	}
	
	@Override
	public void writeNodeContent(IBDTNodeWriter writer, DataOutputStream stream)
			throws IOException {
		stream.writeFloat(x);
		stream.writeFloat(y);
		stream.writeFloat(z);
	}
	
	@Override
	public void printTextualNodeRepresentationToPrintStream(
			PrintStream printer, int depth, boolean b) {
		printer.println(makeLine(" ",depth) + " Vector3f '" + this.getName() + "', ["+x+":"+y+":"+z+"]");
	}
	
	@Override
	public BDTNode copy() {
		return new BDTNodeVector3f(this.getName(),this.x,this.y,this.z);
	}
	
	public float getX(){
		return this.x;
	}
	public float getY(){
		return this.y;
	}
	public float getZ(){
		return this.z;
	}
	
	public void setX(float d){
		this.x = d;
	}
	public void setY(float d){
		this.y = d;
	}
	public void setZ(float d){
		this.z = d;
	}

	@Override
	public String getNodeTypeLabel() {
		return "NT_VECTOR3F";
	}
	
	@Override
	public String getContentStringRepresentation() {
		return "["+x+":"+y+":"+z+"]";
	}
}
