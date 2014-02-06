package com.base.engine.core;

public class Transform
{
	private Vector3f pos;
	private Vector3f rot;
	private Vector3f scale;
	
	public Transform()
	{
		pos = new Vector3f(0,0,0);
		rot = new Vector3f(0,0,0);
		scale = new Vector3f(1,1,1);
	}
	
	public Matrix4f getTransformation()
	{
		Matrix4f translationMatrix = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());
		Matrix4f rotationMatrix = new Matrix4f().initRotation(rot.getX(), rot.getY(), rot.getZ());
		Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());
		
		return translationMatrix.mul(rotationMatrix.mul(scaleMatrix));
	}
	
	public Vector3f getPos()
	{
		return pos;
	}
	
	public void setPos(Vector3f pos)
	{
		this.pos = pos;
	}
	
	public void setPos(float x, float y, float z)
	{
		this.pos = new Vector3f(x, y, z);
	}

	public Vector3f getRot()
	{
		return rot;
	}

	public void setRot(Vector3f rotation)
	{
		this.rot = rotation;
	}
	
	public void setRot(float x, float y, float z)
	{
		this.rot = new Vector3f(x, y, z);
	}

	public Vector3f getScale()
	{
		return scale;
	}

	public void setScale(Vector3f scale)
	{
		this.scale = scale;
	}
	
	public void setScale(float x, float y, float z)
	{
		this.scale = new Vector3f(x, y, z);
	}
}
