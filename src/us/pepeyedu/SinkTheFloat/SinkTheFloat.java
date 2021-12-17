package us.pepeyedu.SinkTheFloat;

import us.pepeyedu.SinkTheFloat.Game.Game;
import us.pepeyedu.SinkTheFloat.Textures.TextureManager;
import us.pepeyedu.SinkTheFloat.Utils.FontManager;

public class SinkTheFloat {
	public static void main(String[] args) {
		new SinkTheFloat();
	}
	private static SinkTheFloat instance;
	private FontManager fontManager;
	private TextureManager textureManager;
	private Game game;
	public SinkTheFloat() {
		instance = this;
		fontManager = new FontManager();
		textureManager = new TextureManager();
		game = new Game();
	}
	public static SinkTheFloat getInstance() {
		return instance;
	}
	public FontManager getFontManager() {
		return fontManager;
	}
	public TextureManager getTextureManager() {
		return textureManager;
	}
	public Game getGame() {
		return game;
	}
}
