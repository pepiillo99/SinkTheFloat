package us.pepeyedu.SinkTheFloat.Textures;

import java.awt.Color;
import java.awt.image.BufferedImage;

public abstract class TextureChanged {
	private BufferedImage texture;
	public BufferedImage getTexture() {
		return texture;
	}
	public void load(BufferedImage texture) {
		this.texture = texture;
		change();
	}
	public boolean isLoaded() {
		return texture != null;
	}
	public void unload() {
		texture = null;
	}
	public void changeColor(Color mainColor, Color replaceColor) {
	    int RGB_MASK = 0x00ffffff;
	    int ALPHA_MASK = 0xff000000;
		if (texture != null) {
		    int oldRGB = mainColor.getRed() << 16 | mainColor.getGreen() << 8 | mainColor.getBlue();
		    int toggleRGB = oldRGB ^ (replaceColor.getRed() << 16 | replaceColor.getGreen() << 8 | replaceColor.getBlue());
		    int w = texture.getWidth();
		    int h = texture.getHeight();

		    int[] rgb = texture.getRGB(0, 0, w, h, null, 0, w);
		    for (int i = 0; i < rgb.length; i++) {
		        if ((rgb[i] & RGB_MASK) == oldRGB) {
		            rgb[i] ^= toggleRGB;
		        }
		    }
		    texture.setRGB(0, 0, w, h, rgb, 0, w);
		}
	}
	public abstract void change();
}
