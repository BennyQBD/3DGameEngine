package com.base.game;

import com.base.engine.core.*;
import com.base.engine.rendering.*;

public class TestGame extends Game
{
//	private Camera camera;

	public void init()
	{
//		camera = new Camera();

		float fieldDepth = 10.0f;
		float fieldWidth = 10.0f;

		Vertex[] vertices = new Vertex[] { 	new Vertex( new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
				new Vertex( new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3), new Vector2f(0.0f, 1.0f)),
				new Vertex( new Vector3f(fieldWidth * 3, 0.0f, -fieldDepth), new Vector2f(1.0f, 0.0f)),
				new Vertex( new Vector3f(fieldWidth * 3, 0.0f, fieldDepth * 3), new Vector2f(1.0f, 1.0f))};

		int indices[] = { 0, 1, 2,
				2, 1, 3};

		Mesh mesh = new Mesh(vertices, indices, true);
		Material material = new Material(new Texture("test.png"), new Vector3f(1,1,1), 1, 8);

		MeshRenderer meshRenderer = new MeshRenderer(mesh, material);

		GameObject planeObject = new GameObject();
		planeObject.addComponent(meshRenderer);
		planeObject.getTransform().setTranslation(0, -1, 5);

		getRootObject().addChild(planeObject);

//		Transform.setProjection(70f, Window.getWidth(), Window.getHeight(), 0.1f, 1000);
//		Transform.setCamera(camera);
	}

//	public void input()
//	{
//		camera.input();
//		root.input();
//	}
//
//	public void update()
//	{
//		root.getTransform().setTranslation(0, -1, 5);
//		root.update();
//	}
//
//	public void render()
//	{
//		root.render();
//	}
}
