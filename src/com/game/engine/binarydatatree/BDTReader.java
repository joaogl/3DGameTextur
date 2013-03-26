package com.game.engine.binarydatatree;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/**
 * BinaryDataTree Reader.<br>
 * Use this class to read BDT's from a stream.
 * <hr>
 * @author Longor1996
 **/
public class BDTReader implements IBDTNodeReader{
	private DataInputStream inputStream;
	private int nodesRead = 0;
	
	public BDTReader(InputStream given_input) throws IOException {
		if(given_input == null)
			throw new IllegalArgumentException("The given InputStream cannot be Null.");
		
		//Read Compression Byte
		int compressed = given_input.read();
		
		if(compressed == 1){
			//System.out.println("[BDTReader] Reading compressed Stream!");
			
			// The BDT is compressed! Create a new GZIPInputStream to wrap the InputStream and use it to create a new DataInputstream.
			inputStream = new DataInputStream(new GZIPInputStream(given_input));
		}else{
			//System.out.println("[BDTReader] Reading uncompressed Stream!");
			
			// The BDT is not compressed. Create a new DataInputStream.
			inputStream = new DataInputStream(given_input);
		}
	}
	
	/**Writes the given Node and all it's contents to an Outputstream.
	 * @throws Exception **/
	public BDTNode readNode() throws Exception{
		int type = inputStream.read();
		
		//System.out.println("[BDTReader] Reading a " + type);
		
		if(type == BDTNode.NT_END){
			//End Tags are instantly returned.
			return new BDTNodeEnd();
		}else{
			nodesRead++;
			
			//Read the Name of the named Tag.
			String name = inputStream.readUTF();
			
			//Create a new named Node
			BDTNode node = BDTNode.createNode(type,name);
			
			//let the node read it content's.
			node.readNodeContent(this, inputStream);
			return node;
		}
	}

	public int getTotalReadNodes() {
		return nodesRead;
	}
}
