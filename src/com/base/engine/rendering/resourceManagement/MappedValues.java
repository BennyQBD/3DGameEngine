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

import com.base.engine.core.Vector3f;

import java.util.HashMap;

public abstract class MappedValues
{
	private HashMap<String, Vector3f> m_vector3fHashMap;
	private HashMap<String, Float>    m_floatHashMap;

	public MappedValues()
	{
		m_vector3fHashMap = new HashMap<String, Vector3f>();
		m_floatHashMap = new HashMap<String, Float>();
	}

	public void AddVector3f(String name, Vector3f vector3f) { m_vector3fHashMap.put(name, vector3f); }
	public void AddFloat(String name, float floatValue) { m_floatHashMap.put(name, floatValue); }

	public Vector3f GetVector3f(String name)
	{
		Vector3f result = m_vector3fHashMap.get(name);
		if(result != null)
			return result;

		return new Vector3f(0,0,0);
	}

	public float GetFloat(String name)
	{
		Float result = m_floatHashMap.get(name);
		if(result != null)
			return result;

		return 0;
	}
}
