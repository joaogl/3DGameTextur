package com.game.engine.binarydatatree;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**A node that contains a vector.**/
public class BDTNodeVector4f extends BDTNode{
	private float x;
	private float y;
	private float z;
	private float w;
	
	public BDTNodeVector4f(String name){
		super(name,BDTNode.NT_VECTOR4F);
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.w = 0;
	}
	
	public BDTNodeVector4f(String name,float x,float y,float z,float w){
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
		x = stream.readFloat();
		y = stream.readFloat();
		z = stream.readFloat();
		w = stream.readFloat();
	}
	
	@Override
	public void writeNodeContent(IBDTNodeWriter writer, DataOutputStream stream)
			throws IOException {
		stream.writeFloat(x);
		stream.writeFloat(y);
		stream.writeFloat(z);
		stream.writeFloat(w);
	}
	
	@Override
	public void printTextualNodeRepresentationToPrintStream(
			PrintStream printer, int depth, boolean b) {
		printer.println(makeLine(" ",depth) + " Vector4f '" + this.getName() + "', ["+x+":"+y+":"+z+":"+w+"]");
	}
	
	@Override
	public BDTNode copy() {
		return new BDTNodeVector4f(this.getName(),this.x,this.y,this.z,this.w);
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
	public float getW(){
		return this.w;
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
	public void setW(float d){
		this.w = d;
	}
	
	@Override
	public String getNodeTypeLabel() {
		return "NT_VECTOR4F";
	}
	
	@Override
	public String getContentStringRepresentation() {
		return "["+x+":"+y+":"+z+":"+w+"]";
	}
}
