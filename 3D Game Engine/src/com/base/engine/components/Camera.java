package com.base.engine.components;
import static org.lwjgl.glfw.GLFW.*;

import com.base.core.Input;
import com.base.core.Matrix4f;
import com.base.core.Quaternion;
import com.base.core.Vector2f;
import com.base.core.Vector3f;
import com.base.rendering.RenderingEngine;
import com.base.rendering.Window;
public class Camera extends GameComponent {
	
	public static final Vector3f yAxis = new Vector3f(0, 1, 0);
	
	private Matrix4f projection;
	
	
	public Camera(float fov, float aspect, float zNear, float zFar) {
		this.projection = new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
	}

	public Matrix4f getViewProjection() {
		Matrix4f cameraRotation = getTransform().getRot().toRotationMatrix();
		Matrix4f cameraTranslation = new Matrix4f().initTranslation(-getTransform().getPos().getX(), -getTransform().getPos().getY(), -getTransform().getPos().getZ());

		return projection.mul(cameraRotation.mul(cameraTranslation));
	}

	@Override
	public void addToRenderingEngine(RenderingEngine renderingEngine) {
		renderingEngine.addCamera(this);
	}

	private boolean mouseLocked = false;
	private Vector2f centrePos = Window.getCentre();
	
	@Override
	public void input(float delta) {
		float sensitivity = -0.5f;
		float movAmt = (float)(10 * delta);
		
		if(Input.getKeyHeld(GLFW_KEY_W)) {
			move(getTransform().getRot().getForward(), movAmt);
		}
		if(Input.getKeyHeld(GLFW_KEY_S)) {
			move(getTransform().getRot().getForward(), -movAmt);
		}
		if(Input.getKeyHeld(GLFW_KEY_A)) {
			move(getTransform().getRot().getLeft(), movAmt);
		}
		if(Input.getKeyHeld(GLFW_KEY_D)) {
			move(getTransform().getRot().getRight(), movAmt);
		}
		if(Input.getMouseDown(GLFW_MOUSE_BUTTON_LEFT)) {
			Input.setMousePosition(Window.getCentre());
			Input.setMouseVisible(false);
			mouseLocked = true;
		}
		if(Input.getMouseUp(GLFW_MOUSE_BUTTON_LEFT)) {
			mouseLocked = false;
			Input.setMouseVisible(true);
		}
		
		
		if(mouseLocked) {
			Vector2f deltaPos = Input.getCurrentMousePosition().sub(centrePos);
			
			boolean rotY = deltaPos.getX() != 0;
			boolean rotX = deltaPos.getY() != 0;
			
			if(rotY)
				getTransform().setRot(getTransform().getRot().mul(new Quaternion(yAxis, (float) Math.toRadians(deltaPos.getX() * sensitivity))).normalized());
			if(rotX)
				getTransform().setRot(getTransform().getRot().mul(new Quaternion(getTransform().getRot().getRight(), ((float) Math.toRadians(deltaPos.getY() * sensitivity)))).normalized());

			if(rotY || rotX) {
				Input.setMousePosition(Window.getCentre());
			}
		}
	}
	
	public void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}
	

}
