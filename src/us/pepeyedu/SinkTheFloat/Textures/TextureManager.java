package us.pepeyedu.SinkTheFloat.Textures;

import java.util.HashMap;

public class TextureManager {
	private HashMap<String, Texture> textures = new HashMap<String, Texture>();
	public TextureManager() {
		// aqui que voy a hacer ;(
	}
	public boolean hasTexture(String textureName) {
		return textures.containsKey(textureName);
	}
	public Texture getTexture(String textureName) {
		if (hasTexture(textureName)) {
			return textures.get(textureName);
		} else {
			return null;
		}
	}
	public Texture getTexture(TexturePath texturePath) {
		if (hasTexture(texturePath.getName())) {
			return textures.get(texturePath.getName());
		} else {
			return null;
		}
	}
	public void registerTexture(Texture texture) {
		if (textures.containsKey(texture.getName())) {
			System.out.println("La textura " + texture.getName() + " ya está registrada");
		} else {
			textures.put(texture.getName(), texture);
		}
	}
}
