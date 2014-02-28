package com.base.engine.components;

import com.base.engine.core.Transform;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Shader;

public class MeshRenderer extends GameComponent
{
	private Mesh mesh;
	private Material material;

	public MeshRenderer(Mesh mesh, Material material)
	{
		this.mesh = mesh;
		this.material = material;
	}

	@Override
	public void render(Shader shader, RenderingEngine renderingEngine)
	{
		shader.bind();
		shader.updateUniforms(getTransform(), material, renderingEngine);
		mesh.draw();
	}
}
