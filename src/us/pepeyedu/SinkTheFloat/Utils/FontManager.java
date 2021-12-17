package us.pepeyedu.SinkTheFloat.Utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class FontManager {
	public HashMap<String, Font> fonts = new HashMap<String, Font>();
	public Font getFont(String font) {
		if (fonts.containsKey(font)) {
			return fonts.get(font);
		} else {
			return null;
		}
	}
	public void registerFont(String fontName, String path) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(path);
        Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 60);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		fonts.put(fontName, font);
	}
}
