package com.base.core;

import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;

import org.lwjgl.glfw.GLFWCursorPosCallback;

import com.base.rendering.Window;
public class MousePosInput extends GLFWCursorPosCallback {
	private static float x = 0f;
	private static float y = 0f;
	@Override
	public void invoke(long window, double xpos, double ypos) {
		//TODO: store positions
		x = (float)xpos;
		y = (float)ypos;
		
	}
	
	public static Vector2f getMousePosition() {
		return new Vector2f(x,y);
	}
	
	public static void setMousePosition(Vector2f pos) {
		x = pos.getX();
		y = pos.getY();
		glfwSetCursorPos(Window.getWindow(), x, y);
	}
	
	
}
