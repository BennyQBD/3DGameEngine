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

package com.base.engine.rendering.resourceManagement;

import static org.lwjgl.opengl.GL15.*;

public class MeshResource
{
	private int m_vbo;
	private int m_ibo;
	private int m_size;
	private int m_refCount;

	public MeshResource(int size)
	{
		m_vbo = glGenBuffers();
		m_ibo = glGenBuffers();
		this.m_size = size;
		this.m_refCount = 1;
	}

	@Override
	protected void finalize()
	{
		glDeleteBuffers(m_vbo);
		glDeleteBuffers(m_ibo);
	}

	public void AddReference()
	{
		m_refCount++;
	}

	public boolean RemoveReference()
	{
		m_refCount--;
		return m_refCount == 0;
	}

	public int GetVbo()  { return m_vbo; }
	public int GetIbo()  { return m_ibo; }
	public int GetSize() { return m_size; }
}
