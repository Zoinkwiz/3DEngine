package com.base.game;

import com.base.core.Game;
import com.base.core.Time;
import com.base.core.Transform;
import com.base.core.Vector2f;
import com.base.core.Vector3f;
import com.base.rendering.Attenuation;
import com.base.rendering.BaseLight;
import com.base.rendering.Camera;
import com.base.rendering.DirectionalLight;
import com.base.rendering.Material;
import com.base.rendering.Mesh;
import com.base.rendering.PhongShader;
import com.base.rendering.PointLight;
import com.base.rendering.RenderUtil;
import com.base.rendering.Shader;
import com.base.rendering.SpotLight;
import com.base.rendering.Texture;
import com.base.rendering.Vertex;
import com.base.rendering.Window;

public class TestGame implements Game {
	private Mesh mesh;
	private Shader shader;
	private Transform transform;
	private Material material;
	private Camera camera;
	
	PointLight pLight1 = new PointLight(new BaseLight(new Vector3f(1,0.5f,0), 0.8f), new Attenuation(0,0,1), new Vector3f(-2,0,5f), 10);
	PointLight pLight2 = new PointLight(new BaseLight(new Vector3f(0,0.5f,1), 0.8f), new Attenuation(0,0,1), new Vector3f(2,0,7f), 10);
	
	SpotLight sLight1 = new SpotLight( new PointLight(new BaseLight(new Vector3f(0f,1f,1f), 0.8f), new Attenuation(0,0,0.1f), new Vector3f(-2,0,5f), 30)
										, new Vector3f(1,1,1), 0.7f);
	public TestGame() {
		
	}
	
	
	
	public void init() {
		//mesh = ResourceLoader.loadMesh("box.obj");
		material = new Material(new Texture("test.png"), new Vector3f(1,1,1), 1, 8);
		shader = PhongShader.getInstance();
		camera = new Camera();
		transform = new Transform();
	
		float fieldDepth = 10.0f;
		float fieldWidth = 10.0f;
		
		Vertex[] vertices = new Vertex[] {  new Vertex(new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f,0.0f)),
											new Vertex(new Vector3f(-fieldWidth, 0.0f, fieldDepth*3), new Vector2f(0.0f,1.0f)),
											new Vertex(new Vector3f(fieldWidth*3, 0.0f, -fieldDepth), new Vector2f(1.0f,0.0f)),
											new Vertex(new Vector3f(fieldWidth*3, 0.0f, fieldDepth*3), new Vector2f(1.0f,1.0f))
		};
		
		int[] indices = new int[] { 0,1,2,
									2,1,3};
//        Vertex[] vertices = new Vertex[] { new Vertex( new Vector3f( -1.0f, -1.0f, 0.5773f ), new Vector2f( 0.0f, 0.0f ) ),
//        												new Vertex( new Vector3f( 0.0f, -1.0f, -1.15475f ), new Vector2f( 0.5f, 0.0f ) ),
//        												new Vertex( new Vector3f( 1.0f, -1.0f, 0.5773f ),new Vector2f( 1.0f, 0 ) ),
//        												new Vertex( new Vector3f( 0.0f, 1.0f, 0.0f ), new Vector2f( 0.5f, 1.0f ) ) };         
//        int[] indices = new int[] { 0, 3, 1,                 
//        							1, 3, 2,               
//        							2, 3, 0,                 
//        							1, 2, 0 };
		
		mesh = new Mesh(vertices, indices, true);
		
		//GLFWVidMode vid = Window.getScreen(); //Get monitor information
		Transform.setProjection(70f, Window.getHeight(), Window.getWidth(), 0.1f, 1000f);
		Transform.setCamera(camera);
		
		PhongShader.setAmbientLight(new Vector3f(0.1f,0.1f,0.1f));
		PhongShader.setDirectionalLight(new DirectionalLight(new BaseLight(new Vector3f(1,1,1), 0.8f), new Vector3f(1,1,1)));
		
		PhongShader.setPointLights(new PointLight[]{pLight1, pLight2});
		PhongShader.setSpotLights(new SpotLight[]{sLight1});
		
	}
	
	public void input() {
		camera.input();
	}
	
	float temp = 0.0f;
	float tempAmount = 0.0f;
	public void update() {
		temp+=Time.getDelta();
		//float sinTemp = (float)Math.sin(temp);
		transform.setTranslation(0, -1, 5);
		
		pLight1.setPosition(new Vector3f(3,0,8.0f * (float)(Math.sin(temp) + 1.0/2.0) + 10));
		pLight2.setPosition(new Vector3f(7,0,8.0f * (float)(Math.cos(temp) + 1.0/2.0) + 10));
		sLight1.getPointLight().setPosition(camera.getPos());
		sLight1.setDirection(camera.getForward());

		//transform.setRotation(0, sinTemp*180, 0);
		//transform.setScale(0.7f * sinTemp, 0.7f * sinTemp, 0.7f * sinTemp);
	}
	
	public void render() {
		RenderUtil.setClearColour(Transform.getCamera().getPos().div(2048f).abs());
		shader.bind();
		shader.updateUniforms(transform.getTransformation(), transform.getProjectedTransformation(), material);
		mesh.draw();
	}
}
