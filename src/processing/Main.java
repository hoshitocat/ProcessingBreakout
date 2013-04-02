package processing;

import java.util.ArrayList;

import processing.core.*;

public class Main extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<OperateShape> blocks = new ArrayList<OperateShape>();
	OperateShape bar, ball;
	
	private int xPoint, yPoint;
	
	public void setup() {
		size(1200, 700);
		smooth();
		bar = new Rectangle(this, 100, 10);
		bar.setX(600);
		bar.setY(680);
		bar.setColor(color(100, 100, 100));
		bar.setIsFollowingMouse(true);
		
		ball = new Circle(this, 10);
		ball.setX(600);
		ball.setY(670);
		ball.setColor(color(0, 0, 255));
		ball.setIsFollowingMouse(true);
		
		yPoint = 3;
		for (int i = 1; i <= 108; i++) {
			OperateShape block = new Rectangle(this, 60, 30);
			xPoint++;
			if (xPoint + 60 * xPoint > 1140) {
				xPoint = 1;
				yPoint++;
			}
			block.setX(xPoint + 60 * xPoint);
			block.setY(yPoint + 30 * yPoint);
			blocks.add(block);
		}
	}
	
	public void draw() {
		clearShape();
		
		bar.display();
		
		if (ball.getSpeed() > 0) {
			ball.move();
			ball.hitBar(bar.getX(), bar.getY(), bar.getWidthFromCenter(), bar.getHeightFromCenter());
			for (OperateShape block : blocks) {
				block.display();
				if (ball.hitBlock(block.getX(), block.getY(), block.getWidthFromCenter(), block.getHeightFromCenter())) {
					block.setX(-100);
				}
			}
		} else {
			for (OperateShape block : blocks) {
				block.display();
			}
			ball.display();
		}
	}
	
	public void clearShape() {
		stroke(255, 255, 255);
		fill(255, 255, 255);
		rect(0, 0, width, height);
	}
	
}