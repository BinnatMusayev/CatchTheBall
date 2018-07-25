package com.casualgames.upperbound;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;
import com.casualgames.upperbound.states.GameOverState;
import com.casualgames.upperbound.states.GameStateManager;
import com.casualgames.upperbound.states.MenuState;
import com.casualgames.upperbound.states.PlayState;

import java.util.concurrent.TimeUnit;

public class UpperBound extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	Texture splash;
	private GameStateManager gsm;

	private Music bgMusic;
	public static AdsController adsController;
	public static int gameoverCount = 0;

	private long startTime;

	public UpperBound(AdsController adsController){
		this.adsController = adsController;
	}

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();

		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("musicBg.mp3"));
		bgMusic.setLooping(true);
		bgMusic.setVolume(0.7f);
		bgMusic.play();

//		Gdx.gl.glClearColor(44/255f, 44/255f, 44/255f, 0);
		gsm = new GameStateManager();
		gsm.push(new MenuState(gsm));

		splash = new Texture("splash.png");
		startTime = TimeUtils.millis();

	}

	@Override
	public void render () {

		if (TimeUtils.millis() - startTime < 2000 ){
//			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
			Gdx.gl.glClearColor(255/255f, 217/255f,84/255f,0);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.begin();
			batch.draw(splash, 0, Gdx.graphics.getHeight()/2-Gdx.graphics.getWidth()/2, Gdx.graphics.getWidth(), Gdx.graphics.getWidth());
			batch.end();

		}else {


			gsm.update(Gdx.graphics.getDeltaTime());
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			//for anti alias
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
			Gdx.gl.glClearColor(44/255f, 44/255f, 44/255f, 0);

//		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//		shapeRenderer.rect(50, 50, 50, 200);
			gsm.render(shapeRenderer);
//		shapeRenderer.end();

//		batch.begin();
			gsm.render(batch);
//		batch.end();

		}

	}
	
	@Override
	public void dispose () {
//		batch.dispose();
		super.dispose();
		bgMusic.dispose();
		gsm.dispose();
	}
}
