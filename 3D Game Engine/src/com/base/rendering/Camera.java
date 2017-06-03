package com.base.rendering;
import static org.lwjgl.glfw.GLFW.*;

import com.base.core.Input;
import com.base.core.Matrix4f;
import com.base.core.Time;
import com.base.core.Vector2f;
import com.base.core.Vector3f;
public class Camera {
	public static final Vector3f yAxis = new Vector3f(0, 1, 0);
	private Vector3f pos; //Current position
	private Vector3f forward;
	private Vector3f up;
	private Matrix4f projection;
	
	
	/** Create a base camera with pos (0,0,0), forward(0,0,1) and up (0,1,0)*/
	public Camera(float fov, float aspect, float zNear, float zFar) {
		this.pos = new Vector3f(0,0,0);
		this.forward = new Vector3f(0,0,1).normalized();
		this.up = new Vector3f(0,1,0).normalized();
		this.projection = new Matrix4f().initPerspective(fov, aspect, zNear, zFar);

	}
	
	public Matrix4f getViewProjection() {
		Matrix4f cameraRotation = new Matrix4f().initRotation(forward, up);
		Matrix4f cameraTranslation = new Matrix4f().initTranslation(-pos.getX(), -pos.getY(), -pos.getZ());
	
		return projection.mul(cameraRotation.mul(cameraTranslation));

	}
	
	public Camera(Vector3f pos, Vector3f forward, Vector3f up) {
		this.pos = pos;
		this.forward = forward.normalized();
		this.up = up.normalized();
	}

	private boolean mouseLocked = false;
	private Vector2f centrePos = Window.getCentre();
	public void input() {
		float sensitivity = 0.5f;
		float movAmt = (float)(10 * Time.getDelta());
		float rotAmt = (float)(100 * Time.getDelta());
		
		if(Input.getKeyHeld(GLFW_KEY_W)) {
			move(getForward(), movAmt);
		}
		if(Input.getKeyHeld(GLFW_KEY_S)) {
			move(getForward(), -movAmt);
		}
		if(Input.getKeyHeld(GLFW_KEY_A)) {
			move(getLeft(), movAmt);
		}
		if(Input.getKeyHeld(GLFW_KEY_D)) {
			move(getRight(), movAmt);
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
			
			if(rotY) {
				rotateY(deltaPos.getX() * sensitivity);
			}
			if(rotX) {
				rotateX(deltaPos.getY() * sensitivity);
			}
			if(rotY || rotX) {
				Input.setMousePosition(Window.getCentre());
			}
		}
		
		if(Input.getKeyHeld(GLFW_KEY_UP)){
			rotateX(-rotAmt);
		}
		if(Input.getKeyHeld(GLFW_KEY_DOWN)){
			rotateX(rotAmt);
		}
		if(Input.getKeyHeld(GLFW_KEY_LEFT)){
			rotateY(-rotAmt);
		}
		if(Input.getKeyHeld(GLFW_KEY_RIGHT)){
			rotateY(rotAmt);
		}
	}
	
	/**Rotate around Y axis, looking left/right*/
	public void rotateY(float angle) {
		Vector3f hAxis = yAxis.cross(forward).normalized();	
		forward = forward.rotate(angle, yAxis).normalized();	
		up = forward.cross(hAxis).normalized();
	}
	
	/**Rotate around X axis, looking up/down*/
	public void rotateX(float angle) {
		Vector3f hAxis = yAxis.cross(forward).normalized();
		forward = forward.rotate(angle, hAxis).normalized();	
		up = forward.cross(hAxis).normalized();
	}
	
	public void move(Vector3f dir, float amt) {
		pos = pos.add(dir.mul(amt));//Move camera in a direction by distance amt
	}
	
	public Vector3f getLeft() {
		//Cross product of up and forward is left
		return forward.cross(up).normalized();
	}
	
	public Vector3f getRight() {
		return up.cross(forward).normalized();
	}

	public Vector3f getPos() {
		return pos;
	}

	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

	public Vector3f getForward() {
		return forward;
	}

	public void setForward(Vector3f forward) {
		this.forward = forward;
	}

	public Vector3f getUp() {
		return up;
	}

	public void setUp(Vector3f up) {
		this.up = up;
	}
	
}
