package com.base.engine.rendering;

import com.base.engine.components.BaseLight;
import com.base.engine.components.PointLight;
import com.base.engine.components.SpotLight;
import com.base.engine.core.Matrix4f;
import com.base.engine.core.Transform;

public class ForwardSpot extends Shader
{
	private static final ForwardSpot instance = new ForwardSpot();

	public static ForwardSpot getInstance()
	{
		return instance;
	}

	private ForwardSpot()
	{
		super();

		addVertexShaderFromFile("forward-spot.vs");
		addFragmentShaderFromFile("forward-spot.fs");

		setAttribLocation("position", 0);
		setAttribLocation("texCoord", 1);
		setAttribLocation("normal", 2);

		compileShader();

		addUniform("model");
		addUniform("MVP");

		addUniform("specularIntensity");
		addUniform("specularPower");
		addUniform("eyePos");

		addUniform("spotLight.pointLight.base.color");
		addUniform("spotLight.pointLight.base.intensity");
		addUniform("spotLight.pointLight.atten.constant");
		addUniform("spotLight.pointLight.atten.linear");
		addUniform("spotLight.pointLight.atten.exponent");
		addUniform("spotLight.pointLight.position");
		addUniform("spotLight.pointLight.range");
		addUniform("spotLight.direction");
		addUniform("spotLight.cutoff");
	}

	public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine)
	{
		Matrix4f worldMatrix = transform.getTransformation();
		Matrix4f projectedMatrix = renderingEngine.getMainCamera().getViewProjection().mul(worldMatrix);
		material.getTexture("diffuse").bind();

		setUniform("model", worldMatrix);
		setUniform("MVP", projectedMatrix);

		setUniformf("specularIntensity", material.getFloat("specularIntensity"));
		setUniformf("specularPower", material.getFloat("specularPower"));

		setUniform("eyePos", renderingEngine.getMainCamera().getTransform().getTransformedPos());
		setUniformSpotLight("spotLight", (SpotLight)renderingEngine.getActiveLight());
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

	public void setUniformSpotLight(String uniformName, SpotLight spotLight)
	{
		setUniformPointLight(uniformName + ".pointLight", spotLight);
		setUniform(uniformName + ".direction", spotLight.getDirection());
		setUniformf(uniformName + ".cutoff", spotLight.getCutoff());
	}
}
