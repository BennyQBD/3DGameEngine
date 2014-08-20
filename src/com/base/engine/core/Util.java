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

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import com.base.engine.rendering.Vertex;
import org.lwjgl.BufferUtils;

public class Util
{
	public static FloatBuffer CreateFloatBuffer(int size)
	{
		return BufferUtils.createFloatBuffer(size);
	}
	
	public static IntBuffer CreateIntBuffer(int size)
	{
		return BufferUtils.createIntBuffer(size);
	}

	public static ByteBuffer CreateByteBuffer(int size)
	{
		return BufferUtils.createByteBuffer(size);
	}
	
	public static IntBuffer CreateFlippedBuffer(int... values)
	{
		IntBuffer buffer = CreateIntBuffer(values.length);
		buffer.put(values);
		buffer.flip();
		
		return buffer;
	}
	
	public static FloatBuffer CreateFlippedBuffer(Vertex[] vertices)
	{
		FloatBuffer buffer = CreateFloatBuffer(vertices.length * Vertex.SIZE);
		
		for(int i = 0; i < vertices.length; i++)
		{
			buffer.put(vertices[i].GetPos().GetX());
			buffer.put(vertices[i].GetPos().GetY());
			buffer.put(vertices[i].GetPos().GetZ());
			buffer.put(vertices[i].GetTexCoord().GetX());
			buffer.put(vertices[i].GetTexCoord().GetY());
			buffer.put(vertices[i].GetNormal().GetX());
			buffer.put(vertices[i].GetNormal().GetY());
			buffer.put(vertices[i].GetNormal().GetZ());
			buffer.put(vertices[i].GetTangent().GetX());
			buffer.put(vertices[i].GetTangent().GetY());
			buffer.put(vertices[i].GetTangent().GetZ());
		}
		
		buffer.flip();
		
		return buffer;
	}
	
	public static FloatBuffer CreateFlippedBuffer(Matrix4f value)
	{
		FloatBuffer buffer = CreateFloatBuffer(4 * 4);
		
		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 4; j++)
				buffer.put(value.Get(i, j));
		
		buffer.flip();
		
		return buffer;
	}
	
	public static String[] RemoveEmptyStrings(String[] data)
	{
		ArrayList<String> result = new ArrayList<String>();
		
		for(int i = 0; i < data.length; i++)
			if(!data[i].equals(""))
				result.add(data[i]);
		
		String[] res = new String[result.size()];
		result.toArray(res);
		
		return res;
	}
	
	public static int[] ToIntArray(Integer[] data)
	{
		int[] result = new int[data.length];
		
		for(int i = 0; i < data.length; i++)
			result[i] = data[i].intValue();
		
		return result;
	}
}
