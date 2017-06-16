package com.base.engine.core.input;

import org.lwjgl.glfw.GLFWWindowSizeCallback;
import static org.lwjgl.opengl.GL11.*;

import com.base.engine.rendering.Window;

public class WindowScaling extends GLFWWindowSizeCallback {
        @Override
        public void invoke(long window, int width, int height){
            Window.setWidth(width);
            Window.setHeight(height);
//            Game.aspectRatio = (float)Game.WIDTH / (float)Game.HEIGHT;
            System.out.println("WUT");
            glViewport(0, 0, width, height);
//            gfx.initGL();
//            Camera.initMatricies();
        }
}
