package com.base.core;

import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_CW;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glFrontFace;
import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

import com.base.rendering.BasicShader;
import com.base.rendering.Camera;
import com.base.rendering.Shader;
import com.base.rendering.Window;

public class RenderingEngine {
	private Camera mainCamera;
	
	public RenderingEngine() {
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		glFrontFace(GL_CW);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		
		//TODO: Confirm this is correct
		glEnable(GL_DEPTH_CLAMP);
		
		mainCamera = new Camera((float)Math.toRadians(70.0f), (float)Window.getWidth()/(float)Window.getWidth(), 0.01f, 1000.0f);
		//glEnable(GL_TEXTURE_2D);
		//glEnable(GL_FRAMEBUFFER_SRGB);
	}
	
	public void input() {
		mainCamera.input();
	}
	
	public void render(GameObject object) {
		clearScreen();
		
		Shader shader = BasicShader.getInstance();
		shader.setRenderingEngine(this);
		object.render(BasicShader.getInstance());
	}
	
	
	private static void clearScreen() {
		//TODO: Stencil Buffer
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	private static void setTextures(boolean enabled) {
		if(enabled) {
//			glEnable(GL_TEXTURE_2D);				
		} else {
//			glDisable(GL_TEXTURE_2D);
		}
	}
	
	private static void unbindTextures() {
		//glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	private static void setClearColour(Vector3f colour) {
		glClearColor(colour.getX(), colour.getY(), colour.getZ(), 1.0f);
	}
	
	
	public static String getOpenGLVersion() {
		return glGetString(GL_VERSION);
	}

	public Camera getMainCamera() {
		return mainCamera;
	}

	public void setMainCamera(Camera mainCamera) {
		this.mainCamera = mainCamera;
	}
	
	
}
