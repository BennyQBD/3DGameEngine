package com.base.engine.rendering.meshLoading;

public class OBJIndex implements Comparable<OBJIndex>
{
	public int vertexIndex;
	public int texCoordIndex;
	public int normalIndex;
	public int orderTag;

	@Override
	public int compareTo(OBJIndex objIndex)
	{
		final int BEFORE = -1;
		final int AFTER = 1;
		final int EQUAL = 0;

		if(vertexIndex == objIndex.vertexIndex)
			return EQUAL;
		else if(vertexIndex < objIndex.vertexIndex)
			return BEFORE;

		return AFTER;
	}
}
