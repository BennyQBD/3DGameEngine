package com.base.engine;

public class PointLight
{
	private BaseLight baseLight;
	private Attenuation atten;
	private Vector3f position;
	private float range;
	
	public PointLight(BaseLight baseLight, Attenuation atten, Vector3f position, float range)
	{
		this.baseLight = baseLight;
		this.atten = atten;
		this.position = position;
		this.range = range;
	}
	
	public BaseLight getBaseLight()
	{
		return baseLight;
	}
	public void setBaseLight(BaseLight baseLight)
	{
		this.baseLight = baseLight;
	}
	public Attenuation getAtten()
	{
		return atten;
	}
	public void setAtten(Attenuation atten)
	{
		this.atten = atten;
	}
	public Vector3f getPosition()
	{
		return position;
	}
	public void setPosition(Vector3f position)
	{
		this.position = position;
	}

	public float getRange()
	{
		return range;
	}

	public void setRange(float range)
	{
		this.range = range;
	}
}
