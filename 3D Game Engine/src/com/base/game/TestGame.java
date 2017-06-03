package com.base.game;

import com.base.core.Game;
import com.base.core.GameObject;
import com.base.core.Transform;
import com.base.core.Vector2f;
import com.base.core.Vector3f;
import com.base.rendering.Attenuation;
import com.base.rendering.BaseLight;
import com.base.rendering.BasicShader;
import com.base.rendering.Camera;
import com.base.rendering.Material;
import com.base.rendering.Mesh;
import com.base.rendering.PointLight;
import com.base.rendering.SpotLight;
import com.base.rendering.Texture;
import com.base.rendering.Vertex;
import com.base.rendering.Window;

public class TestGame extends Game {
//	private Camera camera;
	
	public void init() {

//		camera = new Camera();
	
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
		planeObject.getTransform().setTranslation(0, -1, 5);
		
		getRootObject().addChild(planeObject);
		
		//Transform.setProjection(70f, Window.getHeight(), Window.getWidth(), 0.1f, 1000f);
		//Transform.setCamera(camera);
	}
	
//	public void input() {
//		camera.input();
//		root.input();
//	}
//	
//	public void update() {
//		root.getTransform().setTranslation(0, -1, 5);
//		root.update();
//	}
//	
//	public void render() {
//		root.render();
//	}
}
