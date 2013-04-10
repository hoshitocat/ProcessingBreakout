package processing.collision;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.shape.Bar;
import processing.shape.Block;
import processing.shape.Circle;
import processing.shape.OperateShape;

public class CollisionDecider {
	private ArrayList<OperateShape> shapes;
	private ArrayList<CollisionListener> listeners;
	private boolean isCollision = false;
	private PApplet papplet;
	
	public CollisionDecider(PApplet p) {
		papplet = p;
		this.shapes = new ArrayList<OperateShape>();
		this.listeners = new ArrayList<CollisionListener>();
	}
	
	public void add(OperateShape shape) {
		this.shapes.add(shape);
	}
	
	public void addListener(CollisionListener listener) {
		this.listeners.add(listener);
	}
	
	public void checkCollision() {
		for (OperateShape shape : shapes) {
			if (shape instanceof Circle) {
				for (OperateShape checkShape : shapes) {
					if (checkShape instanceof Circle) {
					} else if (checkShape instanceof Block) {
						hitBlock(shape, checkShape);
					} else if (checkShape instanceof Bar) {
						hitBar(shape, checkShape);
					}

					if (isCollision) {
						CollisionEvent event = new CollisionEvent();
						event.setTarget(shape);

						notifyEvent(event);
					}
				}
			}
		}
	}
	
	public void hitBlock(OperateShape ball, OperateShape block) {
		boolean isRightVertexBlockRange =
				ball.getRightVertex() >= block.getX() - block.getWidthFromCenter()
				&& ball.getRightVertex() <= block.getX() + block.getWidthFromCenter()
				&& ball.getY() >= block.getY() - block.getHeightFromCenter()
				&& ball.getY() <= block.getY() + block.getHeightFromCenter();
		boolean isLeftVertexBlockRange = 
				ball.getLeftVertex() >= block.getX() - block.getWidthFromCenter()
				&& ball.getLeftVertex() <= block.getX() + block.getWidthFromCenter()
				&& ball.getY() >= block.getY() - block.getHeightFromCenter()
				&& ball.getY() <= block.getY() + block.getHeightFromCenter();
		boolean isTopVertexBlockRange =
				ball.getTopVertex() >= block.getY() - block.getHeightFromCenter()
				&& ball.getTopVertex() <= block.getY() + block.getHeightFromCenter()
				&& ball.getX() >= block.getX() - block.getWidthFromCenter()
				&& ball.getX() <= block.getX() + block.getWidthFromCenter();
		boolean isBottomVertexBlockRange =
				ball.getBottomVertex() >= block.getY() - block.getHeightFromCenter()
				&& ball.getBottomVertex() <= block.getY() + block.getHeightFromCenter()
				&& ball.getX() >= block.getX() - block.getWidthFromCenter()
				&& ball.getX() <= block.getX() + block.getWidthFromCenter();
		if (isRightVertexBlockRange) {
			((Block) block).deleteBlock();
			ball.setAngle(180 - (ball.getAngle()));
		} else if (isLeftVertexBlockRange) {
			((Block) block).deleteBlock();
			ball.setAngle(180 - (ball.getAngle()));
		} else if (isTopVertexBlockRange) {
			((Block) block).deleteBlock();
			ball.setAngle( - (ball.getAngle()));
		} else if (isBottomVertexBlockRange) {
			((Block) block).deleteBlock();
			ball.setAngle( - (ball.getAngle()));
		}
	}
	
	public void hitBar(OperateShape ball, OperateShape bar) {
		boolean isBallXBarRange = ball.getX() >= bar.getX() - bar.getWidthFromCenter() && ball.getX() <= bar.getX() + bar.getWidthFromCenter();
		boolean isBallYBarRange = ball.getY() >= bar.getY() - bar.getHeightFromCenter() && ball.getY() <= bar.getY() + bar.getHeightFromCenter(); 
		boolean isBallBarRangeLeft = ball.getX() < (((bar.getWidthFromCenter() * 2) / 3) + (bar.getX() - bar.getWidthFromCenter()));
		boolean isBallBarRangeCenter = ball.getX() >= (((bar.getWidthFromCenter() * 2) / 3) + (bar.getX() - bar.getWidthFromCenter()));
		boolean isBallBarRangeRight = ball.getX() >= ((((bar.getWidthFromCenter() * 2) / 3) * 2) + (bar.getX() - bar.getWidthFromCenter()));
		if (isBallXBarRange && isBallYBarRange) {
			if (isBallBarRangeLeft) {
				ball.setY(670);
				ball.setAngle(30 - (ball.getAngle()));
				ball.setSpeed(8);
			} else if (isBallBarRangeCenter && !isBallBarRangeRight) {
				ball.setY(670);
				ball.setAngle( - (ball.getAngle()));
				ball.setSpeed(7);
				isCollision = true;
			} else if (isBallBarRangeRight) {
				ball.setY(670);
				ball.setAngle(- 30 - (ball.getAngle()));
				ball.setSpeed(8);
				isCollision = true;
			}
		} else if (ball.getY() > papplet.width) {
			ball.setX(bar.getX());
			ball.setY(bar.getY() - ball.getHeightFromCenter());
			ball.setSpeed(0);
		}
	}
	
	private void notifyEvent(CollisionEvent event) {
		for (CollisionListener listener : listeners) {
			listener.onCollision(event);
		}
	}
}