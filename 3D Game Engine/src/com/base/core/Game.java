package com.base.core;
//import static org.lwjgl.glfw.Callbacks.*;
//import static org.lwjgl.glfw.GLFW.*;


//import org.lwjgl.glfw.GLFWVidMode;
//import static org.lwjgl.opengl.GL11.*;
//import static org.lwjgl.system.MemoryUtil.*;
public interface Game {
	public void init();
	public void input();
	public void update();
	public void render();

}
