package com.base.engine;

public class SpotLight
{
	private PointLight pointLight;
	private Vector3f direction;
	private float cutoff;
	
	public SpotLight(PointLight pointLight, Vector3f direction, float cutoff)
	{
		this.pointLight = pointLight;
		this.direction = direction.normalized();
		this.cutoff = cutoff;
	}
	
	public PointLight getPointLight()
	{
		return pointLight;
	}
	
	public void setPointLight(PointLight pointLight)
	{
		this.pointLight = pointLight;
	}
	
	public Vector3f getDirection()
	{
		return direction;
	}
	public void setDirection(Vector3f direction)
	{
		this.direction = direction.normalized();
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
