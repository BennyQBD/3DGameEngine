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

import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;

public class TextureResource
{
	private int m_id;
	private int m_refCount;

	public TextureResource()
	{
		this.m_id = glGenTextures();
		this.m_refCount = 1;
	}

	@Override
	protected void finalize()
	{
		glDeleteBuffers(m_id);
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

	public int GetId() { return m_id; }
}
