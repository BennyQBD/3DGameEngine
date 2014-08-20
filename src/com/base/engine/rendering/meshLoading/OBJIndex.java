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

package com.base.engine.rendering.meshLoading;

public class OBJIndex
{
	private int m_vertexIndex;
	private int m_texCoordIndex;
	private int m_normalIndex;

	public int GetVertexIndex()   { return m_vertexIndex; }
	public int GetTexCoordIndex() { return m_texCoordIndex; }
	public int GetNormalIndex()   { return m_normalIndex; }

	public void SetVertexIndex(int val)   { m_vertexIndex = val; }
	public void SetTexCoordIndex(int val) { m_texCoordIndex = val; }
	public void SetNormalIndex(int val)   { m_normalIndex = val; }

	@Override
	public boolean equals(Object obj)
	{
		OBJIndex index = (OBJIndex)obj;

		return m_vertexIndex == index.m_vertexIndex
				&& m_texCoordIndex == index.m_texCoordIndex
				&& m_normalIndex == index.m_normalIndex;
	}

	@Override
	public int hashCode()
	{
		final int BASE = 17;
		final int MULTIPLIER = 31;

		int result = BASE;

		result = MULTIPLIER * result + m_vertexIndex;
		result = MULTIPLIER * result + m_texCoordIndex;
		result = MULTIPLIER * result + m_normalIndex;

		return result;
	}
}
