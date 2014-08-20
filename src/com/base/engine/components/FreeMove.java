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

import com.base.engine.core.Input;
import com.base.engine.core.Vector3f;

public class FreeMove extends GameComponent
{
	private float m_speed;
	private int   m_forwardKey;
	private int   m_backKey;
	private int   m_leftKey;
	private int   m_rightKey;

	public FreeMove(float speed)
	{
		this(speed, Input.KEY_W, Input.KEY_S, Input.KEY_A, Input.KEY_D);
	}

	public FreeMove(float speed, int forwardKey, int backKey, int leftKey, int rightKey)
	{
		this.m_speed = speed;
		this.m_forwardKey = forwardKey;
		this.m_backKey = backKey;
		this.m_leftKey = leftKey;
		this.m_rightKey = rightKey;
	}

	@Override
	public void Input(float delta)
	{
		float movAmt = m_speed * delta;

		if(Input.GetKey(m_forwardKey))
			Move(GetTransform().GetRot().GetForward(), movAmt);
		if(Input.GetKey(m_backKey))
			Move(GetTransform().GetRot().GetForward(), -movAmt);
		if(Input.GetKey(m_leftKey))
			Move(GetTransform().GetRot().GetLeft(), movAmt);
		if(Input.GetKey(m_rightKey))
			Move(GetTransform().GetRot().GetRight(), movAmt);
	}

	private void Move(Vector3f dir, float amt)
	{
		GetTransform().SetPos(GetTransform().GetPos().Add(dir.Mul(amt)));
	}
}
