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

import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Attenuation;
import com.base.engine.rendering.Shader;

public class PointLight extends BaseLight
{
	private static final int COLOR_DEPTH = 256;

	private Attenuation m_attenuation;
	private float       m_range;
	
	public PointLight(Vector3f color, float intensity, Attenuation attenuation)
	{
		super(color, intensity);
		this.m_attenuation = attenuation;

		float a = attenuation.GetExponent();
		float b = attenuation.GetLinear();
		float c = attenuation.GetConstant() - COLOR_DEPTH * GetIntensity() * GetColor().Max();

		this.m_range = (float)((-b + Math.sqrt(b * b - 4 * a * c))/(2 * a));

		SetShader(new Shader("forward-point"));
	}

	public float GetRange()
	{
		return m_range;
	}

	public void SetRange(float range)
	{
		this.m_range = range;
	}

	public Attenuation GetAttenuation()
	{
		return m_attenuation;
	}
}
