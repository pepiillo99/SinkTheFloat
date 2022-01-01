package us.pepeyedu.SinkTheFloat.Game.Objects.Screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import us.pepeyedu.SinkTheFloat.SinkTheFloat;
import us.pepeyedu.SinkTheFloat.Game.Game;
import us.pepeyedu.SinkTheFloat.Textures.Texture;
import us.pepeyedu.SinkTheFloat.Textures.TexturePath;
import us.pepeyedu.SinkTheFloat.Utils.GameLocation;
import us.pepeyedu.SinkTheFloat.Utils.ObjectDimension;
import us.pepeyedu.SinkTheFloat.Utils.Utils;

public abstract class ImageButton extends Button {
	private TexturePath texturePath;
	private String text;
	public ImageButton(String text, String name, TexturePath texturePath, GameLocation gameLocation, Game game, ObjectDimension dimension) {
		super(name, gameLocation, game, dimension);
		this.texturePath = texturePath;
		this.text = text;
	}
	public ImageButton(String name, TexturePath texturePath, GameLocation gameLocation, Game game, ObjectDimension dimension) {
		super(name, gameLocation, game, dimension);
		this.texturePath = texturePath;
	}
	public TexturePath getTexturePath() {
		return texturePath;
	}
	public void setTexture(TexturePath texturePath) {
		this.texturePath = texturePath;
	}
	public String getText() {
		return text;
	}
	@Override
	public void render(Graphics g) {
		if (isShow()) {
			Texture texture = SinkTheFloat.getInstance().getTextureManager().getTexture(texturePath);
			BufferedImage bud = texture.getTexture();
			if (!isOver() && texture.hasChangedTexture("unnOver")) {
				bud = texture.getTextureChanged("unnOver").getTexture();
			}
			g.drawImage(bud, getX() + (!isOver() ? 3 : -3), getY() + (!isOver() ? 3 : -3), getDimension().getX() + (isOver() ? 6 : -6), getDimension().getY() + (isOver() ? 6 : -6), null, null);
			g.setColor(new Color(0, 0, 0, isOver() ? 255 : 200));
			if (text != null) {
				Utils.drawCenteredString(g, text, new Rectangle(x - (!isOver() ? 0 : 1), y - (!isOver() ? 17 : 19), getDimension().getX(), getDimension().getY()), SinkTheFloat.getInstance().getFontManager().getFont("Airborne").deriveFont(Font.PLAIN, isOver() ? 50 : 45));
			}
		}
	}
}
