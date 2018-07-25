package com.casualgames.upperbound.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Tiles {

	protected float x, y, width, height;
	protected float oneThrid;
	protected Rectangle rectangle;

//	protected Vector2 coordinates;

	public Tiles(){
		this.width = Gdx.graphics.getWidth()/20;
		this.height = Gdx.graphics.getHeight()/5;
		this.x = x;
		this.y = Gdx.graphics.getHeight()/2-height/2;

		rectangle = new Rectangle(x, y, width, height);

		this.oneThrid = height/3;

//		coordinates = new Vector2(x,y);
	}

	public abstract void update();

	public abstract void render(ShapeRenderer shapeRenderer);

//	public Vector2 getCoordinates() {
//		return coordinates;
//	}

	public Rectangle getBounds(){
		return this.rectangle;
//		return new Rectangle(coordinates.x, coordinates.y, width, height);
	}


	public void setY(float y) {
		rectangle.setY(y);
//		coordinates.set(x, y);
	}

	public float getX() {

		return x;
	}

	public float getY() {
		return rectangle.getY();
	}

	public float getWidth() {

		return width;
	}

	public float getHeight() {
		return height;
	}

	public float getOneThrid() {
		return oneThrid;
	}
}
