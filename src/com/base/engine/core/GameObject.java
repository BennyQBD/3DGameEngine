package com.base.engine.core;

import com.base.engine.rendering.Shader;

import java.util.ArrayList;

public class GameObject
{
	private ArrayList<GameObject> children;
	private ArrayList<GameComponent> components;
	private Transform transform;

	public GameObject()
	{
		children = new ArrayList<GameObject>();
		components = new ArrayList<GameComponent>();
		transform = new Transform();
	}

	public void addChild(GameObject child)
	{
		children.add(child);
	}

	public void addComponent(GameComponent component)
	{
		components.add(component);
	}

	public void input()
	{
		for(GameComponent component : components)
			component.input(transform);

		for(GameObject child : children)
			child.input();
	}

	public void update()
	{
		for(GameComponent component : components)
			component.update(transform);

		for(GameObject child : children)
			child.update();
	}

	public void render(Shader shader)
	{
		for(GameComponent component : components)
			component.render(transform, shader);

		for(GameObject child : children)
			child.render(shader);
	}

	public Transform getTransform()
	{
		return transform;
	}
}
