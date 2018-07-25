package com.casualgames.upperbound.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class BottomTile extends Tiles {

	public BottomTile() {
//		this.width = Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/5;
//		this.height = Gdx.graphics.getHeight()/10;
//		this.x = Gdx.graphics.getWidth()*3/20;
//		this.y = Gdx.graphics.getHeight()/10;

		rectangle.setWidth(Gdx.graphics.getWidth());
		rectangle.setHeight(Gdx.graphics.getHeight()/40);
		rectangle.setX(0);
		rectangle.setY(Gdx.graphics.getHeight()-rectangle.getHeight());

		this.oneThrid = rectangle.getWidth()/3;

	}

	@Override
	public void update() {

	}

	@Override
	public void render(ShapeRenderer shapeRenderer) {
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(238/255f, 66/255f, 104/255f, 0);
		shapeRenderer.rect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
		shapeRenderer.end();
	}


}
