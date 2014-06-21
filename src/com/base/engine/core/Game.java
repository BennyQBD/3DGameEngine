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

import com.base.engine.rendering.RenderingEngine;

public abstract class Game
{
	private GameObject root;

	public void init() {}

	public void input(float delta)
	{
		getRootObject().inputAll(delta);
	}

	public void update(float delta)
	{
		getRootObject().updateAll(delta);
	}

	public void render(RenderingEngine renderingEngine)
	{
		renderingEngine.render(getRootObject());
	}

	public void addObject(GameObject object)
	{
		getRootObject().addChild(object);
	}

	private GameObject getRootObject()
	{
		if(root == null)
			root = new GameObject();

		return root;
	}

	public void setEngine(CoreEngine engine) { getRootObject().setEngine(engine); }
}
