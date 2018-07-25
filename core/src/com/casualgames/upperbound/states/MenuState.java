package com.casualgames.upperbound.states;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MenuState extends State implements InputProcessor {

	private Texture playBtn, title, musicIcon, soundIcon, musicOn, musicOff;
	private float playWidth, titleWidth, titleHeight;
	private float playbtnX, playbtnY, titleX, titleY;
	private BitmapFont bitmapFont;
	private float fontX, fontY;

	private float iconWidth, iconHeight;
	private float musicX, musicY, soundX, soundY;

	Preferences preferences;

	public MenuState(GameStateManager gsm){
		super(gsm);

		playBtn = new Texture(Gdx.files.internal("playBtn.png"));
		title = new Texture(Gdx.files.internal("title.png"));
		playWidth = Gdx.graphics.getWidth()/3;
		playbtnX = Gdx.graphics.getWidth()/2 - playWidth/2;
		playbtnY = Gdx.graphics.getHeight()/2 - playWidth/2;

		titleWidth = Gdx.graphics.getWidth()*4/5;
		titleHeight = Gdx.graphics.getHeight()/10;
		titleX = Gdx.graphics.getWidth()/2 - titleWidth/2;
		titleY = Gdx.graphics.getHeight()*4/5;

		bitmapFont = new BitmapFont(Gdx.files.internal("myfont.fnt"));
		bitmapFont.getData().setScale(1.5f);
		fontX = Gdx.graphics.getWidth()/3;
		fontY = Gdx.graphics.getHeight()/3;

		Gdx.input.setInputProcessor(this);

		preferences = Gdx.app.getPreferences("gameprefs");

		musicOn = new Texture(Gdx.files.internal("musicOn.png"));
		musicOff = new Texture(Gdx.files.internal("musicOff.png"));
		if (preferences.getBoolean("music") ){
			musicIcon = musicOn;
		}else{
			musicIcon = musicOff;
		}

		if (preferences.getBoolean("sound") ){
			soundIcon = new Texture(Gdx.files.internal("soundOn.png"));
		}else{
			soundIcon = new Texture(Gdx.files.internal("soundOff.png"));
		}

		iconWidth = Gdx.graphics.getWidth()/10;
		iconHeight = iconWidth;

		musicX = Gdx.graphics.getWidth()/3;
		musicY = Gdx.graphics.getHeight()/7;
		soundX = musicX + iconWidth*2;
		soundY = musicY;


	}

	@Override
	public void hangleInput() {

	}

	@Override
	public void update(float dt) {
		hangleInput();
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.begin();
		sb.draw(playBtn, playbtnX, playbtnY, playWidth, playWidth );
		sb.draw(title, titleX, titleY, titleWidth, titleHeight);
//		sb.draw(musicIcon, musicX, musicY, iconWidth, iconHeight);
//		sb.draw(soundIcon, soundX, soundY, iconWidth, iconHeight);
		bitmapFont.draw(sb, "Best: "+preferences.getInteger("bestScore"), fontX, fontY );

		sb.end();
	}

	@Override
	public void render(ShapeRenderer shapeRenderer) {
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(93/255f, 198/255f, 204/255f, 0);
		shapeRenderer.rect(Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()*2/5, Gdx.graphics.getWidth()/20, Gdx.graphics.getHeight()/5);
		shapeRenderer.rect(Gdx.graphics.getWidth()*17/20, Gdx.graphics.getHeight()*2/5, Gdx.graphics.getWidth()/20, Gdx.graphics.getHeight()/5);
		shapeRenderer.end();
	}

	@Override
	public void dispose() {
		playBtn.dispose();
		title.dispose();
		bitmapFont.dispose();
		musicIcon.dispose();
		soundIcon.dispose();
	}


	//----------------------------INPUT PROCESSOR
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
		if (screenX >= playbtnX && screenX <= playbtnX + playWidth
				&& screenY >= playbtnY && screenY <= playbtnY + playWidth) {

			gsm.set(new PlayState(gsm));
		}

		if (screenX >= musicX && screenX <= musicX + iconWidth
				&& screenY >= musicY && screenY <= musicY + iconWidth) {

			if (!preferences.getBoolean("music")) {
				preferences.putBoolean("music", true);
				musicIcon = musicOn;
			}else {
				preferences.putBoolean("music", false);
				musicIcon = musicOff;
			}

			if (!preferences.getBoolean("sound")) {
				preferences.putBoolean("sound", true);
				soundIcon = new Texture(Gdx.files.internal("soundOn.png"));
			}else {
				preferences.putBoolean("sound", false);
				soundIcon = new Texture(Gdx.files.internal("soundOff.png"));
			}

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
