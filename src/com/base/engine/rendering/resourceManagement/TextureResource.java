package com.base.engine.rendering.resourceManagement;

import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;

public class TextureResource
{
	private int id;
	private int refCount;

	public TextureResource()
	{
		this.id = glGenTextures();
		this.refCount = 1;
	}

	@Override
	protected void finalize()
	{
		glDeleteBuffers(id);
	}

	public void addReference()
	{
		refCount++;
	}

	public boolean removeReference()
	{
		refCount--;
		return refCount == 0;
	}

	public int getId() { return id; }
}
