package com.casualgames.upperbound.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class LeftNails {

	private ArrayList<LeftNail> leftNails;
	private float speed;

	public LeftNails(float y){
		leftNails = new ArrayList<LeftNail>();

		leftNails.add(new LeftNail(y));
		this.populate();
		speed = 2.5f;
	}

	public void update(float dt){
		this.deleteNails();
		this.populate();
		this.move();
	}

	public void render(SpriteBatch sb){
		for(LeftNail leftNail: leftNails){
			leftNail.render(sb);
		}
	}

	public void dispose(){
		for(LeftNail leftNail: leftNails){
			leftNail.dispose();
		}
	}

	public void move(){
		for(LeftNail leftNail: leftNails){
			leftNail.getPostion().set(0, leftNail.getPostion().y+=speed);
		}
	}

	public void populate(){
		if (leftNails.size() < (2*Gdx.graphics.getHeight())/ (Gdx.graphics.getWidth()/10) ){
//		if (leftNails.size() < 20 ){
			leftNails.add(new LeftNail(leftNails.get(leftNails.size()-1).getPostion().y-leftNails.get(0).getHeight() ));
		}
	}

	public void deleteNails(){
//		for (LeftNail leftNail: leftNails){
//			if (leftNail.getPostion().y > Gdx.graphics.getHeight()){
//				leftNails.remove(0);
//			}
//		}
		if (leftNails.get(0).getPostion().y >Gdx.graphics.getHeight()){
			leftNails.remove(0);
		}
	}


	public LeftNail getLeftNail(int index){
		return leftNails.get(index);
	}

}
