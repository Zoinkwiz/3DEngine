package com.base.engine.components;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import com.base.engine.core.Vector3f;
import com.base.engine.core.input.Input;

public class FreeMove extends GameComponent {
	private float speed;
	private int   forwardKey;
	private int   backKey;
	private int   leftKey;
	private int   rightKey;

	public FreeMove(float speed) {
		this(speed, GLFW_KEY_W, GLFW_KEY_S, GLFW_KEY_A, GLFW_KEY_D);
	}

	public FreeMove(float speed, int forwardKey, int backKey, int leftKey, int rightKey)
	{
		this.speed = speed;
		this.forwardKey = forwardKey;
		this.backKey = backKey;
		this.leftKey = leftKey;
		this.rightKey = rightKey;
}
	
	
	@Override
	public void input(float delta) {
		float movAmt = (float)(speed * delta);
	
		if(Input.getKeyHeld(forwardKey)) {
			move(getTransform().getRot().getForward(), movAmt);
		}
		if(Input.getKeyHeld(backKey)) {
			move(getTransform().getRot().getForward(), -movAmt);
		}
		if(Input.getKeyHeld(leftKey)) {
			move(getTransform().getRot().getLeft(), movAmt);
		}
		if(Input.getKeyHeld(rightKey)) {
			move(getTransform().getRot().getRight(), movAmt);
		}
	}	
	
	public void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}
}
