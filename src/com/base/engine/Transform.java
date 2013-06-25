package com.base.engine;

public class Transform
{
	private Vector3f translation;
	private Vector3f rotation;
	
	public Transform()
	{
		translation = new Vector3f(0,0,0);
		rotation = new Vector3f(0,0,0);
	}
	
	public Matrix4f getTransformation()
	{
		Matrix4f translationMatrix = new Matrix4f().initTranslation(translation.getX(), translation.getY(), translation.getZ());
		Matrix4f rotationMatrix = new Matrix4f().initRotation(rotation.getX(), rotation.getY(), rotation.getZ());
		
		return translationMatrix.mul(rotationMatrix);
	}
	
	public Vector3f getTranslation()
	{
		return translation;
	}

	public void setTranslation(Vector3f translation)
	{
		this.translation = translation;
	}
	
	public void setTranslation(float x, float y, float z)
	{
		this.translation = new Vector3f(x, y, z);
	}

	public Vector3f getRotation()
	{
		return rotation;
	}

	public void setRotation(Vector3f rotation)
	{
		this.rotation = rotation;
	}
	
	public void setRotation(float x, float y, float z)
	{
		this.rotation = new Vector3f(x, y, z);
	}
}
