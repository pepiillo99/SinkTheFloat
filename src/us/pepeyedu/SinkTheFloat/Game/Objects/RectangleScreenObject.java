package us.pepeyedu.SinkTheFloat.Game.Objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import us.pepeyedu.SinkTheFloat.SinkTheFloat;
import us.pepeyedu.SinkTheFloat.Game.Game;
import us.pepeyedu.SinkTheFloat.Game.Animations.Animation;
import us.pepeyedu.SinkTheFloat.Textures.Texture;
import us.pepeyedu.SinkTheFloat.Utils.GameLocation;
import us.pepeyedu.SinkTheFloat.Utils.GameObject;
import us.pepeyedu.SinkTheFloat.Utils.ObjectDimension;
import us.pepeyedu.SinkTheFloat.Utils.Utils;

public abstract class RectangleScreenObject extends GameObject {
	private int matrizX = 0;
	private int matrizY = 0;
	private boolean over = false;
	private Texture waterTexture;
	private boolean attacked = false;
	private int alpha = 100;
	private Animation animation;
	public RectangleScreenObject(int matrizX, int matrizY, GameLocation gameLocation, Game game, ObjectDimension dimension) {
		super(gameLocation, game, dimension);
		this.matrizX = matrizX;
		this.matrizY = matrizY;
		waterTexture = SinkTheFloat.getInstance().getTextureManager().getTexture("Agua");
	}
	@Override
	public void tick() {
		if (getGame().getScreen().getMouseInput() != null) {
			if (getGame().getScreen().isTouch(getGame().getScreen().getMouseLocation(), this)) {
				over = true;
			} else {
				over = false;
			}
		} else {
			over = false;
		}
		if (alpha > 100) {
			alpha -= 2;
		}
	}
	@Override
	public void render(Graphics g) {
		if (getGame().getScreen().getMouseInput() != null) {
			if (getGame().getScreen().isTouch(getGame().getScreen().getMouseLocation(), this)) {
				g.setColor(Color.CYAN);
				g.fillRect(getX(), getY(), getDimension().getX(), getDimension().getY());
			} else {
				if (waterTexture != null && waterTexture.isLoaded()) {
					g.drawImage(waterTexture.getTexture(), getX(), getY(), getDimension().getX(), getDimension().getY(), null, null);
				} else {
					if (Utils.random.nextBoolean()) {
						g.setColor(Color.white);
					} else {
						g.setColor(Color.pink);
					}
					g.fillRect(getX(), getY(), getDimension().getX(), getDimension().getY());
				}
			}
		}
		if (attacked) {
			g.setColor(new Color(255, 255, 255, alpha));
			g.setFont(new Font("Aria", Font.PLAIN, 60));
			g.drawString("X", getX() + 4, getY() + 45);
		}
	}
	public abstract int getData();
	public int getMatrizX() {
		return matrizX;
	}
	public int getMatrizY() {
		return matrizY;
	}
	public boolean isOver() {
		return over;
	}
	public boolean isAttacked() {
		return attacked;
	}
	public void setAttacked(boolean attacked) {
		this.attacked = attacked;
		this.alpha = 255;
	}
	public Animation getAnimation() {
		return animation;
	}
	public void setAnimation(Animation animation) {
		this.animation = animation;
	}
}
