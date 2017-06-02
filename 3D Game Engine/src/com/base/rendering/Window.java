package com.base.rendering;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import com.base.core.KeyInput;
import com.base.core.MouseButtonInput;
import com.base.core.MousePosInput;
import com.base.core.Vector2f;

import java.nio.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
	private static long window;
	@SuppressWarnings("unused")
	private static GLFWKeyCallback keyCallback;
	@SuppressWarnings("unused")
	private static GLFWMouseButtonCallback mouseClickCallback;
	@SuppressWarnings("unused")
	private static GLFWCursorPosCallback mousePosCallback;
	
	public static void createWindow(int width, int height, String title) {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");
		init(width, height, title);
	}

	
	public static void init(int width, int height, String title){
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if (!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		//glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		//glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

		// Create the window
		window = glfwCreateWindow(width, height, title, NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");
		
		glfwSetKeyCallback(window, keyCallback = new KeyInput());
		glfwSetMouseButtonCallback(window, mouseClickCallback = new MouseButtonInput());
		glfwSetCursorPosCallback(window, mousePosCallback = new MousePosInput());
//		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*
//
//			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);
//
//			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		//glfwSwapInterval(1); //TODO: vsync?

		// Make the window visible
		glfwShowWindow(window);
		
		
		//WAS ORIGNALLY AT START OF LOOP, MAY NEED TO MOVE
		
		// This line is critical for LWJGL's interperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();
		// Set the clear color
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		RenderUtil.initGraphics();
	}
	
	public static void loop() {
		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		if(!glfwWindowShouldClose(window)){
//			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

			glfwSwapBuffers(window); // swap the color buffers

			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
			//return true;
		} else {
			//return false;
		}
	}
	
	public static void dispose() {
		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
	
	public static boolean closed() {
		if(!glfwWindowShouldClose(window)){
			return false;
		}
		return true;
	}
	
	public static GLFWVidMode getScreen() {
		return glfwGetVideoMode(glfwGetPrimaryMonitor());
	}
	
	public static long getWindow() {
		return window;
	}
	
	public static int getWidth() {
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1);

			glfwGetWindowSize(window, pWidth, null);
			return pWidth.get(0);
		}
	}
	
	public static int getHeight() {
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pHeight = stack.mallocInt(1);
			glfwGetWindowSize(window, null, pHeight);
			return pHeight.get(0);
		}
	}
	
	public static Vector2f getCentre() {
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			glfwGetWindowSize(window, pWidth, pHeight);
			return new Vector2f((float)(pWidth.get(0)/2), (float)(pHeight.get(0)/2));
		}
	}
}
