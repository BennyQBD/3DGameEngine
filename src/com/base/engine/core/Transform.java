/*
 * Copyright (C) 2014 Benny Bobaganoosh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.base.engine.core;

public class Transform
{
	private Transform  m_parent;
	private Matrix4f   m_parentMatrix;

	private Vector3f   m_pos;
	private Quaternion m_rot;
	private Vector3f   m_scale;

	private Vector3f   m_oldPos;
	private Quaternion m_oldRot;
	private Vector3f   m_oldScale;

	public Transform()
	{
		m_pos = new Vector3f(0,0,0);
		m_rot = new Quaternion(0,0,0,1);
		m_scale = new Vector3f(1,1,1);

		m_parentMatrix = new Matrix4f().InitIdentity();
	}

	public void Update()
	{
		if(m_oldPos != null)
		{
			m_oldPos.Set(m_pos);
			m_oldRot.Set(m_rot);
			m_oldScale.Set(m_scale);
		}
		else
		{
			m_oldPos = new Vector3f(0,0,0).Set(m_pos).Add(1.0f);
			m_oldRot = new Quaternion(0,0,0,0).Set(m_rot).Mul(0.5f);
			m_oldScale = new Vector3f(0,0,0).Set(m_scale).Add(1.0f);
		}
	}

	public void Rotate(Vector3f axis, float angle)
	{
		m_rot = new Quaternion(axis, angle).Mul(m_rot).Normalized();
	}

	public void LookAt(Vector3f point, Vector3f up)
	{
		m_rot = GetLookAtRotation(point, up);
	}

	public Quaternion GetLookAtRotation(Vector3f point, Vector3f up)
	{
		return new Quaternion(new Matrix4f().InitRotation(point.Sub(m_pos).Normalized(), up));
	}

	public boolean HasChanged()
	{
		if(m_parent != null && m_parent.HasChanged())
			return true;

		if(!m_pos.equals(m_oldPos))
			return true;

		if(!m_rot.equals(m_oldRot))
			return true;

		if(!m_scale.equals(m_oldScale))
			return true;

		return false;
	}

	public Matrix4f GetTransformation()
	{
		Matrix4f translationMatrix = new Matrix4f().InitTranslation(m_pos.GetX(), m_pos.GetY(), m_pos.GetZ());
		Matrix4f rotationMatrix = m_rot.ToRotationMatrix();
		Matrix4f scaleMatrix = new Matrix4f().InitScale(m_scale.GetX(), m_scale.GetY(), m_scale.GetZ());

		return GetParentMatrix().Mul(translationMatrix.Mul(rotationMatrix.Mul(scaleMatrix)));
	}

	private Matrix4f GetParentMatrix()
	{
		if(m_parent != null && m_parent.HasChanged())
			m_parentMatrix = m_parent.GetTransformation();

		return m_parentMatrix;
	}

	public void SetParent(Transform parent)
	{
		this.m_parent = parent;
	}

	public Vector3f GetTransformedPos()
	{
		return GetParentMatrix().Transform(m_pos);
	}

	public Quaternion GetTransformedRot()
	{
		Quaternion parentRotation = new Quaternion(0,0,0,1);

		if(m_parent != null)
			parentRotation = m_parent.GetTransformedRot();

		return parentRotation.Mul(m_rot);
	}

	public Vector3f GetPos()
	{
		return m_pos;
	}
	
	public void SetPos(Vector3f pos)
	{
		this.m_pos = pos;
	}

	public Quaternion GetRot()
	{
		return m_rot;
	}

	public void SetRot(Quaternion rotation)
	{
		this.m_rot = rotation;
	}

	public Vector3f GetScale()
	{
		return m_scale;
	}

	public void SetScale(Vector3f scale)
	{
		this.m_scale = scale;
	}
}
