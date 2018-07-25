package com.casualgames.upperbound.objects;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

public class Barikada extends Tiles {

	private Random random;
	private int colorCode;
	private int[] rArray, gArray, bArray;

	public Barikada(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		rectangle.setX(x);
		rectangle.setY(y);
		rectangle.setWidth(width);
		rectangle.setHeight(height);


		rArray = new int[]{234, 176, 230, 225, 222, 244, 104, 251, 197,  95, 249, 252, 252, 106, 95, 252, 99, 99, 255};
		gArray = new int[]{76, 209, 129, 236, 227, 134, 190, 186, 252, 252, 252, 158, 200, 252, 168, 95, 234, 255, 230};
		bArray = new int[]{57, 53, 180, 179, 78, 109, 102, 19, 32,  148, 95, 95, 95, 95, 252, 116, 255, 169, 40};

		random = new Random();
		colorCode = random.nextInt(19);
	}

	@Override
	public void update() {

	}

	@Override
	public void render(ShapeRenderer shapeRenderer) {
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//		shapeRenderer.setColor(121/255f, 94/255f, 255/255f, 0);
		shapeRenderer.setColor(rArray[colorCode]/255f, gArray[colorCode]/255f, bArray[colorCode]/255f, 0);
		shapeRenderer.rect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
		shapeRenderer.end();
	}

	public int getColor(){
		return this.colorCode;
	}

}
