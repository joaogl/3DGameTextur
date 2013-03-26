package com.game.engine.binarydatatree;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**A node that contains a vector.**/
public class BDTNodeVector2f extends BDTNode{
	private float x;
	private float y;
	
	public BDTNodeVector2f(String name){
		super(name,BDTNode.NT_VECTOR2F);
		this.x = 0;
		this.y = 0;
	}
	
	public BDTNodeVector2f(String name,float x,float y){
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
		x = stream.readFloat();
		y = stream.readFloat();
	}
	
	@Override
	public void writeNodeContent(IBDTNodeWriter writer, DataOutputStream stream)
			throws IOException {
		stream.writeFloat(x);
		stream.writeFloat(y);
	}
	
	@Override
	public void printTextualNodeRepresentationToPrintStream(
			PrintStream printer, int depth, boolean b) {
		printer.println(makeLine(" ",depth) + " Vector2f '" + this.getName() + "', ["+x+":"+y+"]");
	}
	
	@Override
	public BDTNode copy() {
		return new BDTNodeVector2f(this.getName(),this.x,this.y);
	}
	
	public float getX(){
		return this.x;
	}
	public float getY(){
		return this.y;
	}
	
	public void setX(float d){
		this.x = d;
	}
	public void setY(float d){
		this.y = d;
	}

	@Override
	public String getNodeTypeLabel() {
		return "NT_VECTOR2F";
	}
	
	@Override
	public String getContentStringRepresentation() {
		return "["+x+":"+y+"]";
	}
}
