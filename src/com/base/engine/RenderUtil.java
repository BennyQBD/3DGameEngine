package com.base.engine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.GL_TEXTURE10;
import static org.lwjgl.opengl.GL13.GL_TEXTURE11;
import static org.lwjgl.opengl.GL13.GL_TEXTURE12;
import static org.lwjgl.opengl.GL13.GL_TEXTURE13;
import static org.lwjgl.opengl.GL13.GL_TEXTURE14;
import static org.lwjgl.opengl.GL13.GL_TEXTURE15;
import static org.lwjgl.opengl.GL13.GL_TEXTURE16;
import static org.lwjgl.opengl.GL13.GL_TEXTURE17;
import static org.lwjgl.opengl.GL13.GL_TEXTURE18;
import static org.lwjgl.opengl.GL13.GL_TEXTURE19;
import static org.lwjgl.opengl.GL13.GL_TEXTURE2;
import static org.lwjgl.opengl.GL13.GL_TEXTURE20;
import static org.lwjgl.opengl.GL13.GL_TEXTURE21;
import static org.lwjgl.opengl.GL13.GL_TEXTURE22;
import static org.lwjgl.opengl.GL13.GL_TEXTURE23;
import static org.lwjgl.opengl.GL13.GL_TEXTURE24;
import static org.lwjgl.opengl.GL13.GL_TEXTURE25;
import static org.lwjgl.opengl.GL13.GL_TEXTURE26;
import static org.lwjgl.opengl.GL13.GL_TEXTURE27;
import static org.lwjgl.opengl.GL13.GL_TEXTURE28;
import static org.lwjgl.opengl.GL13.GL_TEXTURE29;
import static org.lwjgl.opengl.GL13.GL_TEXTURE3;
import static org.lwjgl.opengl.GL13.GL_TEXTURE30;
import static org.lwjgl.opengl.GL13.GL_TEXTURE31;
import static org.lwjgl.opengl.GL13.GL_TEXTURE4;
import static org.lwjgl.opengl.GL13.GL_TEXTURE5;
import static org.lwjgl.opengl.GL13.GL_TEXTURE6;
import static org.lwjgl.opengl.GL13.GL_TEXTURE7;
import static org.lwjgl.opengl.GL13.GL_TEXTURE8;
import static org.lwjgl.opengl.GL13.GL_TEXTURE9;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

public class RenderUtil
{
	public static void clearScreen()
	{
		//TODO: Stencil Buffer
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public static void setTextures(boolean enabled)
	{
		if(enabled)
			glEnable(GL_TEXTURE_2D);
		else
			glDisable(GL_TEXTURE_2D);
	}
	
	public static void unbindTextures()
	{
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public static void setClearColor(Vector3f color)
	{
		glClearColor(color.getX(), color.getY(), color.getZ(), 1.0f);
	}
	
	public static void initGraphics()
	{
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		glFrontFace(GL_CW);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		
		glEnable(GL_DEPTH_CLAMP);
		
		glEnable(GL_TEXTURE_2D);
	}
	
	public static String getOpenGLVersion()
	{
		return glGetString(GL_VERSION);
	}
}
