package us.pepeyedu.SinkTheFloat.Game.Emote;

import java.awt.Graphics;

import us.pepeyedu.SinkTheFloat.SinkTheFloat;
import us.pepeyedu.SinkTheFloat.Textures.TexturePath;
import us.pepeyedu.SinkTheFloat.Utils.PlayerType;

public class Emote {
	private TexturePath texture;
	private int x, y;
	private PlayerType pType;
	private int etape = 0;
	private long toSleep = 0;
	public Emote(TexturePath texture, PlayerType pType) {
		this.texture = texture;
		this.pType = pType;
		x = 1200;
		y = pType.equals(PlayerType.ENEMY) ? 60 : 500;
	}
	public void tick() {
		if (etape == 0) {
			if (x <= 900) {
				x = 900;
				if (toSleep != -1 && SinkTheFloat.getInstance().getGame().getGameData().getMachineLogic().countPendenting(pType) == 0) {
					toSleep = System.currentTimeMillis() + 2500;
				}
				etape++;
			} else {
				x -= 7;
			}
		} else if (etape == 1) {
			if (toSleep - System.currentTimeMillis() <= 0) {
				etape++;
			}
		} else if (etape == 2) {
			if (x >= 1200) {
				SinkTheFloat.getInstance().getGame().getGameData().getMachineLogic().finishedEmote(pType);
			} else {
				x += 7;
			}
		}
	}
	public void render(Graphics g) {
		g.drawImage(SinkTheFloat.getInstance().getTextureManager().getTexture(TexturePath.BOCADILLO).getTexture(), x, y, 250, 250, null, null);
		g.drawImage(SinkTheFloat.getInstance().getTextureManager().getTexture(texture).getTexture(), x + 50, y + 10, 150, 150, null, null);
	}
	public void runToFinish() {
		toSleep = -1;
	}
}
