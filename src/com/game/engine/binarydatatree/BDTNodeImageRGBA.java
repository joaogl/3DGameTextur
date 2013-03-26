package com.game.engine.binarydatatree;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;

/**A node that contains an uncompressed native RGBA image buffer.<br>
 * The image databuffer can be directly uploaded into a openGL application!**/
public class BDTNodeImageRGBA extends BDTNode {
	private int width;
	private int height;
	private ByteBuffer data;
	
	public BDTNodeImageRGBA(String name) {
		super(name,BDTNode.NT_IMAGE_RGBA);
		this.width = -1;
		this.height= -1;
		this.data = null;
	}

	@Override
	public int getPayloadSize() {
		return 4 + 4 + (width * height * 4);
	}

	@Override
	public void readNodeContent(IBDTNodeReader reader, DataInputStream stream)
			throws IOException {
		
		width = stream.readInt();
		height= stream.readInt();
		
		data = ByteBuffer.allocateDirect(width * height * 4);
		data.clear();
		
		byte[] cache = new byte[(width * height * 4)];
		stream.readFully(cache);
		data.put(cache);
		data.position(0).limit(cache.length);
		
	}
	
	@Override
	public void writeNodeContent(IBDTNodeWriter writer, DataOutputStream stream)
			throws IOException {
		
		stream.writeInt(width);
		stream.writeInt(height);
		
		WritableByteChannel channel = Channels.newChannel(stream);
		channel.write(data);
	}

	@Override
	public void printTextualNodeRepresentationToPrintStream(
			PrintStream printer, int depth, boolean b) {
		printer.println(makeLine(" ",depth) + " RGBA Image '" + this.getName() + "', Width: " + width + ", Height: " + height);
	}

	@Override
	public BDTNode copy() {
		throw new UnsupportedOperationException("Copying images is not yet implemented.");
	}
	
	/**This Method is currently the only way to transfer picture data into a NT_IMAGE_XXX Node.**/
	public void setImage(BufferedImage bufferedImage){
        width = bufferedImage.getWidth();
        height = bufferedImage.getHeight();
        
        int ai[] = new int[width * height];
        byte abyte0[] = new byte[width * height * 4];
        bufferedImage.getRGB(0, 0, width, height, ai, 0, width);
		data = ByteBuffer.allocateDirect(width * height * 4);
        
        for (int k = 0; k < ai.length; k++)
        {
            int i1 = ai[k] >> 24 & 0xff;
            int k1 = ai[k] >> 16 & 0xff;
            int i2 = ai[k] >> 8 & 0xff;
            int k2 = ai[k] & 0xff;
            
            abyte0[k * 4 + 0] = (byte)k1;
            abyte0[k * 4 + 1] = (byte)i2;
            abyte0[k * 4 + 2] = (byte)k2;
            abyte0[k * 4 + 3] = (byte)i1;
        }

        data.clear();
        data.put(abyte0);
        data.position(0).limit(abyte0.length);
	}

	@Override
	public String getNodeTypeLabel() {
		return "NT_IMAGE_RGBA";
	}
	
	@Override
	public String getContentStringRepresentation() {
		return "["+width+":"+height+"]";
	}

	/**Returns the width of the image, or -1 if there is no data.**/
	public int getWidth(){
		return this.width;
	}
	
	/**Returns the height of the image, or -1 if there is no data.**/
	public int getHeight(){
		return this.height;
	}
	
	/**Returns the databuffer of the image, or <i>null</i> if there is no data.**/
	public ByteBuffer getDataBuffer(){
		return data;
	}
}
