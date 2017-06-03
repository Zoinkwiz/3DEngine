package com.base.game;

import com.base.core.GameComponent;
import com.base.core.Transform;
import com.base.rendering.BasicShader;
import com.base.rendering.Material;
import com.base.rendering.Mesh;
import com.base.rendering.Shader;

public class MeshRenderer implements GameComponent {
	
	private Mesh mesh;
	private Material material;
	
	public MeshRenderer(Mesh mesh, Material material) {
		this.mesh = mesh;
		this.material = material;
	}

	@Override
	public void input(Transform transform) {
		
	}

	@Override
	public void update(Transform transform) {
		
	}
	
	@Override	
	public void render(Transform transform, Shader shader) {
		shader.bind();
		shader.updateUniforms(transform, material);
		mesh.draw();
	}
}
