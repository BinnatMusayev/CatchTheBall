package com.casualgames.upperbound.objects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;


public class Circle {

	private Vector2 postion;
	private float radius;
	private float speed;
	private float angle;

	//tail stuff
	private float h1, h2;
	private float triangleAngle , triangleAngle2;
	private float tailLength;
	private float currentTailLength;
	private float minTailLength;
	private float changeTail;
	private boolean increasingTail;
	private boolean rotateUpLeft, rotateUpRight, rotateDownLeft, rotateDownRight;


	private boolean movingRight, movingLeft, movingUp, movingDown;

	private Random random;
	private int colorCode;
	private int[] rArray, gArray, bArray;

	public Circle(){
		radius = Gdx.graphics.getWidth()/36;
		postion = new Vector2(Gdx.graphics.getWidth()/2-radius, Gdx.graphics.getHeight()*6/7);

		//evvel 70-e bolurdum
		speed = Gdx.graphics.getWidth()/80;
//		speed = 0;
		angle = 45;

		this.movingRight = true;
		this.movingLeft = false;
		this.movingUp = true;
		this.movingDown = false;

		//tail stuff
		triangleAngle = angle;
//		triangleAngle = 30;
//		tailLength = Gdx.graphics.getWidth()/15	;
		setTailLength(Gdx.graphics.getWidth()/15);
		changeTail = Gdx.graphics.getWidth()/500f;
//		currentTailLength = tailLength;
//		minTailLength = tailLength*3/4;
//		changeTail = Gdx.graphics.getWidth()/500f;
//		increasingTail = true;
		rotateUpLeft = false;
		rotateUpRight = false;
		rotateDownLeft = false;
		rotateDownRight = false;


		//triangle colors --  outer |  inner
		//93, 198, 204  |||  44, 44, 44
		//249, 236, 94  |||  242, 162, 2
		//93, 198, 204  |||  29, 108, 147 -- cox da yaxshi deil
		//172, 232, 160 |||  49, 181, 23
		//255, 107, 107 |||  255, 0, 0
		//181, 132, 255 |||  126, 40, 255
		rArray = new int[]{93, 249, 93, 172, 255, 181, 44, 242, 29, 49, 255, 126};
		gArray = new int[]{198, 236, 198, 232, 107, 132, 44, 162, 108, 181, 0, 40};
		bArray = new int[]{204, 94, 204, 160, 107, 255, 44, 2, 147, 23, 0, 255};

		random = new Random();
		colorCode = random.nextInt(6);

	}

	public void update(float dt){
		move();
//		tailChange();
//		setupTriangleAngle();
	}

	public void render(ShapeRenderer shapeRenderer){
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.identity();




		//tail stuff
//		shapeRenderer.set(ShapeRenderer.ShapeType.Line);
		shapeRenderer.setColor(rArray[colorCode]/255f, gArray[colorCode]/255f, bArray[colorCode]/255f, 0);
		shapeRenderer.triangle(
								postion.x + radius * MathUtils.cos((triangleAngle2)* MathUtils.degreesToRadians),
								postion.y +  radius *MathUtils.sin((triangleAngle2)*MathUtils.degreesToRadians),
								postion.x  + radius*MathUtils.cos((180-triangleAngle2)* MathUtils.degreesToRadians),
								postion.y  -  radius *MathUtils.sin((180-triangleAngle2)*MathUtils.degreesToRadians),
								postion.x + tailLength*MathUtils.cos(triangleAngle* MathUtils.degreesToRadians) ,
								postion.y+tailLength*MathUtils.sin(triangleAngle*MathUtils.degreesToRadians) 	);


		//inner triangle
		// +6 renglerin sayina gore deyishir
		shapeRenderer.setColor(rArray[colorCode + 6]/255f, gArray[colorCode+6]/255f, bArray[colorCode+6]/255f, 0);
		shapeRenderer.triangle(
				postion.x + radius*3/4 * MathUtils.cos((triangleAngle2)* MathUtils.degreesToRadians),
				postion.y +  radius*3/4 *MathUtils.sin((triangleAngle2)*MathUtils.degreesToRadians),
				postion.x  + radius*3/4*MathUtils.cos((180-triangleAngle2)* MathUtils.degreesToRadians),
				postion.y  -  radius*3/4 *MathUtils.sin((180-triangleAngle2)*MathUtils.degreesToRadians),
				postion.x + tailLength*3/4*MathUtils.cos(triangleAngle* MathUtils.degreesToRadians) ,
				postion.y+tailLength*3/4*MathUtils.sin(triangleAngle*MathUtils.degreesToRadians) 	);


		shapeRenderer.setColor(Color.WHITE);
		shapeRenderer.circle(postion.x, postion.y, radius);

//		shapeRenderer.setColor(Color.RED);
//		shapeRenderer.line(postion.x+ radius * MathUtils.cos((triangleAngle2)* MathUtils.degreesToRadians),
//				              postion.y +radius *MathUtils.sin((triangleAngle2)*MathUtils.degreesToRadians),
//							postion.x + radius * MathUtils.cos((180-triangleAngle2)* MathUtils.degreesToRadians),
//					postion.y - radius *MathUtils.sin((180-triangleAngle2)*MathUtils.degreesToRadians));
//		shapeRenderer.line(postion.x+ radius * MathUtils.cos((30)* MathUtils.degreesToRadians),
//				postion.y +radius *MathUtils.sin((30)*MathUtils.degreesToRadians),
//				postion.x + radius * MathUtils.cos((180-30)* MathUtils.degreesToRadians),
//				postion.y - radius *MathUtils.sin((180-30)*MathUtils.degreesToRadians));

		shapeRenderer.end();

		tailChange();

	}

	public void dispose(){

	}

	public void tailChange(){

		if (increasingTail){
			tailLength += changeTail;
			if(tailLength >= currentTailLength)
				increasingTail = false;
		}
		else{
			tailLength -= changeTail;
			if (tailLength <= minTailLength)
				increasingTail=true;
		}

	}

	public void setTailLength(float newTailLength){
		this.tailLength = newTailLength;
		setCurrentTailLength(newTailLength);
		setMinTailLength(newTailLength*2/3);
//		this.increasingTail = false;
	}

	public void rotateTail(){
		if(rotateUpLeft){
			triangleAngle-=1;
			if(triangleAngle <= angle)
				rotateUpLeft = false;
		}

		if(rotateUpRight){
			triangleAngle-=1;
			if(triangleAngle <= angle)
				rotateUpRight = false;
		}

		if(rotateDownLeft){
			triangleAngle-=1;
			if(triangleAngle <= angle)
				rotateDownLeft = false;
		}

		if(rotateDownRight){
			triangleAngle-=1;
			if(triangleAngle <= angle)
				rotateDownRight = false;
		}
	}

	public void move(){
		if (movingRight){
			postion.x += speed*MathUtils.cos(angle* MathUtils.degreesToRadians);
		}

		if (movingLeft){
			postion.x += -speed*MathUtils.cos(angle* MathUtils.degreesToRadians);
		}

		if (movingUp) {
			postion.y += -speed * MathUtils.sin(angle * MathUtils.degreesToRadians);

			if (movingRight) {
				triangleAngle = 180-angle;
//				triangleAngle2 = angle;
				triangleAngle2 = 90-angle;
//				triangleAngle2 = 30;
			}
			if (movingLeft) {
				triangleAngle = angle;
				triangleAngle2 = angle+90;
//				triangleAngle2 = 180-30;
			}
		}

		if (movingDown) {
			postion.y += speed * MathUtils.sin(angle * MathUtils.degreesToRadians);
			if (movingRight) {
				triangleAngle = angle+180;
				triangleAngle2 = angle+90;
//				triangleAngle2 = 180-30;
			}
			if (movingLeft){
				triangleAngle = -angle;
//				triangleAngle2 = angle;
				triangleAngle2 = 90-angle;
//				triangleAngle2 = 30;
			}
		}

	}

	//bu method hele ishlenmir
	public void setupTriangleAngle(){
		if(movingUp && movingRight){
			triangleAngle = angle +90;
		}
		if(movingUp && movingLeft){
			triangleAngle = angle;
		}
		if(movingDown && movingRight){
			triangleAngle = angle+180;
		}

		if(movingDown && movingLeft){
			triangleAngle = angle - 90;
		}
	}

	//get Rectangle Bounds
	public Rectangle getBounds(){
		return new Rectangle(postion.x-radius, postion.y-radius, 2*radius, 2*radius);
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}

	public void setMovingLeft(boolean movingLeft) {
		this.movingLeft = movingLeft;
	}

	public void setMovingUp(boolean movingUp) {
		this.movingUp = movingUp;
	}

	public void setMovingDown(boolean movingDown) {
		this.movingDown = movingDown;
	}

	public float getSpeed() {
		return speed;
	}

	public Vector2 getPostion() {
		return postion;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getRadius() {
		return radius;
	}

	public boolean isMovingRight() {
		return movingRight;
	}

	public boolean isMovingLeft() {
		return movingLeft;
	}

	public boolean isMovingUp() {
		return movingUp;
	}

	public boolean isMovingDown() {
		return movingDown;
	}

	public float getTriangleAngle() {
		return triangleAngle;
	}

	public void setCurrentTailLength(float currentTailLength){
		this.currentTailLength = currentTailLength;
	}

	public void setMinTailLength(float minTailLength){
		this.minTailLength = minTailLength;
	}

	public float getTailLength() {
		return tailLength;
	}

	public float getCurrentTailLength() {
		return currentTailLength;
	}

	public float getMinTailLength() {
		return minTailLength;
	}
}
