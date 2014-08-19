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

import com.base.engine.rendering.resourceManagement.MappedValues;

import java.util.HashMap;

public class Material extends MappedValues
{
	private HashMap<String, Texture> textureHashMap;

	public Material(Texture diffuse, float specularIntensity, float specularPower, Texture normal,
	                Texture dispMap, float dispMapScale, float dispMapOffset)
	{
		super();
		textureHashMap = new HashMap<String, Texture>();
		addTexture("diffuse", diffuse);
		addFloat("specularIntensity", specularIntensity);
		addFloat("specularPower", specularPower);
		addTexture("normalMap", normal);
		addTexture("dispMap", dispMap);

		float baseBias = dispMapScale/2.0f;
		addFloat("dispMapScale", dispMapScale);
		addFloat("dispMapBias", -baseBias + baseBias * dispMapOffset);
	}

	public void addTexture(String name, Texture texture) { textureHashMap.put(name, texture); }

	public Texture getTexture(String name)
	{
		Texture result = textureHashMap.get(name);
		if(result != null)
			return result;

		return new Texture("test.png");
	}
}
