package us.pepeyedu.SinkTheFloat.Utils;

import java.awt.Graphics;
import java.awt.Rectangle;

import us.pepeyedu.SinkTheFloat.Game.Game;

public abstract class GameObject {
	private Game game;
	protected int x;
	protected int y;
	private int id;
	private int velX;
	private int velY;
	private boolean windowsPassable = true;
	private ObjectDimension dimension;
	private Rectangle hitBox;
	private boolean move = true;
	public GameObject(GameLocation gameLocation, Game game, ObjectDimension dimension) {
		this.game = game;
		this.x = gameLocation.getX();
		this.y = gameLocation.getY();
		this.id = game.getNextObjectID();
		this.dimension = dimension;
		hitBox = new Rectangle(x, y, dimension.getX(), dimension.getY());
	}
	protected Game getGame() {
		return game;
	}
	public int getID() {
		return id;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getVelX() {
		return velX;
	}
	public int getVelY() {
		return velY;
	}
	public void setVelX(int velX) {
		this.velX = velX;
	}
	public void setVelY(int velY) {
		this.velY = velY;
	}
	protected void setMove(boolean move) {
		this.move = move;
	}
	protected boolean isMove() {
		return move;
	}
	public ObjectDimension getDimension() {
		return dimension;
	}
	public Rectangle getHitBox() {
		return hitBox;
	}
	public boolean isCollision(GameObject object) {
		return object.getHitBox().intersects(hitBox);
	}
	public void internalTick() {
		if (move) {
			x += velX;
			y += velY;
			if (!windowsPassable) {
				x = clamp(x, 0, game.getWindows().getXToPaint() - dimension.getX());
				y = clamp(y, 0, game.getWindows().getYToPaint() - dimension.getY());
			}
			hitBox.setBounds(x, y, dimension.getX(), dimension.getY());
		}
		tick();
	}
	protected void setWindowsPassable(boolean windowsPassable) {
		this.windowsPassable = windowsPassable;
	}
	public abstract void tick();
	public abstract void render(Graphics g);
	private int clamp(int var, int min, int max) {
		if (var >= max) {
			return var = max;
		} else if (var <= min) {
			return var = min;
		} else {
			return var;
		}
	}
}
