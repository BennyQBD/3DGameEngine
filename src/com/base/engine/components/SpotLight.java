package com.base.engine.components;

import com.base.engine.components.PointLight;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.ForwardSpot;

public class SpotLight extends PointLight
{
	private float cutoff;
	
	public SpotLight(Vector3f color, float intensity, Vector3f attenuation, float cutoff)
	{
		super(color, intensity, attenuation);
		this.cutoff = cutoff;

		setShader(ForwardSpot.getInstance());
	}
	
	public Vector3f getDirection()
	{
		return getTransform().getTransformedRot().getForward();
	}

	public float getCutoff()
	{
		return cutoff;
	}
	public void setCutoff(float cutoff)
	{
		this.cutoff = cutoff;
	}
}
