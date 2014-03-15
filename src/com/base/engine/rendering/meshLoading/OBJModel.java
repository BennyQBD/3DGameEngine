package com.base.engine.rendering.meshLoading;

import com.base.engine.core.Util;
import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class OBJModel
{
	private ArrayList<Vector3f> positions;
	private ArrayList<Vector2f> texCoords;
	private ArrayList<Vector3f> normals;
	private ArrayList<OBJIndex> indices;
	private boolean hasTexCoords;
	private boolean hasNormals;

	public OBJModel(String fileName)
	{
		positions = new ArrayList<Vector3f>();
		texCoords = new ArrayList<Vector2f>();
		normals = new ArrayList<Vector3f>();
		indices = new ArrayList<OBJIndex>();
		hasTexCoords = false;
		hasNormals = false;

		BufferedReader meshReader = null;

		try
		{
			meshReader = new BufferedReader(new FileReader(fileName));
			String line;

			while((line = meshReader.readLine()) != null)
			{
				String[] tokens = line.split(" ");
				tokens = Util.removeEmptyStrings(tokens);

				if(tokens.length == 0 || tokens[0].equals("#"))
					continue;
				else if(tokens[0].equals("v"))
				{
					positions.add(new Vector3f(Float.valueOf(tokens[1]),
							Float.valueOf(tokens[2]),
							Float.valueOf(tokens[3])));
				}
				else if(tokens[0].equals("vt"))
				{
					texCoords.add(new Vector2f(Float.valueOf(tokens[1]),
							Float.valueOf(tokens[2])));
				}
				else if(tokens[0].equals("vn"))
				{
					normals.add(new Vector3f(Float.valueOf(tokens[1]),
							Float.valueOf(tokens[2]),
							Float.valueOf(tokens[3])));
				}
				else if(tokens[0].equals("f"))
				{
					for(int i = 0; i < tokens.length - 3; i++)
					{
						indices.add(parseOBJIndex(tokens[1]));
						indices.add(parseOBJIndex(tokens[2 + i]));
						indices.add(parseOBJIndex(tokens[3 + i]));
					}
				}
			}

			meshReader.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}

	private int FindPreviousVertexIndex(ArrayList<OBJIndex> sortedIndices, OBJIndex currentIndex, int currentIndicesIndex)
	{
		int start = 0;
		int end = sortedIndices.size();
		int current = (end - start) / 2 + start;
		int previous = start;

		while(current != previous)
		{
			OBJIndex testIndex = sortedIndices.get(current);

			if(testIndex.vertexIndex == currentIndex.vertexIndex)
			{
				int countStart = current;

				for(countStart = current; countStart >= 0; countStart--)
				{
					if(sortedIndices.get(countStart).vertexIndex != currentIndex.vertexIndex)
						break;
				}
				countStart++;

				for(int i = countStart; i < sortedIndices.size(); i++)
				{
					OBJIndex possibleIndex = sortedIndices.get(i);

					if(sortedIndices.get(i).vertexIndex != currentIndex.vertexIndex)
						break;

					if(possibleIndex == currentIndex)
						continue;

					if((!hasTexCoords || possibleIndex.texCoordIndex == currentIndex.texCoordIndex)
						&& (!hasNormals || possibleIndex.normalIndex == currentIndex.normalIndex)
						&& (possibleIndex.orderTag < currentIndicesIndex))
					{
						return possibleIndex.orderTag;
					}
				}

				return -1;
			}
			else
			{
				if(testIndex.vertexIndex < currentIndex.vertexIndex)
					start = current;
				else
					end = current;
			}

			previous = current;
			current = (end - start) / 2 + start;
		}

		return -1;
	}

	public IndexedModel toIndexedModel()
	{
		IndexedModel result = new IndexedModel();
		HashMap<Integer, Integer> indexMap = new HashMap<Integer, Integer>();

		ArrayList<OBJIndex> sortedIndices = new ArrayList<OBJIndex>();
		for (int i = 0; i < indices.size(); i++)
		{
			OBJIndex index = indices.get(i);
			index.orderTag = i;
			sortedIndices.add(index);
		}

		Collections.sort(sortedIndices);

		int currentVertexIndex = 0;
		for(int i = 0; i < indices.size(); i++)
		{
			OBJIndex currentIndex = indices.get(i);

			Vector3f currentPosition = positions.get(currentIndex.vertexIndex);
			Vector2f currentTexCoord;
			Vector3f currentNormal;

			if(hasTexCoords)
				currentTexCoord = texCoords.get(currentIndex.texCoordIndex);
			else
				currentTexCoord = new Vector2f(0,0);

			if(hasNormals)
				currentNormal = normals.get(currentIndex.normalIndex);
			else
				currentNormal = new Vector3f(0,0,0);

			int previousVertexIndex = FindPreviousVertexIndex(sortedIndices, currentIndex, i);

			if(previousVertexIndex == -1)
			{
				indexMap.put(i, currentVertexIndex);

				result.getPositions().add(currentPosition);
				result.getTexCoords().add(currentTexCoord);
				result.getNormals().add(currentNormal);
				result.getIndices().add(currentVertexIndex);
				currentVertexIndex++;
			}
			else
				result.getIndices().add(indexMap.get(previousVertexIndex));

//			for(int j = 0; j < i; j++)
//			{
//				OBJIndex oldIndex = indices.get(j);
//
//				if(currentIndex.vertexIndex == oldIndex.vertexIndex
//					&& currentIndex.texCoordIndex == oldIndex.texCoordIndex
//					&& currentIndex.normalIndex == oldIndex.normalIndex)
//				{
//					previousVertexIndex = j;
//					break;
//				}
//			}
//
//			if(previousVertexIndex == -1)
//			{
//				indexMap.put(i, currentVertexIndex);
//
//				result.getPositions().add(currentPosition);
//				result.getTexCoords().add(currentTexCoord);
//				result.getNormals().add(currentNormal);
//				result.getIndices().add(currentVertexIndex);
//				currentVertexIndex++;
//			}
//			else
//				result.getIndices().add(indexMap.get(previousVertexIndex));
		}

		return result;
	}

	private OBJIndex parseOBJIndex(String token)
	{
		String[] values = token.split("/");

		OBJIndex result = new OBJIndex();
		result.vertexIndex = Integer.parseInt(values[0]) - 1;

		if(values.length > 1)
		{
			hasTexCoords = true;
			result.texCoordIndex = Integer.parseInt(values[1]) - 1;

			if(values.length > 2)
			{
				hasNormals = true;
				result.normalIndex = Integer.parseInt(values[2]) - 1;
			}
		}

		return result;
	}

//	public ArrayList<Vector3f> getPositions() { return positions; }
//	public ArrayList<Vector2f> getTexCoords() { return texCoords; }
//	public ArrayList<Vector3f> getNormals() { return normals; }
//	public ArrayList<OBJIndex> getIndices() { return indices; }
}
