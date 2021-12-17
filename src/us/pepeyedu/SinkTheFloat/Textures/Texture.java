package us.pepeyedu.SinkTheFloat.Textures;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import javax.imageio.ImageIO;

import us.pepeyedu.SinkTheFloat.Utils.Callback;
import us.pepeyedu.SinkTheFloat.Utils.Utils;

public class Texture {
	private String name;
	private String path;
	private BufferedImage texture;
	private HashMap<String, TextureChanged> changed = new HashMap<String, TextureChanged>();
	public Texture(String name, String path) {
		this.name = name;
		this.path = path;
	}
	public String getName() {
		return name;
	}
	public String getPath() {
		return path;
	}
	public boolean isLoaded() {
		return texture != null;
	}
	public BufferedImage getTexture() {
		return texture;
	}
	public void load(Callback<Boolean> callback) {
		try {
			texture = ImageIO.read(getClass().getClassLoader().getResourceAsStream(path));
			for (TextureChanged tc : changed.values()) {
				BufferedImage img = Utils.copyTexture(texture);
				tc.load(img);
			}
			callback.done(true, null);
		} catch (IOException | IllegalArgumentException e) {
			callback.done(false, e);
		}
	}
	public void unload() {
		texture = null;
		for (TextureChanged tc : changed.values()) {
			tc.unload();
		}
	}
	public void createTextureChanged(String name, TextureChanged textureChanged) {
		changed.put(name, textureChanged);
	}
	public boolean hasChangedTexture(String name) {
		return changed.containsKey(name);
	}
	public Set<String> getTextureChangeds() {
		return changed.keySet();
	}
	public TextureChanged getTextureChanged(String name) {
		return changed.get(name);
	}
}
