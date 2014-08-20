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

package com.base.engine.rendering;

import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;

public class Vertex
{
	public static final int SIZE = 11;
	
	private Vector3f m_pos;
	private Vector2f m_texCoord;
	private Vector3f m_normal;
	private Vector3f m_tangent;
	
	public Vertex(Vector3f pos)
	{
		this(pos, new Vector2f(0,0));
	}
	
	public Vertex(Vector3f pos, Vector2f texCoord)
	{
		this(pos, texCoord, new Vector3f(0,0,0));
	}
	
	public Vertex(Vector3f pos, Vector2f texCoord, Vector3f normal)
	{
		this(pos, texCoord, normal, new Vector3f(0,0,0));
	}

	public Vertex(Vector3f pos, Vector2f texCoord, Vector3f normal, Vector3f tangent)
	{
		this.m_pos = pos;
		this.m_texCoord = texCoord;
		this.m_normal = normal;
		this.m_tangent = tangent;
	}

	public Vector3f GetTangent() {
		return m_tangent;
	}

	public void SetTangent(Vector3f tangent) {
		this.m_tangent = tangent;
	}

	public Vector3f GetPos()
	{
		return m_pos;
	}

	public void SetPos(Vector3f pos)
	{
		this.m_pos = pos;
	}

	public Vector2f GetTexCoord()
	{
		return m_texCoord;
	}

	public void SetTexCoord(Vector2f texCoord)
	{
		this.m_texCoord = texCoord;
	}

	public Vector3f GetNormal()
	{
		return m_normal;
	}

	public void SetNormal(Vector3f normal)
	{
		this.m_normal = normal;
	}
}
