package us.pepeyedu.SinkTheFloat.Game.Objects.Screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import us.pepeyedu.SinkTheFloat.SinkTheFloat;
import us.pepeyedu.SinkTheFloat.Game.Game;
import us.pepeyedu.SinkTheFloat.Utils.GameLocation;
import us.pepeyedu.SinkTheFloat.Utils.ObjectDimension;
import us.pepeyedu.SinkTheFloat.Utils.Utils;

public abstract class TextButton extends Button {
	private String text = "";
	public TextButton(String text, String name, GameLocation gameLocation, Game game, ObjectDimension dimension) {
		super(name, gameLocation, game, dimension);
		this.text = text;
	}
	public String getText() {
		return text;
	}
	@Override
	public void render(Graphics g) {
		if (isShow()) {
			g.setColor(new Color(125, 125, 125, isOver() ? 255 : 150));
			g.fillRect(x - (isOver() ? 5 : 0), y - (isOver() ? 5 : 0), getDimension().getX() + (isOver() ? 10 : 0), getDimension().getY() + (isOver() ? 10 : 0));
			g.setColor(new Color(255, 0, 0, isOver() ? 255 : 150));
			Utils.drawCenteredString(g, text, new Rectangle(x, y, getDimension().getX(), getDimension().getY()), SinkTheFloat.getInstance().getFontManager().getFont("Airborne").deriveFont(Font.PLAIN, isOver() ? 50 : 45));
		}
	}
}
