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

import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL20.glCreateProgram;

public class ShaderResource
{
	private int                      m_program;
	private HashMap<String, Integer> m_uniforms;
	private ArrayList<String>        m_uniformNames;
	private ArrayList<String>        m_uniformTypes;
	private int                      m_refCount;

	public ShaderResource()
	{
		this.m_program = glCreateProgram();
		this.m_refCount = 1;

		if(m_program == 0)
		{
			System.err.println("Shader creation failed: Could not find valid memory location in constructor");
			System.exit(1);
		}

		m_uniforms = new HashMap<String, Integer>();
		m_uniformNames = new ArrayList<String>();
		m_uniformTypes = new ArrayList<String>();
	}

	@Override
	protected void finalize()
	{
		glDeleteBuffers(m_program);
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

	public int GetProgram()                       { return m_program; }
	public HashMap<String, Integer> GetUniforms() { return m_uniforms; }
	public ArrayList<String> GetUniformNames()    { return m_uniformNames; }
	public ArrayList<String> GetUniformTypes()    { return m_uniformTypes; }
}
