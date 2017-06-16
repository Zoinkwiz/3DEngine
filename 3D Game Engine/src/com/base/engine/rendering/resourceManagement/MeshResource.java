package com.base.engine.rendering.resourceManagement;

import static org.lwjgl.opengl.GL15.*;

public class MeshResource {
	private int vbo;
	private int ibo;
	private int size;
	private int refCount;

	public MeshResource(int size) {
		vbo = glGenBuffers();
		ibo = glGenBuffers();
		this.size = size;
		this.refCount = 1;
	}

	@Override
	protected void finalize() {
//		System.out.println("WAOW MeshResource start die");
//		glDeleteBuffers(vbo);
//		glDeleteBuffers(ibo);
//		System.out.println("WAOW MeshResource finish die");
	}

	public void addReference() {
		refCount++;
	}

	public boolean removeReference() {
		refCount--;
		return refCount == 0;
	}

	public int getVbo() { return vbo; }

	public int getIbo() { return ibo; }

	public int getSize() { return size; }
}
