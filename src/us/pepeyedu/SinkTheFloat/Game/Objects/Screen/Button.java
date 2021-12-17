package us.pepeyedu.SinkTheFloat.Game.Objects.Screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import us.pepeyedu.SinkTheFloat.SinkTheFloat;
import us.pepeyedu.SinkTheFloat.Game.Game;
import us.pepeyedu.SinkTheFloat.Utils.GameLocation;
import us.pepeyedu.SinkTheFloat.Utils.GameObject;
import us.pepeyedu.SinkTheFloat.Utils.ObjectDimension;
import us.pepeyedu.SinkTheFloat.Utils.Utils;

public abstract class Button extends GameObject {
	private String text = "";
	private boolean over = false;
	private boolean show = true;
	public Button(String text, GameLocation gameLocation, Game game, ObjectDimension dimension) {
		super(gameLocation, game, dimension);
		this.text = text;
	}
	public String getText() {
		return text;
	}
	public boolean isShow() {
		return show;
	}
	public void setShow(boolean show) {
		this.show = show;
	}
	@Override
	public void tick() {
		boolean newover = getGame().getScreen().isTouch(getGame().getScreen().getMouseLocation(), this);
		if (!over && newover) {
			onOver();
		}
		over = newover;
	}
	@Override
	public void render(Graphics g) {
		if (show) {
			g.setColor(new Color(125, 125, 125, over ? 255 : 150));
			g.fillRect(x - (over ? 5 : 0), y - (over ? 5 : 0), getDimension().getX() + (over ? 10 : 0), getDimension().getY() + (over ? 10 : 0));
			g.setColor(new Color(255, 0, 0, over ? 255 : 150));
			Utils.drawCenteredString(g, text, new Rectangle(x, y, getDimension().getX(), getDimension().getY()), SinkTheFloat.getInstance().getFontManager().getFont("Airborne").deriveFont(Font.PLAIN, over ? 50 : 45));
		}
	}
	public boolean isOver() {
		return over;
	}
	public abstract void onClick();
	public abstract void onOver();
}
