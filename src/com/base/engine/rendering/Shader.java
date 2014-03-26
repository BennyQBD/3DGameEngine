package com.base.engine.rendering;

import com.base.engine.core.*;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Shader
{
	private int program;
	private HashMap<String, Integer> uniforms;
	
	public Shader(String fileName)
	{
		program = glCreateProgram();
		uniforms = new HashMap<String, Integer>();
		
		if(program == 0)
		{
			System.err.println("Shader creation failed: Could not find valid memory location in constructor");
			System.exit(1);
		}

		String vertexShaderText = loadShader(fileName + ".vs");
		String fragmentShaderText = loadShader(fileName + ".fs");

		addVertexShader(vertexShaderText);
		addFragmentShader(fragmentShaderText);

		addAllAttributes(vertexShaderText);

		compileShader();

		addAllUniforms(vertexShaderText);
		addAllUniforms(fragmentShaderText);
	}
	
	public void bind()
	{
		glUseProgram(program);
	}

	public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine)
	{
		
	}

	private void addAllAttributes(String shaderText)
	{
		final String ATTRIBUTE_KEYWORD = "attribute";
		int attributeStartLocation = shaderText.indexOf(ATTRIBUTE_KEYWORD);
		int attribNumber = 0;
		while(attributeStartLocation != -1)
		{
			int begin = attributeStartLocation + ATTRIBUTE_KEYWORD.length() + 1;
			int end = shaderText.indexOf(";", begin);

			String attributeLine = shaderText.substring(begin, end);
			String attributeName = attributeLine.substring(attributeLine.indexOf(' ') + 1, attributeLine.length());

			setAttribLocation(attributeName, attribNumber);
			attribNumber++;

			attributeStartLocation = shaderText.indexOf(ATTRIBUTE_KEYWORD, attributeStartLocation + ATTRIBUTE_KEYWORD.length());
		}
	}

	private class GLSLStruct
	{
		public String name;
		public String type;
	}

	private HashMap<String, ArrayList<GLSLStruct>> findUniformStructs(String shaderText)
	{
		HashMap<String, ArrayList<GLSLStruct>> result = new HashMap<String, ArrayList<GLSLStruct>>();

		final String STRUCT_KEYWORD = "struct";
		int structStartLocation = shaderText.indexOf(STRUCT_KEYWORD);
		while(structStartLocation != -1)
		{
			int nameBegin = structStartLocation + STRUCT_KEYWORD.length() + 1;
			int braceBegin = shaderText.indexOf("{", nameBegin);
			int braceEnd = shaderText.indexOf("}", braceBegin);

			String structName = shaderText.substring(nameBegin, braceBegin).trim();
			ArrayList<GLSLStruct> glslStructs = new ArrayList<GLSLStruct>();

			int componentSemicolonPos = shaderText.indexOf(";", braceBegin);
			while(componentSemicolonPos != -1 && componentSemicolonPos < braceEnd)
			{
				int componentNameStart = componentSemicolonPos;

				while(!Character.isWhitespace(shaderText.charAt(componentNameStart - 1)))
					componentNameStart--;

				int componentTypeEnd = componentNameStart - 1;
				int componentTypeStart = componentTypeEnd;

				while(!Character.isWhitespace(shaderText.charAt(componentTypeStart - 1)))
					componentTypeStart--;

				String componentName = shaderText.substring(componentNameStart, componentSemicolonPos);
				String componentType = shaderText.substring(componentTypeStart, componentTypeEnd);

				GLSLStruct glslStruct = new GLSLStruct();
				glslStruct.name = componentName;
				glslStruct.type = componentType;

				glslStructs.add(glslStruct);

				componentSemicolonPos = shaderText.indexOf(";", componentSemicolonPos + 1);
			}

			result.put(structName, glslStructs);

			structStartLocation = shaderText.indexOf(STRUCT_KEYWORD, structStartLocation + STRUCT_KEYWORD.length());
		}

		return result;
	}

	private void addAllUniforms(String shaderText)
	{
		HashMap<String, ArrayList<GLSLStruct>> structs = findUniformStructs(shaderText);

		final String UNIFORM_KEYWORD = "uniform";
		int uniformStartLocation = shaderText.indexOf(UNIFORM_KEYWORD);
		while(uniformStartLocation != -1)
		{
			int begin = uniformStartLocation + UNIFORM_KEYWORD.length() + 1;
			int end = shaderText.indexOf(";", begin);

			String uniformLine = shaderText.substring(begin, end);

			int whiteSpacePos = uniformLine.indexOf(' ');
			String uniformName = uniformLine.substring(whiteSpacePos + 1, uniformLine.length());
			String uniformType = uniformLine.substring(0, whiteSpacePos);

			addUniformWithStructCheck(uniformName, uniformType, structs);

			uniformStartLocation = shaderText.indexOf(UNIFORM_KEYWORD, uniformStartLocation + UNIFORM_KEYWORD.length());
		}
	}

	private void addUniformWithStructCheck(String uniformName, String uniformType, HashMap<String, ArrayList<GLSLStruct>> structs)
	{
		boolean addThis = true;
		ArrayList<GLSLStruct> structComponents = structs.get(uniformType);

		if(structComponents != null)
		{
			addThis = false;
			for(GLSLStruct struct : structComponents)
			{
				addUniformWithStructCheck(uniformName + "." + struct.name, struct.type, structs);
			}
		}

		if(addThis)
			addUniform(uniformName);
	}

	private void addUniform(String uniform)
	{
		int uniformLocation = glGetUniformLocation(program, uniform);
		
		if(uniformLocation == 0xFFFFFFFF)
		{
			System.err.println("Error: Could not find uniform: " + uniform);
			new Exception().printStackTrace();
			System.exit(1);
		}
		
		uniforms.put(uniform, uniformLocation);
	}

	private void addVertexShader(String text)
	{
		addProgram(text, GL_VERTEX_SHADER);
	}

	private void addGeometryShader(String text)
	{
		addProgram(text, GL_GEOMETRY_SHADER);
	}

	private void addFragmentShader(String text)
	{
		addProgram(text, GL_FRAGMENT_SHADER);
	}

	private void setAttribLocation(String attributeName, int location)
	{
		glBindAttribLocation(program, location, attributeName);
	}

	private void compileShader()
	{
		glLinkProgram(program);
		
		if(glGetProgrami(program, GL_LINK_STATUS) == 0)
		{
			System.err.println(glGetProgramInfoLog(program, 1024));
			System.exit(1);
		}
		
		glValidateProgram(program);
		
		if(glGetProgrami(program, GL_VALIDATE_STATUS) == 0)
		{
			System.err.println(glGetProgramInfoLog(program, 1024));
			System.exit(1);
		}
	}

	private void addProgram(String text, int type)
	{
		int shader = glCreateShader(type);
		
		if(shader == 0)
		{
			System.err.println("Shader creation failed: Could not find valid memory location when adding shader");
			System.exit(1);
		}
		
		glShaderSource(shader, text);
		glCompileShader(shader);
		
		if(glGetShaderi(shader, GL_COMPILE_STATUS) == 0)
		{
			System.err.println(glGetShaderInfoLog(shader, 1024));
			System.exit(1);
		}
		
		glAttachShader(program, shader);
	}

	private static String loadShader(String fileName)
	{
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader shaderReader = null;
		final String INCLUDE_DIRECTIVE = "#include";

		try
		{
			shaderReader = new BufferedReader(new FileReader("./res/shaders/" + fileName));
			String line;

			while((line = shaderReader.readLine()) != null)
			{
				if(line.startsWith(INCLUDE_DIRECTIVE))
				{
					shaderSource.append(loadShader(line.substring(INCLUDE_DIRECTIVE.length() + 2, line.length() - 1)));
				}
				else
					shaderSource.append(line).append("\n");
			}
			
			shaderReader.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		
		return shaderSource.toString();
	}
	
	public void setUniformi(String uniformName, int value)
	{
		glUniform1i(uniforms.get(uniformName), value);
	}
	
	public void setUniformf(String uniformName, float value)
	{
		glUniform1f(uniforms.get(uniformName), value);
	}
	
	public void setUniform(String uniformName, Vector3f value)
	{
		glUniform3f(uniforms.get(uniformName), value.getX(), value.getY(), value.getZ());
	}
	
	public void setUniform(String uniformName, Matrix4f value)
	{
		glUniformMatrix4(uniforms.get(uniformName), true, Util.createFlippedBuffer(value));
	}
}
