package com.casualgames.upperbound.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Stack;

public class GameStateManager {

	private Stack<State> states;

	public GameStateManager(){
		states = new Stack<State>();
	}

	public void push(State state){
		states.push(state);
	}

	public void pop(){
		states.pop().dispose();
	}

	public void set(State state){
		states.pop().dispose();
		states.push(state);
	}

	public State getState(){
		return states.get(0);
	}

	public void update(float dt){
		states.peek().update(dt);
	}

	public void render(SpriteBatch sb){
		states.peek().render(sb);
	}

	public void dispose(){
		states.peek().dispose();
	}

	public void render(ShapeRenderer shapeRenderer){
		states.peek().render(shapeRenderer);
	}

}
