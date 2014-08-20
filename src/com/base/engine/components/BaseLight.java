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

package com.base.engine.components;

import com.base.engine.core.CoreEngine;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Shader;

public class BaseLight extends GameComponent
{
	private Vector3f m_color;
	private float    m_intensity;
	private Shader   m_shader;
	
	public BaseLight(Vector3f color, float intensity)
	{
		this.m_color = color;
		this.m_intensity = intensity;
	}

	@Override
	public void AddToEngine(CoreEngine engine)
	{
		engine.GetRenderingEngine().AddLight(this);
	}

	public void SetShader(Shader shader)
	{
		this.m_shader = shader;
	}

	public Shader GetShader()
	{
		return m_shader;
	}

	public Vector3f GetColor()
	{
		return m_color;
	}

	public void SetColor(Vector3f color)
	{
		this.m_color = color;
	}

	public float GetIntensity()
	{
		return m_intensity;
	}

	public void SetIntensity(float intensity)
	{
		this.m_intensity = intensity;
	}
}
