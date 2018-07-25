package com.casualgames.upperbound.states;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.casualgames.upperbound.UpperBound;
import com.casualgames.upperbound.objects.Barikada;
import com.casualgames.upperbound.objects.Barikadas;
import com.casualgames.upperbound.objects.BottomTile;
import com.casualgames.upperbound.objects.Circle;
import com.casualgames.upperbound.objects.LeftNail;
import com.casualgames.upperbound.objects.LeftNails;
import com.casualgames.upperbound.objects.LeftTile;
import com.casualgames.upperbound.objects.RightNails;
import com.casualgames.upperbound.objects.RightTile;
import com.casualgames.upperbound.objects.UpTile;

import java.util.Random;


public class PlayState extends State implements InputProcessor {

	private RightTile rightTile;
	private LeftTile leftTile;
	private UpTile upTile;
	private BottomTile bottomTile;
	private Circle circle;
	float deltaLengthRight, deltaLengthLeft;

	private LeftNails leftNails;
	private RightNails rightNails;

	private Barikadas barikadas;

	private BitmapFont bitmapFont;
	private int score;
	private float scoreX, scoreY;

	private boolean gameover;

	Preferences preferences;

	private Sound gameoverSound, tapSound;

	private Rectangle intersection;

	private float collDiff;

	private long gameOverTimeToChange;
	private int colorCode;
	private Random random;



	public PlayState(GameStateManager gsm){
		super(gsm);
//		cam.setToOrtho(false, FlappyDemo.WIDTH/2, FlappyDemo.HEIGHT/2);

		random = new Random();
		colorCode = random.nextInt(5);
		rightTile = new RightTile(colorCode);
		leftTile = new LeftTile(colorCode);
		upTile = new UpTile();
		bottomTile = new BottomTile();
		circle = new Circle();

		deltaLengthRight = 0;
		deltaLengthLeft = 0;

		Gdx.input.setInputProcessor(this);
//		cam.setToOrtho(true, circle.getBounds().getX(), circle.getBounds().getX() );
		cam.position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0);

		leftNails = new LeftNails(2*cam.position.y-Gdx.graphics.getWidth()/20);
		rightNails = new RightNails(2*cam.position.y-Gdx.graphics.getWidth()/20);
//		rightNails = new RightNails(0);

		barikadas = new Barikadas();

		bitmapFont = new BitmapFont(Gdx.files.internal("myfont.fnt"));
		bitmapFont.getData().setScale(2, -2);
		this.score = 0;
		this.scoreX = Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/100;
		this.scoreY = Gdx.graphics.getHeight()/23;

		this.gameover  = false;

		preferences = Gdx.app.getPreferences("gameprefs");

		gameoverSound = Gdx.audio.newSound(Gdx.files.internal("tap.mp3"));
		tapSound = Gdx.audio.newSound(Gdx.files.internal("tap2.wav"));

		intersection = new Rectangle();

		 collDiff = 0;
		gameOverTimeToChange=0;
	}

	@Override
	public void hangleInput() {

	}

	@Override
	public void update(float dt) {
		hangleInput();
		leftNails.update(dt);
		rightNails.update(dt);
		barikadas.update(dt);
		if (!gameover) {
			circle.move();
		}
		collisionDetction();
		if(gameover){
			if ( (TimeUtils.millis()-gameOverTimeToChange)  > 1000 ) {
				gsm.set(new GameOverState(gsm, score, UpperBound.adsController));
			}
		}



		if (circle.getBounds().getY() <= Gdx.graphics.getHeight()/2 ){
//			rightTile.getBounds().setY(rightTile.getBounds().getY()-circle.getSpeed());

//			rightTile.getBounds().setY(rightTile.getBounds().getY() - circle.getSpeed());
//			leftTile.getBounds().setY(leftTile.getBounds().getY()-circle.getSpeed());
//			cam.translate(0, -circle.getSpeed()/2);
		}

		if(rightTile.getBounds().getY() <= cam.position.y || leftTile.getBounds().getY() <= cam.position.y){
//			cam.position.y = rightTile.getBounds().getY();
//			cam.position.y -= circle.getSpeed();
		}

		cam.update();

	}


	@Override
	public void render(ShapeRenderer shapeRenderer){
		shapeRenderer.setProjectionMatrix(cam.combined);
//		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		rightTile.render(shapeRenderer);
		leftTile.render(shapeRenderer);
		barikadas.render(shapeRenderer);
		upTile.render(shapeRenderer);
		bottomTile.render(shapeRenderer);
		circle.render(shapeRenderer);
//		shapeRenderer.end();
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		rightNails.render(sb);
		leftNails.render(sb);

		bitmapFont.draw(sb, String.valueOf(this.score), this.scoreX , this.scoreY);
		//both angles
//		bitmapFont.draw(sb, String.valueOf(this.circle.getAngle()) + ", " + String.valueOf(this.circle.getTriangleAngle()), this.scoreX , this.scoreY);
		//triangleangle
//		bitmapFont.draw(sb,  String.valueOf(this.circle.getTriangleAngle()), this.scoreX , this.scoreY);
//		bitmapFont.draw(sb, String.valueOf(this.circle.getTailLength()) + ", " + String.valueOf(this.circle.getCurrentTailLength()), this.scoreX , this.scoreY);
//		bitmapFont.draw(sb, String.valueOf(TimeUtils.millis()-gameOverTimeToChange), this.scoreX , this.scoreY);
		sb.end();




	}

	@Override
	public void dispose() {
		bitmapFont.dispose();
		gameoverSound.dispose();
		tapSound.dispose();
	}


	public void collisionDetction(){
		if (!gameover) {
			if (this.circle.getBounds().overlaps(rightTile.getBounds())) {



				collDiff = circle.getBounds().getY() - rightTile.getY();
				if (circle.isMovingUp()){

//					circle.rotateTailtoBottomRight(50 );
					circle.setAngle( 60 -(30*collDiff/rightTile.getHeight() )  );
				}else if (circle.isMovingDown()){
					circle.setAngle(  ((30*collDiff/rightTile.getHeight() +30) ));
				}

				circle.setMovingRight(false);
				circle.setMovingLeft(true);
				score = score + 1;
				tapSound.play(0.2f);
				speedUp();
//				if(score >= 10 && score < 20){
//					circle.setSpeed(Gdx.graphics.getWidth()/65);
//					circle.setTailLength(Gdx.graphics.getWidth()/14);
//				}else if(score >= 20 && score < 30){
//					circle.setSpeed(Gdx.graphics.getWidth()/60);
//					circle.setTailLength(Gdx.graphics.getWidth()/13);
//				}else if(score >= 30 && score < 40){
//					circle.setSpeed(Gdx.graphics.getWidth()/55);
//					circle.setTailLength(Gdx.graphics.getWidth()/12);
//				}else if(score >= 40 && score < 50){
//					circle.setSpeed(Gdx.graphics.getWidth()/55);
//					circle.setTailLength(Gdx.graphics.getWidth()/11.5f);
//				}else if(score >= 50 && score < 60){
//					circle.setSpeed(Gdx.graphics.getWidth()/50);
//					circle.setTailLength(Gdx.graphics.getWidth()/11);
//				}else if(score >= 60 && score < 70){
//					circle.setSpeed(Gdx.graphics.getWidth()/45);
//					circle.setTailLength(Gdx.graphics.getWidth()/10.5f);
//				}

			}

			if (this.circle.getBounds().overlaps(leftTile.getBounds())) {

				collDiff = circle.getBounds().getY() - leftTile.getY();
				if (circle.isMovingUp()){
					circle.setAngle( 60 - (30*collDiff/leftTile.getHeight() )  );
				}else if (circle.isMovingDown()){
					circle.setAngle( ((30*collDiff/leftTile.getHeight() +30 ) ));
				}

				circle.setMovingLeft(false);
				circle.setMovingRight(true);
				score = score + 1;
				tapSound.play(0.2f);
				speedUp();
//				if(score >= 10 && score < 20){
//					circle.setSpeed(Gdx.graphics.getWidth()/65);
//					circle.setTailLength(Gdx.graphics.getWidth()/14);
//				}else if(score >= 20 && score < 30){
//					circle.setSpeed(Gdx.graphics.getWidth()/60);
//					circle.setTailLength(Gdx.graphics.getWidth()/13);
//				}else if(score >= 30 && score < 40){
//					circle.setSpeed(Gdx.graphics.getWidth()/55);
//					circle.setTailLength(Gdx.graphics.getWidth()/12);
//				}else if(score >= 40 && score < 50){
//					circle.setSpeed(Gdx.graphics.getWidth()/55);
//					circle.setTailLength(Gdx.graphics.getWidth()/11.5f);
//				}else if(score >= 50 && score < 60){
//					circle.setSpeed(Gdx.graphics.getWidth()/50);
//					circle.setTailLength(Gdx.graphics.getWidth()/11);
//				}else if(score >= 60 && score < 70){
//					circle.setSpeed(Gdx.graphics.getWidth()/45);
//					circle.setTailLength(Gdx.graphics.getWidth()/10.5f);
//				}

			}

			if (this.circle.getBounds().overlaps(upTile.getBounds())) {
				circle.setMovingUp(false);
				circle.setMovingDown(true);
//				score = score + 1;
				tapSound.play(0.2f);
			}

			if (this.circle.getBounds().overlaps(bottomTile.getBounds())) {
				circle.setMovingDown(false);
				circle.setMovingUp(true);
//				score = score + 1;
				tapSound.play(0.2f);
			}

			for (Barikada barikada: barikadas.getBarikadas() ) {
				if (circle.getBounds().overlaps(barikada.getBounds()) ){
//					if (circle.getPostion().y +circle.getRadius() > barikada.getBounds().y && circle.getPostion().y-circle.getRadius() < barikada.getBounds().y + barikada.getBounds().height) {
					if ( (circle.getPostion().y +circle.getRadius() > barikada.getBounds().y
							&& circle.getPostion().y   < barikada.getBounds().y + circle.getRadius()/10 )
							||
							( circle.getPostion().y-circle.getRadius() < barikada.getBounds().y + barikada.getBounds().height
							&& circle.getPostion().y  > barikada.getBounds().y + barikada.getBounds().height - circle.getRadius()/10 )

							) {

						if (circle.isMovingUp() && ( circle.getPostion().y-circle.getRadius() < barikada.getBounds().y + barikada.getBounds().height
								&& circle.getPostion().y  > barikada.getBounds().y + barikada.getBounds().height - circle.getRadius()/10 )){
							circle.setMovingUp(false);
							circle.setMovingDown(true);
							tapSound.play(0.2f);
						}else if(circle.isMovingDown() && (circle.getPostion().y +circle.getRadius() > barikada.getBounds().y
								&& circle.getPostion().y   < barikada.getBounds().y + circle.getRadius()/10 ) ){
							circle.setMovingDown(false);
							circle.setMovingUp(true);
							tapSound.play(0.2f);
						}
					}else{
						if (circle.isMovingRight()) {
							circle.setMovingRight(false);
							circle.setMovingLeft(true);
							tapSound.play(0.2f);
						} else if (circle.isMovingLeft()) {
							circle.setMovingLeft(false);
							circle.setMovingRight(true);
							tapSound.play(0.2f);
						}
					}


				}


			}

		}

		if ( circle.getPostion().x + circle.getRadius() >rightTile.getBounds().getX() + rightTile.getBounds().getWidth()
				|| circle.getPostion().x < leftTile.getBounds().getX() + leftTile.getBounds().getWidth() ){
//			circle.setSpeed(0);

			if (!gameover) {
				gameoverSound.play();
				gameOverTimeToChange = TimeUtils.millis();
			}
			gameover = true;

//			if (TimeUtils.millis() - gameOverTimeToChange < 2000 ) {
//				gameover = true;
//			}

			if(score > preferences.getInteger("bestScore") ){
				preferences.putInteger("bestScore", score);
				preferences.flush();
			}

//			Timer.schedule(new Timer.Task(){
//				@Override
//				public void run() {
//					if (gameover)
//					gsm.set(new GameOverState(gsm, score, UpperBound.adsController));
//				}
//			}, 0.1f);


//			gsm.set(new GameOverState(gsm, this.score));

		}
	}

	public void speedUp(){

		if(score >= 10 && score < 20){
			circle.setSpeed(Gdx.graphics.getWidth()/75);
			circle.setTailLength(Gdx.graphics.getWidth()/14);
		}else if(score >= 20 && score < 30){
			circle.setSpeed(Gdx.graphics.getWidth()/70);
			circle.setTailLength(Gdx.graphics.getWidth()/13);
		}else if(score >= 30 && score < 40){
			circle.setSpeed(Gdx.graphics.getWidth()/65);
			circle.setTailLength(Gdx.graphics.getWidth()/12);
		}else if(score >= 40 && score < 50){
			circle.setSpeed(Gdx.graphics.getWidth()/60);
			circle.setTailLength(Gdx.graphics.getWidth()/11.5f);
		}else if(score >= 50 && score < 60){
			circle.setSpeed(Gdx.graphics.getWidth()/55);
			circle.setTailLength(Gdx.graphics.getWidth()/11);
		}else if(score >= 60 && score < 70){
			circle.setSpeed(Gdx.graphics.getWidth()/50);
			circle.setTailLength(Gdx.graphics.getWidth()/10.5f);
		}else if(score >= 70 && score < 80){
			circle.setSpeed(Gdx.graphics.getWidth()/48);
			circle.setTailLength(Gdx.graphics.getWidth()/10.5f);
		}else if(score >= 100 && score < 110){
			circle.setSpeed(Gdx.graphics.getWidth()/45);
			circle.setTailLength(Gdx.graphics.getWidth()/10.5f);
		}else if(score >= 110 && score < 120){
			circle.setSpeed(Gdx.graphics.getWidth()/43);
			circle.setTailLength(Gdx.graphics.getWidth()/10.3f);
		}



		//evvelki
//		if(score >= 10 && score < 20){
//			circle.setSpeed(Gdx.graphics.getWidth()/65);
//			circle.setTailLength(Gdx.graphics.getWidth()/14);
//		}else if(score >= 20 && score < 30){
//			circle.setSpeed(Gdx.graphics.getWidth()/60);
//			circle.setTailLength(Gdx.graphics.getWidth()/13);
//		}else if(score >= 30 && score < 40){
//			circle.setSpeed(Gdx.graphics.getWidth()/55);
//			circle.setTailLength(Gdx.graphics.getWidth()/12);
//		}else if(score >= 40 && score < 50){
//			circle.setSpeed(Gdx.graphics.getWidth()/55);
//			circle.setTailLength(Gdx.graphics.getWidth()/11.5f);
//		}else if(score >= 50 && score < 60){
//			circle.setSpeed(Gdx.graphics.getWidth()/50);
//			circle.setTailLength(Gdx.graphics.getWidth()/11);
//		}else if(score >= 60 && score < 70){
//			circle.setSpeed(Gdx.graphics.getWidth()/48);
//			circle.setTailLength(Gdx.graphics.getWidth()/10.5f);
//		}else if(score >= 70 && score < 80){
//			circle.setSpeed(Gdx.graphics.getWidth()/45);
//			circle.setTailLength(Gdx.graphics.getWidth()/10.5f);
//		}else if(score >= 100 && score < 110){
//			circle.setSpeed(Gdx.graphics.getWidth()/43);
//			circle.setTailLength(Gdx.graphics.getWidth()/10.5f);
//		}
	}

	// TOUCH EVENTS

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		if (pointer<3){
			if(screenX >= rightTile.getBounds().getX() && screenX <= rightTile.getBounds().getX() + rightTile.getBounds().getWidth()
//					&& screenY >= rightTile.getBounds().getY() && screenY <= rightTile.getBounds().getY() + rightTile.getBounds().getHeight() ){
					){
				deltaLengthRight = cam.position.y - rightTile.getBounds().getY();
				System.out.println("Scrren Y: " + screenY + ", Camera Y: " + cam.position.y);

			}

			if(screenX >= leftTile.getBounds().getX() && screenX <= leftTile.getBounds().getX() + leftTile.getBounds().getWidth()
//					&& screenY >= leftTile.getBounds().getY() && screenY <= leftTile.getBounds().getY() + leftTile.getBounds().getHeight() ){
					){

				if(screenY >= leftTile.getBounds().getY()) {
					deltaLengthLeft = screenY - leftTile.getBounds().getY();
				}else{
					deltaLengthLeft =  leftTile.getBounds().getY()- screenY;
				}
			}
		}


		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		deltaLengthRight = 0;
		deltaLengthLeft = 0;
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (pointer<3 && !gameover){
			if(screenX >= rightTile.getBounds().getX() - 4*rightTile.getBounds().getWidth() && screenX <= rightTile.getBounds().getX() + 3*rightTile.getBounds().getWidth()
					&& screenY >= (rightTile.getBounds().getY()-rightTile.getBounds().getHeight())
					&& screenY <= rightTile.getBounds().getY() + 2*rightTile.getBounds().getHeight() ){
//					){
//				rightTile.getBounds().setY( screenY-deltaLengthRight);
				rightTile.getBounds().setY( cam.position.y - Gdx.graphics.getHeight()*5/6 + screenY + rightTile.getHeight());

//				rightTile.getCoordinates().set(rightTile.getCoordinates().x, rightTile.getCoordinates().y - screenY);
			}


			if(screenX >= leftTile.getBounds().getX() - 2*leftTile.getBounds().getWidth() && screenX <= leftTile.getBounds().getX() + 5*leftTile.getBounds().getWidth()
					&& screenY >= (leftTile.getBounds().getY()-leftTile.getBounds().getHeight())
					&& screenY <= leftTile.getBounds().getY() + leftTile.getBounds().getHeight() ){
//					){
//				leftTile.getBounds().setY(screenY-deltaLengthLeft);
				leftTile.getBounds().setY(cam.position.y  - Gdx.graphics.getHeight()*5/6 + screenY + leftTile.getHeight());
			}
		}
		return true;
	}




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
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
