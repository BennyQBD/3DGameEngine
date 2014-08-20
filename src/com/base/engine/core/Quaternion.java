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

public class Quaternion
{
	private float m_x;
	private float m_y;
	private float m_z;
	private float m_w;

	public Quaternion(float x, float y, float z, float w)
	{
		this.m_x = x;
		this.m_y = y;
		this.m_z = z;
		this.m_w = w;
	}

	public Quaternion(Vector3f axis, float angle)
	{
		float sinHalfAngle = (float)Math.sin(angle / 2);
		float cosHalfAngle = (float)Math.cos(angle / 2);

		this.m_x = axis.GetX() * sinHalfAngle;
		this.m_y = axis.GetY() * sinHalfAngle;
		this.m_z = axis.GetZ() * sinHalfAngle;
		this.m_w = cosHalfAngle;
	}

	public float Length()
	{
		return (float)Math.sqrt(m_x * m_x + m_y * m_y + m_z * m_z + m_w * m_w);
	}
	
	public Quaternion Normalized()
	{
		float length = Length();
		
		return new Quaternion(m_x / length, m_y / length, m_z / length, m_w / length);
	}
	
	public Quaternion Conjugate()
	{
		return new Quaternion(-m_x, -m_y, -m_z, m_w);
	}

	public Quaternion Mul(float r)
	{
		return new Quaternion(m_x * r, m_y * r, m_z * r, m_w * r);
	}

	public Quaternion Mul(Quaternion r)
	{
		float w_ = m_w * r.GetW() - m_x * r.GetX() - m_y * r.GetY() - m_z * r.GetZ();
		float x_ = m_x * r.GetW() + m_w * r.GetX() + m_y * r.GetZ() - m_z * r.GetY();
		float y_ = m_y * r.GetW() + m_w * r.GetY() + m_z * r.GetX() - m_x * r.GetZ();
		float z_ = m_z * r.GetW() + m_w * r.GetZ() + m_x * r.GetY() - m_y * r.GetX();
		
		return new Quaternion(x_, y_, z_, w_);
	}
	
	public Quaternion Mul(Vector3f r)
	{
		float w_ = -m_x * r.GetX() - m_y * r.GetY() - m_z * r.GetZ();
		float x_ =  m_w * r.GetX() + m_y * r.GetZ() - m_z * r.GetY();
		float y_ =  m_w * r.GetY() + m_z * r.GetX() - m_x * r.GetZ();
		float z_ =  m_w * r.GetZ() + m_x * r.GetY() - m_y * r.GetX();
		
		return new Quaternion(x_, y_, z_, w_);
	}

	public Quaternion Sub(Quaternion r)
	{
		return new Quaternion(m_x - r.GetX(), m_y - r.GetY(), m_z - r.GetZ(), m_w - r.GetW());
	}

	public Quaternion Add(Quaternion r)
	{
		return new Quaternion(m_x + r.GetX(), m_y + r.GetY(), m_z + r.GetZ(), m_w + r.GetW());
	}

	public Matrix4f ToRotationMatrix()
	{
		Vector3f forward =  new Vector3f(2.0f * (m_x * m_z - m_w * m_y), 2.0f * (m_y * m_z + m_w * m_x), 1.0f - 2.0f * (m_x * m_x + m_y * m_y));
		Vector3f up = new Vector3f(2.0f * (m_x * m_y + m_w * m_z), 1.0f - 2.0f * (m_x * m_x + m_z * m_z), 2.0f * (m_y * m_z - m_w * m_x));
		Vector3f right = new Vector3f(1.0f - 2.0f * (m_y * m_y + m_z * m_z), 2.0f * (m_x * m_y - m_w * m_z), 2.0f * (m_x * m_z + m_w * m_y));

		return new Matrix4f().InitRotation(forward, up, right);
	}

	public float Dot(Quaternion r)
	{
		return m_x * r.GetX() + m_y * r.GetY() + m_z * r.GetZ() + m_w * r.GetW();
	}

	public Quaternion NLerp(Quaternion dest, float lerpFactor, boolean shortest)
	{
		Quaternion correctedDest = dest;

		if(shortest && this.Dot(dest) < 0)
			correctedDest = new Quaternion(-dest.GetX(), -dest.GetY(), -dest.GetZ(), -dest.GetW());

		return correctedDest.Sub(this).Mul(lerpFactor).Add(this).Normalized();
	}

	public Quaternion SLerp(Quaternion dest, float lerpFactor, boolean shortest)
	{
		final float EPSILON = 1e3f;

		float cos = this.Dot(dest);
		Quaternion correctedDest = dest;

		if(shortest && cos < 0)
		{
			cos = -cos;
			correctedDest = new Quaternion(-dest.GetX(), -dest.GetY(), -dest.GetZ(), -dest.GetW());
		}

		if(Math.abs(cos) >= 1 - EPSILON)
			return NLerp(correctedDest, lerpFactor, false);

		float sin = (float)Math.sqrt(1.0f - cos * cos);
		float angle = (float)Math.atan2(sin, cos);
		float invSin =  1.0f/sin;

		float srcFactor = (float)Math.sin((1.0f - lerpFactor) * angle) * invSin;
		float destFactor = (float)Math.sin((lerpFactor) * angle) * invSin;

		return this.Mul(srcFactor).Add(correctedDest.Mul(destFactor));
	}

	//From Ken Shoemake's "Quaternion Calculus and Fast Animation" article
	public Quaternion(Matrix4f rot)
	{
		float trace = rot.Get(0, 0) + rot.Get(1, 1) + rot.Get(2, 2);

		if(trace > 0)
		{
			float s = 0.5f / (float)Math.sqrt(trace+ 1.0f);
			m_w = 0.25f / s;
			m_x = (rot.Get(1, 2) - rot.Get(2, 1)) * s;
			m_y = (rot.Get(2, 0) - rot.Get(0, 2)) * s;
			m_z = (rot.Get(0, 1) - rot.Get(1, 0)) * s;
		}
		else
		{
			if(rot.Get(0, 0) > rot.Get(1, 1) && rot.Get(0, 0) > rot.Get(2, 2))
			{
				float s = 2.0f * (float)Math.sqrt(1.0f + rot.Get(0, 0) - rot.Get(1, 1) - rot.Get(2, 2));
				m_w = (rot.Get(1, 2) - rot.Get(2, 1)) / s;
				m_x = 0.25f * s;
				m_y = (rot.Get(1, 0) + rot.Get(0, 1)) / s;
				m_z = (rot.Get(2, 0) + rot.Get(0, 2)) / s;
			}
			else if(rot.Get(1, 1) > rot.Get(2, 2))
			{
				float s = 2.0f * (float)Math.sqrt(1.0f + rot.Get(1, 1) - rot.Get(0, 0) - rot.Get(2, 2));
				m_w = (rot.Get(2, 0) - rot.Get(0, 2)) / s;
				m_x = (rot.Get(1, 0) + rot.Get(0, 1)) / s;
				m_y = 0.25f * s;
				m_z = (rot.Get(2, 1) + rot.Get(1, 2)) / s;
			}
			else
			{
				float s = 2.0f * (float)Math.sqrt(1.0f + rot.Get(2, 2) - rot.Get(0, 0) - rot.Get(1, 1));
				m_w = (rot.Get(0, 1) - rot.Get(1, 0) ) / s;
				m_x = (rot.Get(2, 0) + rot.Get(0, 2) ) / s;
				m_y = (rot.Get(1, 2) + rot.Get(2, 1) ) / s;
				m_z = 0.25f * s;
			}
		}

		float length = (float)Math.sqrt(m_x * m_x + m_y * m_y + m_z * m_z + m_w * m_w);
		m_x /= length;
		m_y /= length;
		m_z /= length;
		m_w /= length;
	}

	public Vector3f GetForward()
	{
		return new Vector3f(0,0,1).Rotate(this);
	}

	public Vector3f GetBack()
	{
		return new Vector3f(0,0,-1).Rotate(this);
	}

	public Vector3f GetUp()
	{
		return new Vector3f(0,1,0).Rotate(this);
	}

	public Vector3f GetDown()
	{
		return new Vector3f(0,-1,0).Rotate(this);
	}

	public Vector3f GetRight()
	{
		return new Vector3f(1,0,0).Rotate(this);
	}

	public Vector3f GetLeft()
	{
		return new Vector3f(-1,0,0).Rotate(this);
	}

	public Quaternion Set(float x, float y, float z, float w) { this.m_x = x; this.m_y = y; this.m_z = z; this.m_w = w; return this; }
	public Quaternion Set(Quaternion r) { Set(r.GetX(), r.GetY(), r.GetZ(), r.GetW()); return this; }

	public float GetX()
	{
		return m_x;
	}

	public void SetX(float x)
	{
		this.m_x = x;
	}

	public float GetY()
	{
		return m_y;
	}

	public void SetY(float m_y)
	{
		this.m_y = m_y;
	}

	public float GetZ()
	{
		return m_z;
	}

	public void SetZ(float z)
	{
		this.m_z = z;
	}

	public float GetW()
	{
		return m_w;
	}

	public void SetW(float w)
	{
		this.m_w = w;
	}

	public boolean equals(Quaternion r)
	{
		return m_x == r.GetX() && m_y == r.GetY() && m_z == r.GetZ() && m_w == r.GetW();
	}
}
