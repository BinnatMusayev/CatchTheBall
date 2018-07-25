package com.casualgames.upperbound.objects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class LeftTile extends Tiles{

	private int colorCode;
	private int[] rArray, gArray, bArray;

	public LeftTile(int colorCode) {
//		this.x = 2*width;
		rectangle.setX(2*width);

		//93 198 204
		//255 73 73
		//122 147 255
		//255 162 63
		//110 137 244
		this.colorCode = colorCode;

		rArray = new int[]{93, 255, 122, 255, 110};
		gArray = new int[]{198, 73, 147, 162, 137};
		bArray = new int[]{204, 73, 255, 63, 244};
	}

	@Override
	public void update() {

	}

	@Override
	public void render(ShapeRenderer shapeRenderer) {
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(rArray[colorCode]/255f, gArray[colorCode]/255f, bArray[colorCode]/255f, 0);
		shapeRenderer.rect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
		shapeRenderer.end();
	}


}
