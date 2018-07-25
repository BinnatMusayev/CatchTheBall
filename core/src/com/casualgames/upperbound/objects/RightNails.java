package com.casualgames.upperbound.objects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class RightNails {

	private ArrayList<RightNail> rightNails;
	private float speed;

	public RightNails(float y){
		rightNails = new ArrayList<RightNail>();

		rightNails.add(new RightNail(y));
		this.populate();
		speed = 2.5f;
	}

	public void update(float dt){
		this.deleteNails();
		this.populate();
		this.move();
	}

	public void render(SpriteBatch sb){
		for(RightNail rightNail: rightNails){
			rightNail.render(sb);
		}
	}

	public void dispose(){
		for(RightNail rightNail: rightNails){
			rightNail.dispose();
		}
	}

	public void move(){
		for(RightNail rightNail: rightNails){
			// for moving opposite direction change += to -=
			rightNail.getPostion().set(0, rightNail.getPostion().y+=speed);
		}
	}

	public void populate(){
		/*if (rightNails.size() < (2* Gdx.graphics.getHeight())/ (Gdx.graphics.getWidth()/10) ){
//		if (leftNails.size() < 20 ){
			rightNails.add(new RightNail(rightNails.get(rightNails.size()-1).getPostion().y+rightNails.get(0).getHeight() ));
		}*/
		if (rightNails.size() < (2*Gdx.graphics.getHeight())/ (Gdx.graphics.getWidth()/10) ){
//		if (leftNails.size() < 20 ){
			rightNails.add(new RightNail(rightNails.get(rightNails.size()-1).getPostion().y-rightNails.get(0).getHeight() ));
		}
	}

	public void deleteNails(){
//		for (LeftNail leftNail: leftNails){
//			if (leftNail.getPostion().y > Gdx.graphics.getHeight()){
//				leftNails.remove(0);
//			}
//		}

//		if (rightNails.get(0).getPostion().y < -rightNails.get(1).getHeight() ){
//			rightNails.remove(0);
//		}

		if (rightNails.get(0).getPostion().y >Gdx.graphics.getHeight()){
			rightNails.remove(0);
		}

	}


	public RightNail getRightNail(int index){
		return rightNails.get(index);
	}

}
