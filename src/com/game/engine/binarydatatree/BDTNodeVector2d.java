package com.game.engine.binarydatatree;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**A node that contains a vector.**/
public class BDTNodeVector2d extends BDTNode{
	private double x;
	private double y;
	
	public BDTNodeVector2d(String name){
		super(name,BDTNode.NT_VECTOR2D);
		this.x = 0;
		this.y = 0;
	}
	
	public BDTNodeVector2d(String name,double x,double y){
		this(name);
		this.x = x;
		this.y = y;
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
	}
	
	@Override
	public void writeNodeContent(IBDTNodeWriter writer, DataOutputStream stream)
			throws IOException {
		stream.writeDouble(x);
		stream.writeDouble(y);
	}
	
	@Override
	public void printTextualNodeRepresentationToPrintStream(
			PrintStream printer, int depth, boolean b) {
		printer.println(makeLine(" ",depth) + " Vector2d '" + this.getName() + "', ["+x+":"+y+"]");
	}
	
	@Override
	public BDTNode copy() {
		return new BDTNodeVector2d(this.getName(),this.x,this.y);
	}
	
	public double getX(){
		return this.x;
	}
	public double getY(){
		return this.y;
	}
	
	public void setX(double d){
		this.x = d;
	}
	public void setY(double d){
		this.y = d;
	}

	@Override
	public String getNodeTypeLabel() {
		return "NT_VECTOR2D";
	}
	
	@Override
	public String getContentStringRepresentation() {
		return "["+x+":"+y+"]";
	}
}
