package com.base.game;

import com.base.core.Game;
import com.base.core.GameObject;
import com.base.core.Vector2f;
import com.base.core.Vector3f;
import com.base.engine.components.*;
import com.base.rendering.*;

public class TestGame extends Game {

	public void init() {
		float fieldDepth = 10.0f;
		float fieldWidth = 10.0f;
		
		Vertex[] vertices = new Vertex[] {  new Vertex(new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f,0.0f)),
											new Vertex(new Vector3f(-fieldWidth, 0.0f, fieldDepth*3), new Vector2f(0.0f,1.0f)),
											new Vertex(new Vector3f(fieldWidth*3, 0.0f, -fieldDepth), new Vector2f(1.0f,0.0f)),
											new Vertex(new Vector3f(fieldWidth*3, 0.0f, fieldDepth*3), new Vector2f(1.0f,1.0f))
		};
		
		int[] indices = new int[] { 0,1,2,
									2,1,3};

		Mesh mesh = new Mesh(vertices, indices, true);
		Material material = new Material(new Texture("test.png"), new Vector3f(1,1,1), 1, 8);

		MeshRenderer meshRenderer = new MeshRenderer(mesh, material);
		
		GameObject planeObject = new GameObject();
		planeObject.addComponent(meshRenderer);
		planeObject.getTransform().setPos(0, -1, 5);
		
		GameObject directionalLightObject = new GameObject();
		DirectionalLight directionalLight = new DirectionalLight(new BaseLight(new Vector3f(0,0,1), 0.4f), new Vector3f(1,1,1));
		directionalLightObject.addComponent(directionalLight);
		
		GameObject pointLightObject = new GameObject();
		pointLightObject.addComponent(new PointLight(new BaseLight(new Vector3f(0,1,0), 0.6f), new Attenuation(0,0,1), new Vector3f(5,0,5), 100));
		
		getRootObject().addChild(planeObject);
		getRootObject().addChild(directionalLightObject);
		getRootObject().addChild(pointLightObject);
		
	}
}
