package com.casualgames.upperbound.states;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.casualgames.upperbound.AdsController;
import com.casualgames.upperbound.UpperBound;

public class GameOverState extends State implements InputProcessor {

	private Texture gameOverTexture, retryBtn;
	private float gameOverX, gameOverY, gameOverWidth, gameOverHeight;
	private float retryX, retryY, retryWidth;
	private BitmapFont bitmapFont;
	private float fontX, fontY;

	int tempScore;

	Preferences preferences;

	// INTERSTITIAL AD
//	private PublisherInterstitialAd mPublisherInterstitialAd;
	private AdsController adsController;
	private Runnable then;

	public GameOverState(GameStateManager gsm, int tempScore, AdsController adsController) {
		super(gsm);

		if (UpperBound.gameoverCount == 1) {
			adsController.showInterstitialAd(then);
			UpperBound.gameoverCount = 0;
		}else{
			UpperBound.gameoverCount++;
		}


		gameOverTexture = new Texture(Gdx.files.internal("gameover.png"));
		retryBtn = new Texture(Gdx.files.internal("retry.png"));

		gameOverWidth = Gdx.graphics.getWidth()*4/5;
		gameOverHeight = Gdx.graphics.getHeight()/10;
		gameOverX = Gdx.graphics.getWidth()/2 - gameOverWidth/2;
		gameOverY = Gdx.graphics.getHeight()*4/5;

		retryWidth = Gdx.graphics.getWidth()/3;
		retryX = Gdx.graphics.getWidth()/2 - retryWidth/2;
		retryY = Gdx.graphics.getHeight()/2 - retryWidth/2;


		bitmapFont = new BitmapFont(Gdx.files.internal("myfont.fnt"));
		bitmapFont.getData().setScale(1.2f);
		fontX = Gdx.graphics.getWidth()/3 + Gdx.graphics.getWidth()/20;
		fontY = Gdx.graphics.getHeight()/3;

		this.tempScore = tempScore;

		cam.setToOrtho(false);
		Gdx.input.setInputProcessor(this);

		preferences = Gdx.app.getPreferences("gameprefs");

		this.adsController = adsController;


	}

	@Override
	protected void hangleInput() {

	}

	@Override
	public void update(float dt) {
		adsController.onAdClosed();
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		sb.begin();

		sb.draw(gameOverTexture, gameOverX, gameOverY, gameOverWidth, gameOverHeight);
		sb.draw(retryBtn, retryX, retryY, retryWidth, retryWidth);

		bitmapFont.draw(sb, "Score: "+ this.tempScore, fontX, fontY );
		bitmapFont.draw(sb, "Best: " + preferences.getInteger("bestScore"), fontX, fontY - Gdx.graphics.getHeight()/20 );

		sb.end();
	}

	@Override
	public void render(ShapeRenderer shapeRenderer) {

	}

	@Override
	public void dispose() {
		bitmapFont.dispose();
		gameOverTexture.dispose();
		retryBtn.dispose();
	}

	//------------- INPUT PROCESSOR


	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (screenX >= retryX && screenX <= retryX + retryWidth
				&& screenY >= retryY && screenY <= retryY + retryWidth) {

			gsm.set(new PlayState(gsm));
		}

		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
