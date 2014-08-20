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

import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;

import java.util.ArrayList;

public class IndexedModel
{
	private ArrayList<Vector3f> m_positions;
	private ArrayList<Vector2f> m_texCoords;
	private ArrayList<Vector3f> m_normals;
	private ArrayList<Vector3f> m_tangents;
	private ArrayList<Integer>  m_indices;

	public IndexedModel()
	{
		m_positions = new ArrayList<Vector3f>();
		m_texCoords = new ArrayList<Vector2f>();
		m_normals = new ArrayList<Vector3f>();
		m_tangents = new ArrayList<Vector3f>();
		m_indices = new ArrayList<Integer>();
	}

	public void CalcNormals()
	{
		for(int i = 0; i < m_indices.size(); i += 3)
		{
			int i0 = m_indices.get(i);
			int i1 = m_indices.get(i + 1);
			int i2 = m_indices.get(i + 2);

			Vector3f v1 = m_positions.get(i1).Sub(m_positions.get(i0));
			Vector3f v2 = m_positions.get(i2).Sub(m_positions.get(i0));

			Vector3f normal = v1.Cross(v2).Normalized();

			m_normals.get(i0).Set(m_normals.get(i0).Add(normal));
			m_normals.get(i1).Set(m_normals.get(i1).Add(normal));
			m_normals.get(i2).Set(m_normals.get(i2).Add(normal));
		}

		for(int i = 0; i < m_normals.size(); i++)
			m_normals.get(i).Set(m_normals.get(i).Normalized());
	}

	public void CalcTangents()
	{
		for(int i = 0; i < m_indices.size(); i += 3)
		{
			int i0 = m_indices.get(i);
			int i1 = m_indices.get(i + 1);
			int i2 = m_indices.get(i + 2);

			Vector3f edge1 = m_positions.get(i1).Sub(m_positions.get(i0));
			Vector3f edge2 = m_positions.get(i2).Sub(m_positions.get(i0));

			float deltaU1 = m_texCoords.get(i1).GetX() - m_texCoords.get(i0).GetX();
			float deltaV1 = m_texCoords.get(i1).GetY() - m_texCoords.get(i0).GetY();
			float deltaU2 = m_texCoords.get(i2).GetX() - m_texCoords.get(i0).GetX();
			float deltaV2 = m_texCoords.get(i2).GetY() - m_texCoords.get(i0).GetY();

			float dividend = (deltaU1*deltaV2 - deltaU2*deltaV1);
			//TODO: The first 0.0f may need to be changed to 1.0f here.
			float f = dividend == 0 ? 0.0f : 1.0f/dividend;

			Vector3f tangent = new Vector3f(0,0,0);
			tangent.SetX(f * (deltaV2 * edge1.GetX() - deltaV1 * edge2.GetX()));
			tangent.SetY(f * (deltaV2 * edge1.GetY() - deltaV1 * edge2.GetY()));
			tangent.SetZ(f * (deltaV2 * edge1.GetZ() - deltaV1 * edge2.GetZ()));

			m_tangents.get(i0).Set(m_tangents.get(i0).Add(tangent));
			m_tangents.get(i1).Set(m_tangents.get(i1).Add(tangent));
			m_tangents.get(i2).Set(m_tangents.get(i2).Add(tangent));
		}

		for(int i = 0; i < m_tangents.size(); i++)
			m_tangents.get(i).Set(m_tangents.get(i).Normalized());
	}

	public ArrayList<Vector3f> GetPositions() { return m_positions; }
	public ArrayList<Vector2f> GetTexCoords() { return m_texCoords; }
	public ArrayList<Vector3f> GetNormals() { return m_normals; }
	public ArrayList<Vector3f> GetTangents() { return m_tangents; }
	public ArrayList<Integer>  GetIndices() { return m_indices; }
}
