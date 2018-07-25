package com.casualgames.upperbound.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Random;

public class Barikadas {

	private ArrayList<Barikada> barikadas;
	private float initialX, initialY, initialWidth, initialHeight;
	private float speed;
	private Random random;

	public Barikadas(){

		random = new Random();

		// X between Gdx.graphics.getWidth()/4 AND Gdx.graphics.getWidth()*2/3
		initialX = random.nextFloat()*(Gdx.graphics.getWidth()*2/3-Gdx.graphics.getWidth()/4) + Gdx.graphics.getWidth()/4;
		initialY = 30;
		// Width between Gdx.graphics.getWidth()/100 and Gdx.graphics.getWidth()/20
//		initialWidth = random.nextFloat()*(Gdx.graphics.getWidth()/20 -Gdx.graphics.getWidth()/100) + Gdx.graphics.getWidth()/100;
		initialWidth = Gdx.graphics.getWidth()/100;
		//initialHeight between Gdx.graphics.getHeight()/16 and  Gdx.graphics.getHeight()/6
		initialHeight = random.nextFloat()*(Gdx.graphics.getHeight()/6 - Gdx.graphics.getHeight()/16) + Gdx.graphics.getHeight()/16;

		barikadas = new ArrayList<Barikada>();
		barikadas.add(new Barikada(initialX, initialY, initialWidth, initialHeight));
		this.speed = 2.5f;
		this.populate();
	}

	public void update(float dt){
		this.deleteBarikadas();
		this.populate();
		this.move();
	}

	public void render(ShapeRenderer shapeRenderer){
		for(Barikada barikada: barikadas){
			barikada.render(shapeRenderer);
		}
	}


	public void move(){
		for(Barikada barikada: barikadas){
			float barikadaPosY = barikada.getY();
			barikada.setY( barikadaPosY+=speed);
		}
	}

	public void populate(){
		if (barikadas.size() < 15 ){
//			initialHeight = random.nextFloat()*Gdx.graphics.getHeight();
			initialX = random.nextFloat()*(Gdx.graphics.getWidth()*3/4-Gdx.graphics.getWidth()/4) + Gdx.graphics.getWidth()/4;
//			initialWidth = random.nextFloat()*(Gdx.graphics.getWidth()/20 -Gdx.graphics.getWidth()/100) + Gdx.graphics.getWidth()/100;
			initialHeight = random.nextFloat()*(Gdx.graphics.getHeight()/6	 - Gdx.graphics.getHeight()/10) + Gdx.graphics.getHeight()/10;
//			float barikadaGap = random.nextFloat()*Gdx.graphics.getHeight()/5 + Gdx.graphics.getHeight()/5;
			float barikadaGap = random.nextFloat()*(Gdx.graphics.getHeight()/7)-Gdx.graphics.getHeight()/20;
			Barikada newBarikada =new Barikada(initialX, barikadas.get(barikadas.size()-1).getY()-initialHeight- barikadaGap, initialWidth, initialHeight );
			if(barikadas.get(barikadas.size()-1).getColor()!=newBarikada.getColor()
				&& ( initialX < (barikadas.get(barikadas.size()-1).getX() - Gdx.graphics.getWidth()/5)
				|| initialX > (barikadas.get(barikadas.size()-1).getX() + Gdx.graphics.getWidth()/5) ) )
				barikadas.add(newBarikada);
		}
	}

	public void deleteBarikadas(){

		if (barikadas.get(0).getY() > Gdx.graphics.getHeight() ){
			barikadas.remove(0);
		}
	}

	public ArrayList<Barikada> getBarikadas(){
		return this.barikadas;
	}


}
