package com.base.engine.core.input;

import org.lwjgl.glfw.GLFWScrollCallback;

public class ScrollingInput extends GLFWScrollCallback {
	static int scrollQuantity = 0;
	public void invoke(long window, double xoffset,  double yoffset) {
		scrollQuantity+=yoffset;
	}
	
	/** calling this resets scrollQuantity to 0 here **/
	public static int getScrollQuantity() {
		int temp = scrollQuantity;
		scrollQuantity = 0;
		return temp;
	}
}
