package us.pepeyedu.SinkTheFloat.Game.Screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import us.pepeyedu.SinkTheFloat.SinkTheFloat;
import us.pepeyedu.SinkTheFloat.Game.Game;
import us.pepeyedu.SinkTheFloat.Textures.TexturePath;
import us.pepeyedu.SinkTheFloat.Utils.GameAudio;
import us.pepeyedu.SinkTheFloat.Utils.PlayerType;
import us.pepeyedu.SinkTheFloat.Utils.Utils;
import us.pepeyedu.SinkTheFloat.Windows.Windows;

public class TransactionScreen extends Screen {
	private int message = -1;
	private int addedPos = 700;
	private boolean sleep = false;;
	private PlayerType randomEnemySelect = PlayerType.YOU;
	private double randomEnemySelectSpeed = 20;
	private double randomEnemySelectTime = 20;
	private long enemySelectedTime = 0;
	private long sleepTime = 0;
	public TransactionScreen(Windows windows, Game game) {
		super(windows, game);
	}
	@Override
	public void onOpen() {
		if (getGame().getGameData().getTurn() == null) {
			getGame().getGameData().setTurn(Utils.random.nextBoolean() ? PlayerType.YOU : PlayerType.ENEMY);
		} else {
			getGame().getGameData().setTurn(getGame().getGameData().getTurn() == PlayerType.YOU ? PlayerType.ENEMY : PlayerType.YOU);
		}
		addedPos = 700;
		sleep = false;
		randomEnemySelectSpeed = 20;
		randomEnemySelectTime = 20;
		enemySelectedTime = 0;
		randomEnemySelect = PlayerType.YOU;
		sleepTime = 0;
	}
	@Override
	public void internalTick() {
		//System.out.println(sleep);
		if (!sleep) {
			if (addedPos > -700) {
				addedPos-=20;
			} else {
				if (getGame().getGameData().getTurn().equals(PlayerType.ENEMY)) {
					getGame().setScreen("you");
				} else {
					getGame().setScreen("attack");
				}
			}
		} else {
			if (enemySelectedTime == 0 && message == -1) {
				randomEnemySelectTime -= randomEnemySelectSpeed;
				if (randomEnemySelectTime <= 0) {
					randomEnemySelectTime = 20;
					randomEnemySelectSpeed -= 0.5;
					if (randomEnemySelectSpeed <= 0) {
						randomEnemySelect = getGame().getGameData().getTurn();
					} else {
						randomEnemySelect = (randomEnemySelect == PlayerType.YOU ? PlayerType.ENEMY : PlayerType.YOU);
					}
					new GameAudio("tick.wav") {
						@Override
						public void onFinish() {}							
					};
				}
				if (randomEnemySelectSpeed <= 0) {
					enemySelectedTime = System.currentTimeMillis();
				}
			} else {
				if (message == -1) {
					if ((enemySelectedTime + 5000) - System.currentTimeMillis() <= 0) {
						sleep = false;
					}
				} else if ((sleepTime + 2500) - System.currentTimeMillis() >= 0) {
					sleep = false;
				}
			}
		}
		if (!sleep && (enemySelectedTime == 0 ? addedPos < 0 : addedPos > 0) && message == -1) {
			sleep = true;
			addedPos = 0;
			sleepTime = System.currentTimeMillis();
			
		}
	}
	@Override
	protected void paintLevel(Graphics g) {
		g.drawImage(SinkTheFloat.getInstance().getTextureManager().getTexture(TexturePath.FONDO).getTexture(), 0, 0, 1200, 800, null, null);
		renderMessage(g);
	}
	public void setMessage(int message) {
		this.message = message;
	}
	public void renderMessage(Graphics g) {
		if (message == -1) { // Se elige el primero aleatoriamente
			Rectangle rec = new Rectangle(((getGame().getWindows().getX()/2)-125) + addedPos, (getGame().getWindows().getY()/2)-100, 250, 50);
			Rectangle rec2 = new Rectangle(((getGame().getWindows().getX()/2)-125) + addedPos, (getGame().getWindows().getY())-225, 250, 50);			
			g.setColor(new Color(255, 0, 0, 225));
			Utils.drawCenteredString(g, "Eligiendo Atacante", rec, SinkTheFloat.getInstance().getFontManager().getFont("Airborne").deriveFont(Font.PLAIN, 110));
			Utils.drawCenteredString(g, randomEnemySelect.getName(), rec2, SinkTheFloat.getInstance().getFontManager().getFont("Airborne").deriveFont(Font.PLAIN, 70));
		} else if (message == 1) { // tu turno
			Rectangle rec = new Rectangle(((getGame().getWindows().getX()/2)-125) + addedPos, (getGame().getWindows().getY()/2)-50, 250, 50);
			g.setColor(new Color(255, 0, 0, 255));
			Utils.drawCenteredString(g, "Tu Turno", rec, SinkTheFloat.getInstance().getFontManager().getFont("Airborne").deriveFont(Font.PLAIN, 120));
		} else if (message == 2) { // turno del rival
			Rectangle rec = new Rectangle(((getGame().getWindows().getX()/2)-125) + addedPos, (getGame().getWindows().getY()/2)-50, 250, 50);
			g.setColor(new Color(255, 0, 0, 255));
			Utils.drawCenteredString(g, "Turno Rival", rec, SinkTheFloat.getInstance().getFontManager().getFont("Airborne").deriveFont(Font.PLAIN, 120));
			
		}
	}
}
