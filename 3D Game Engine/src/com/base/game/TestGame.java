package com.base.game;

import com.base.engine.core.*;
import com.base.engine.components.*;
import com.base.engine.rendering.*;

public class TestGame extends Game {

	public void init() {		
		GameObject planeObject = new GameObject();
		GameObject pointLightObject = new GameObject();
		GameObject spotLightObject = new GameObject();
		GameObject directionalLightObject = new GameObject();
		
		planeObject.addComponent(new MeshRenderer(new Mesh("plane3.obj"), new Material(new Texture("bricks.jpg"), 1, 8,
																					   new Texture("bricks_normal.jpg"), new Texture("bricks_disp.png"), 0.04f, -1.0f)));
		planeObject.getTransform().setPos(0, -1, 5);
		planeObject.getTransform().setScale(4.0f);
		
		pointLightObject.addComponent(new PointLight(new Vector3f(0,1,0),0.4f, new Attenuation(0,0,1)));
		pointLightObject.getTransform().setPos(new Vector3f(7, 0, 7));
		
		spotLightObject.addComponent(new SpotLight(new Vector3f(0,1,0), 10f, new Attenuation(0,0,0.1f), 0.7f));
		spotLightObject.getTransform().setRot(new Quaternion(new Vector3f(0,1,0), (float)Math.toRadians(90.0f)));
		
		directionalLightObject.addComponent(new DirectionalLight(new Vector3f(1,1,1), 0.4f));
		
		GameObject testMesh1 = new GameObject();
		GameObject testMesh2 = new GameObject();
		
		testMesh1.addComponent(new MeshRenderer(new Mesh("monkey3.obj"), new Material(new Texture("bricks.jpg"), 1, 8,
					  																  new Texture("bricks_normal.jpg"),new Texture("bricks_disp.png"), 0.03f, -0.5f)));
		testMesh2.addComponent(new MeshRenderer(new Mesh("monkey3.obj"), new Material(new Texture("bricks.jpg"), 1, 8,
		   																			  new Texture("bricks_normal.jpg"),new Texture("bricks_disp.png"), 0.03f, -0.5f)));

		testMesh1.getTransform().setPos(new Vector3f(0, 2, 0));
		testMesh1.getTransform().setRot(new Quaternion(new Vector3f(0,1,0), 0.4f));
		testMesh1.getTransform().setScale(1.0f);
		
		testMesh2.getTransform().setPos(new Vector3f(0, 0, 25));
		
		testMesh1.addChild(testMesh2);
		
		addToScene(planeObject);
	//	addToScene(pointLightObject);
	//	addToScene(spotLightObject);
		addToScene(directionalLightObject);
		addToScene(testMesh1);
		testMesh2.addChild((new GameObject())
				.addComponent(new Camera((float)Math.toRadians(70.0f), Window.getAspect(), 0.1f, 1000.0f))
				.addComponent(new FreeLook())
				.addComponent(new FreeMove()));
		}
	}
