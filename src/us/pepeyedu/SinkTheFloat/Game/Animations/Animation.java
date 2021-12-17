package us.pepeyedu.SinkTheFloat.Game.Animations;

import java.awt.Graphics;

import us.pepeyedu.SinkTheFloat.Textures.Texture;
import us.pepeyedu.SinkTheFloat.Utils.GameLocation;
import us.pepeyedu.SinkTheFloat.Utils.ObjectDimension;

public abstract class Animation {
	private Texture[] textures;
	private long durationPerFotogram = 0;
	private GameLocation loc;
	private ObjectDimension dim;
	private int pos = 0;
	private boolean bucle = false;
	private boolean finish = false;
	private long timer = 0;
	public Animation(Texture[] textures, long durationPerFotogram, GameLocation loc, ObjectDimension dim) {
		this.textures = textures;
		this.durationPerFotogram = durationPerFotogram;
		this.loc = loc;
		this.dim = dim;
		this.timer = durationPerFotogram + System.currentTimeMillis();
	}
	public void render(Graphics g) {
		if (!finish) {
			g.drawImage(textures[pos].getTexture(), loc.getX(), loc.getY(), dim.getX(), dim.getY(), null, null);
			if (timer - System.currentTimeMillis() <= 0) {
				timer = durationPerFotogram + System.currentTimeMillis();
				pos++;
				if (pos >= textures.length) {
					if (bucle) {
						pos = 0;
					} else {
						finish = true;
						onFinish();
					}
				}
			}
		}
	}
	public void setBucle(boolean bucle) {
		this.bucle = bucle;
	}
	public abstract void onFinish();
}
