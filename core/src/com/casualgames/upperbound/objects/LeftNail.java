package com.casualgames.upperbound.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;


public class LeftNail {

	private Vector2 postion;
	private float width, height;
	private Texture leftNailTexture;

	public LeftNail(float y){
		postion = new Vector2(0, y);
		this.width = Gdx.graphics.getWidth()/10;
		this.height = this.width*24/44;

		leftNailTexture = new Texture(Gdx.files.internal("leftNail.png"));
	}

	public void update(float dt){

	}

	public void render(SpriteBatch sb){
//		sb.begin();
		sb.draw(leftNailTexture, postion.x, postion.y, this.width, this.height);
//		sb.end();
	}

	public  void dispose(){
		leftNailTexture.dispose();
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
