package com.base.engine.components;

import com.base.core.Transform;
import com.base.rendering.Material;
import com.base.rendering.Mesh;
import com.base.rendering.RenderingEngine;
import com.base.rendering.Shader;

public class MeshRenderer extends GameComponent {
	
	private Mesh mesh;
	private Material material;
	
	public MeshRenderer(Mesh mesh, Material material) {
		this.mesh = mesh;
		this.material = material;
	}
	
	@Override	
	public void render( Shader shader, RenderingEngine renderingEngine) {
		shader.bind();
		shader.updateUniforms(getTransform(), material, renderingEngine);
		mesh.draw();
	}
}
