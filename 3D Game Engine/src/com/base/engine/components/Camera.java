package com.base.engine.components;

import com.base.engine.core.*;
import com.base.engine.rendering.Window;
public class Camera extends GameComponent {
	private Matrix4f projection;
	float fov;
	float aspect;
	float zNear;
	float zFar;
	
	public Camera(Matrix4f projection) {
		this.projection = projection;
	}
	
	/** Camera needs radians for fov **/
	public Camera(float fov, float aspect, float zNear, float zFar) {
		this.fov = fov;
		this.aspect = aspect;
		this.zNear = zNear;
		this.zFar = zFar;
		this.projection = new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
	}
	
	//Updates perspective
	@Override
	public void update(float delta) {
		this.aspect = (float)Window.getWidth()/(float)Window.getHeight();
		this.projection = new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
	}
	
	public Matrix4f getViewProjection() {
		Matrix4f cameraRotation = getTransform().getTransformedRot().conjugate().toRotationMatrix();
		Vector3f cameraPos = getTransform().getTransformedPos().mul(-1);
				
		Matrix4f cameraTranslation = new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());

		return projection.mul(cameraRotation.mul(cameraTranslation));
	}

	@Override
	public void addToEngine(CoreEngine engine) {
		engine.getRenderingEngine().addCamera(this);
	}
}
