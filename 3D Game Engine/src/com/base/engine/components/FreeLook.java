package com.base.engine.components;

import static org.lwjgl.glfw.GLFW.*;

import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;
import com.base.engine.core.input.Input;
import com.base.engine.rendering.Window;

public class FreeLook extends GameComponent {
	
	public static final Vector3f yAxis = new Vector3f(0, 1, 0);
	
	private boolean mouseLocked = false;
	private Vector2f centrePos;
	
	private float   sensitivity;

	public FreeLook() {
		this.sensitivity = 0.5f;
	}
	
	public FreeLook(float sensitivity) {
		this.sensitivity = sensitivity;
	}
	
	@Override
	public void input(float delta) {
		centrePos = Window.getCentre();
		if(Input.getMouseDown(GLFW_MOUSE_BUTTON_LEFT)) {
			Input.setMousePosition(centrePos);
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
			
			if(rotY) {
				getTransform().rotate(yAxis, (float) Math.toRadians(deltaPos.getX() * sensitivity));
			}
			if(rotX) {
				getTransform().rotate(getTransform().getRot().getRight(), (float) Math.toRadians(deltaPos.getY() * sensitivity));
			}

			if(rotY || rotX) {
				Input.setMousePosition(centrePos);
			}
		}
	}
	
	public void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}
}
