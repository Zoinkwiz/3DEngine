package com.base.core;

import com.base.rendering.Shader;

public interface GameComponent {

	public void input(Transform transform);
	public void update(Transform transform);
	
	public void render(Transform transform, Shader shader);
}
