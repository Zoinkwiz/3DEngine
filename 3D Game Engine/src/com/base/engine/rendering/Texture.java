package com.base.engine.rendering;
import static org.lwjgl.opengl.GL11.*;
//import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
//import static org.lwjgl.stb.STBImage.stbi_failure_reason;
//import static org.lwjgl.stb.STBImage.stbi_load;
//import static org.lwjgl.system.MemoryStack.stackPush;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
//import java.nio.IntBuffer;
import java.util.HashMap;

import javax.imageio.ImageIO;

//import org.lwjgl.system.MemoryStack;

import com.base.engine.core.Util;
import com.base.engine.rendering.resourceManagement.TextureResource;
public class Texture {
	
	private static HashMap<String, TextureResource> loadedTextures = new HashMap<String, TextureResource>();
	private TextureResource resource;
	String fileName;
	
	//WORK OUT OPTIMUM
	public Texture(String fileName) {
		this.fileName = fileName;
		TextureResource oldResource = loadedTextures.get(fileName);
		
		if(oldResource != null) {
			resource = oldResource;
			resource.addReference();
		} else {
			resource = loadTexture(fileName);
			loadedTextures.put(fileName, resource);
		}
	}
	

	@Override
	protected void finalize() {
		if(resource.removeReference() && !fileName.isEmpty()) {
			loadedTextures.remove(fileName);
		}
	}
	
	public void bind() {
		bind(0);
	}
	
	public void bind(int samplerSlot) {
		assert(samplerSlot >= 0 && samplerSlot <= 31);
		glActiveTexture(GL_TEXTURE0 + samplerSlot);
		glBindTexture(GL_TEXTURE_2D, resource.getID());
	}
	
	
	public int getID() { return resource.getID(); }
	
	private static TextureResource loadTexture(String fileName) {
//		String[] splitArray = fileName.split("\\.");
//		String ext = splitArray[splitArray.length - 1];
		
		try {
			BufferedImage image = ImageIO.read(new File("./res/textures/" + fileName));
			int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null,  0,  image.getWidth());
			
			ByteBuffer buffer = Util.createByteBuffer(image.getHeight() * image.getWidth() * 4); //BYTES PER PIXEL IS 4
			
			boolean hasAlpha = image.getColorModel().hasAlpha();
			
			for(int y=0; y < image.getHeight(); y++) {
				for(int x=0; x < image.getWidth(); x++) {
					int pixel = pixels[y*image.getWidth() + x];
					
					buffer.put((byte)((pixel >> 16) & 0xFF)); //Red
					buffer.put((byte)((pixel >> 8 ) & 0xFF)); //Green
					buffer.put((byte)((pixel      ) & 0xFF)); //Blue
					if(hasAlpha) {
						buffer.put((byte)((pixel >> 24) & 0xFF));
					} else {
						buffer.put((byte)(0xFF));
					}
				}
			}
			
			buffer.flip(); //HAVE TO FLIP OR WRONG
			
			TextureResource resource = new TextureResource();
			glBindTexture(GL_TEXTURE_2D, resource.getID());
			
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
			
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
			
			return resource;
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		return null;
	}
}
