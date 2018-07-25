package com.casualgames.upperbound.objects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class RightNail {

	private Vector2 postion;
	private float width, height;
	private Texture rightNailTexture;

	public RightNail(float y){

		this.width = Gdx.graphics.getWidth()/10;
		this.height = this.width*24/44;

		rightNailTexture = new Texture(Gdx.files.internal("rightNail.png"));

		postion = new Vector2(Gdx.graphics.getWidth()*9/10, y);
	}

	public void update(float dt){

	}

	public void render(SpriteBatch sb){
//		sb.begin();
		sb.draw(rightNailTexture, Gdx.graphics.getWidth() - this.width, postion.y, this.width, this.height);
//		sb.end();
	}

	public  void dispose(){
		rightNailTexture.dispose();
	}

	public float getWidth() {
		return width;
	}

	public Vector2 getPostion() {
		return postion;
	}

	public float getHeight() {
		return height;
	}
}
