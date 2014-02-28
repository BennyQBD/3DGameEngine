package com.base.engine.rendering;

import com.base.engine.components.BaseLight;
import com.base.engine.components.PointLight;
import com.base.engine.core.Matrix4f;
import com.base.engine.core.Transform;

public class ForwardPoint extends Shader
{
	private static final ForwardPoint instance = new ForwardPoint();

	public static ForwardPoint getInstance()
	{
		return instance;
	}

	private ForwardPoint()
	{
		super();

		addVertexShaderFromFile("forward-point.vs");
		addFragmentShaderFromFile("forward-point.fs");

		setAttribLocation("position", 0);
		setAttribLocation("texCoord", 1);
		setAttribLocation("normal", 2);

		compileShader();

		addUniform("model");
		addUniform("MVP");

		addUniform("specularIntensity");
		addUniform("specularPower");
		addUniform("eyePos");

		addUniform("pointLight.base.color");
		addUniform("pointLight.base.intensity");
		addUniform("pointLight.atten.constant");
		addUniform("pointLight.atten.linear");
		addUniform("pointLight.atten.exponent");
		addUniform("pointLight.position");
		addUniform("pointLight.range");
	}

	public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine)
	{
		Matrix4f worldMatrix = transform.getTransformation();
		Matrix4f projectedMatrix = renderingEngine.getMainCamera().getViewProjection().mul(worldMatrix);
		material.getTexture("diffuse").bind();

		setUniform("model", worldMatrix);
		setUniform("MVP", projectedMatrix);

		setUniformf("specularIntensity", material.getFloat("specularIntensity"));//getSpecularIntensity());
		setUniformf("specularPower", material.getFloat("specularPower"));

		setUniform("eyePos", renderingEngine.getMainCamera().getTransform().getTransformedPos());
		setUniformPointLight("pointLight", (PointLight)renderingEngine.getActiveLight());
	}

	public void setUniformBaseLight(String uniformName, BaseLight baseLight)
	{
		setUniform(uniformName + ".color", baseLight.getColor());
		setUniformf(uniformName + ".intensity", baseLight.getIntensity());
	}

	public void setUniformPointLight(String uniformName, PointLight pointLight)
	{
		setUniformBaseLight(uniformName + ".base", pointLight);
		setUniformf(uniformName + ".atten.constant", pointLight.getConstant());
		setUniformf(uniformName + ".atten.linear", pointLight.getLinear());
		setUniformf(uniformName + ".atten.exponent", pointLight.getExponent());
		setUniform(uniformName + ".position", pointLight.getTransform().getTransformedPos());
		setUniformf(uniformName + ".range", pointLight.getRange());
	}


}
