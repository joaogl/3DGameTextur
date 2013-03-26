package com.game.engine.objlwjgl;


import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.HashMap;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TextureLoader {

    private final static int TEXTURE_LEVEL = 0;
    private HashMap<String, Integer> loadedTextures = new HashMap<String, Integer>();

    public int load(String filename, boolean useTextureAlpha) throws IOException {
        if (loadedTextures.containsKey(filename)) {
            return loadedTextures.get(filename);
        }

        final URL url = getClass().getResource( filename );
		File file = null;
		try {
			file = new File( url.toURI() );
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
        
        File imageFile = file;

        if (!imageFile.exists()) {
            System.err.println("ERROR, FIle " + filename + " does not exist");
        }
        if (!imageFile.canRead()) {
            System.err.println("ERROR, FIle " + filename + " is not readable");
        }

        BufferedImage img = null;
        img = ImageIO.read(imageFile);

        int[] pixels = new int[img.getWidth() * img.getHeight()];
        PixelGrabber grabber = new PixelGrabber(img, 0, 0, img.getWidth(), img.getHeight(), pixels, 0, img.getWidth());
        try {
            grabber.grabPixels();
        } catch (InterruptedException e) {
            System.err.println("InterruptedException while trying to grab pixels, e=" + e);
            e.printStackTrace();
            return -1;
        }

        int bufLen = 0;
        if (useTextureAlpha) {
            bufLen = pixels.length * 4;
        } else {
            bufLen = pixels.length * 3;
        }
        ByteBuffer oglPixelBuf = BufferUtils.createByteBuffer(bufLen);

        for (int y = img.getHeight() - 1; y >= 0; y--) {
            for (int x = 0; x < img.getWidth(); x++) {
                int pixel = pixels[y * img.getWidth() + x];
                oglPixelBuf.put((byte) ((pixel >> 16) & 0xFF));
                oglPixelBuf.put((byte) ((pixel >> 8) & 0xFF));
                oglPixelBuf.put((byte) ((pixel >> 0) & 0xFF));
                if (useTextureAlpha) {
                    oglPixelBuf.put((byte) ((pixel >> 24) & 0xFF));
                }
            }
        }

        oglPixelBuf.flip();

        ByteBuffer temp = ByteBuffer.allocateDirect(4);
        temp.order(ByteOrder.nativeOrder());
        IntBuffer textBuf = temp.asIntBuffer();
        GL11.glGenTextures(textBuf);
        int textureID = textBuf.get(0);

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexEnvf(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D,
                TEXTURE_LEVEL,
                GL11.GL_RGBA8,
                img.getWidth(),
                img.getHeight(),
                0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE,
                oglPixelBuf);

        return textureID;
    }
}
