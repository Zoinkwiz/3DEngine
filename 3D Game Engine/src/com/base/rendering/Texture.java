package com.base.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.system.MemoryStack.stackPush;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryStack;
public class Texture {
	private int id;
    private int width;
    private int height;
	
	public Texture(String fileName) {
		int[] temp = loadTexture(fileName);
		this.id = temp[0];
		this.width = temp[1];
		this.height = temp[2];
	}
	
    
	public Texture(int id) {
		this.id = id;
	}
	
	public Texture() {
		this.id = glGenTextures();
	}
	

    public void setParameter(int name, int value) {
        glTexParameteri(GL_TEXTURE_2D, name, value);
    }
    

    public void uploadData(int width, int height, ByteBuffer data) {
        uploadData(GL_RGBA8, width, height, GL_RGBA, data);
    }

    public void uploadData(int internalFormat, int width, int height, int format, ByteBuffer data) {
        glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, GL_UNSIGNED_BYTE, data);
    }

	
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, id);
	}
	
	public int getID() {
		return id;
	}
	
	//Load a texture from an image file
	private static int[] loadTexture(String fileName) {
		try (MemoryStack stack = stackPush()) {
			IntBuffer w = stack.mallocInt(1); //Width of image
			IntBuffer h = stack.mallocInt(1); //Height of image
			IntBuffer comp = stack.mallocInt(1); //Number of 8-bit components per pixel
			
			ByteBuffer image = stbi_load("./res/textures/" + fileName, w, h, comp, Float.BYTES);
			if (image == null) {
			    throw new RuntimeException("Failed to load a texture file!"
			            + System.lineSeparator() + stbi_failure_reason());
			}

			int width = w.get();
			int height = h.get();
			
			//return id; 
			return createTexture(width, height, image);
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		return null;
	}
	
	 private static int[] createTexture(int width, int height, ByteBuffer data) {
	        Texture texture = new Texture();
		 	texture.setWidth(width);
	        texture.setHeight(height);

	        //Bind this texture to an ID
	        texture.bind();
	        
	        //TODO: Go through these and clarify use
	        texture.setParameter(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
	        texture.setParameter(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
	        texture.setParameter(GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	        texture.setParameter(GL_TEXTURE_MAG_FILTER, GL_NEAREST);

	        texture.uploadData(GL_RGBA8, width, height, GL_RGBA, data);

	        return new int[] {texture.id, texture.width, texture.height};
	}
	 
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}
