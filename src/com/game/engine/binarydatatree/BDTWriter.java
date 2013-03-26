package com.game.engine.binarydatatree;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * BinaryDataTree Writer.<br>
 * Use this class to write BDT's to a stream.
 * <hr>
 * @author Longor1996
 **/
public class BDTWriter implements AutoCloseable,IBDTNodeWriter{
	private DataOutputStream outputStream;
	private boolean autoClose = false;
	
	/**
	 * Constructor to create a new BinaryDataTree Writer.<br>
	 * The writer can be used to write BinaryDataTree's into any given outputstream.<br>
	 * @param given_output The Outputstream to write to.
	 * @param compressed If this is true, the writer will wrap the outputStream with an GZIPOutputStream.
	 * @param autoClose If this is true, the writer will close the given_output when it is closed/finalized.
	 * Do not set this to true if you want to use the given stream again after writing the BDT.
	 **/
	public BDTWriter(OutputStream given_output,boolean compressed,boolean autoClose) throws IOException {
		if(given_output == null)
			throw new IllegalArgumentException("The given InputStream cannot be Null.");
		
		given_output.write(compressed ? 1 : 0);
		given_output.flush();
		
		if(compressed)
			outputStream = new DataOutputStream(new GZIPOutputStream(given_output));
		else
			outputStream = new DataOutputStream(given_output);
		
		this.autoClose = autoClose;
	}
	
	/**Writes the given Node and all it's contents to an outputstream.**/
	public void writeNode(BDTNode node) throws IOException{
		
		//System.out.println("[BDTWriter] Writing a " + node.getType());
		
		if(node.getType() == BDTNode.NT_END){
			outputStream.write(BDTNode.NT_END);
		}else{
			outputStream.write(node.getType());
			outputStream.writeUTF(node.getName());
			node.writeNodeContent(this, outputStream);
		}
		
		outputStream.flush();
	}
	
	@Override
	public void close() throws Exception {
		outputStream.flush();
		
		if(this.autoClose){
			outputStream.close();
		}
	}
}
