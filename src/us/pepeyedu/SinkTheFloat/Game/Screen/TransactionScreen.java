package us.pepeyedu.SinkTheFloat.Game.Screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import us.pepeyedu.SinkTheFloat.SinkTheFloat;
import us.pepeyedu.SinkTheFloat.Game.Game;
import us.pepeyedu.SinkTheFloat.Game.Objects.Screen.Button;
import us.pepeyedu.SinkTheFloat.Game.Objects.Screen.ImageButton;
import us.pepeyedu.SinkTheFloat.Textures.TexturePath;
import us.pepeyedu.SinkTheFloat.Utils.GameAudio;
import us.pepeyedu.SinkTheFloat.Utils.GameLocation;
import us.pepeyedu.SinkTheFloat.Utils.GameObject;
import us.pepeyedu.SinkTheFloat.Utils.ObjectDimension;
import us.pepeyedu.SinkTheFloat.Utils.PlayerType;
import us.pepeyedu.SinkTheFloat.Utils.Utils;
import us.pepeyedu.SinkTheFloat.Windows.KeyInput;
import us.pepeyedu.SinkTheFloat.Windows.MouseInput;
import us.pepeyedu.SinkTheFloat.Windows.Windows;
import us.pepeyedu.SinkTheFloat.Windows.Buttons.MouseButtons;

public class TransactionScreen extends Screen {
	private int message = -1;
	private int addedPos = 700;
	private boolean sleep = false;;
	private PlayerType randomEnemySelect = PlayerType.YOU;
	private double randomEnemySelectSpeed = 20;
	private double randomEnemySelectTime = 20;
	private long enemySelectedTime = 0;
	private long sleepTime = 0;
	private long lastRoating = 0;
	public TransactionScreen(Windows windows, Game game) {
		super(windows, game);
		setKeyInput(new KeyInput() {
			@Override
			public void tick() {}
			@Override
			public void onKeyPressed(int key) {}
			@Override
			public void onKeyReleased(int key) {}			
		});
		setMouseInput(new MouseInput() {
			@Override
			public void tick() {}
			@Override
			public void onClick(MouseButtons mouseButton) {}
			@Override
			public void onWheelMoved(MouseButtons mouseButton) {
				if (System.currentTimeMillis() - lastRoating >= 150) {
					GameLocation mouseLocation = new GameLocation(getMouseInput().getX(), getMouseInput().getY());
					// g.drawRect(900, 500, 230, 230);
					if (mouseLocation.getX() >= 900 && mouseLocation.getX() <= 1130 && mouseLocation.getY() >= 500 && mouseLocation.getY() <= 730) {
						if (mouseButton.equals(MouseButtons.WHEEL_UP)) {
							System.out.println(getGame().getAllEmotes().size() / 4);
							getGame().setEmoteLineSelected(((getGame().getEmoteLineSelected() + 1) * 4 >= getGame().getAllEmotes().size() ? getGame().getEmoteLineSelected() : getGame().getEmoteLineSelected() + 1));
						} else if (mouseButton.equals(MouseButtons.WHEEL_DOWN)) {
							getGame().setEmoteLineSelected((getGame().getEmoteLineSelected() - 1 <= 0 ? 0 : getGame().getEmoteLineSelected() - 1));
						}
						updateEmotePage();
					}
					lastRoating = System.currentTimeMillis();
				}
			}
			@Override
			public void onButtonPressed(MouseButtons mouseButton) {}
			@Override
			public void onButtonReleased(MouseButtons mouseButton) {
				for (GameObject object : getGameObjects()) {
					if (object instanceof Button) {
						Button button = (Button) object;
						if (button.isShow() && button.isOver()) {
							button.onClick();
						}
					}
				}	
			}			
		});
		addGameObject(new ImageButton("...", "emotes", TexturePath.BOCADILLO, new GameLocation(1000, 650), getGame(), new ObjectDimension(50, 50)) {
			@Override
			public void onClick() {
				new Thread() {
					@Override
					public void run() {
						try {
							sleep(50);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						getGame().setShowEmotePage(true);
						showEmotes(true);
					}
				}.start();
			}
			@Override
			public void onOver() {}			
		});
		int size = 0;
		for (int ii = 0; ii < 4; ii++) {
			for (int i = 0; i < 4; i++) {
				size++;
				int selected = ii + (i * 4) + 1 + (getGame().getEmoteLineSelected() * 4);
				if (getGame().getAllEmotes().size()-1 >= selected) {
					addGameObject(new ImageButton("emotebutton-" + size, getGame().getAllEmotes().get(selected), new GameLocation(910 + (55 * ii), 510 + (55 * i)), getGame(), new ObjectDimension(50, 50)) {
						@Override
						public void onClick() {
							getGame().getGameData().getMachineLogic().addEmote(getTexturePath(), PlayerType.YOU);
							getGame().setEmoteLineSelected(0);
							getGame().setShowEmotePage(false);
							showEmotes(false);
						}
						@Override
						public void onOver() {}
					});
				}
			}
		}
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
		showEmotes(getGame().isShowEmotePage());
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
		for (PlayerType pType : PlayerType.values()) {
			if (getGame().getGameData().getMachineLogic().hasEmote(pType)) {
				getGame().getGameData().getMachineLogic().getEmote(pType).tick();
			}
		}
	}
	@Override
	protected void paintLevel(Graphics g) {
		g.drawImage(SinkTheFloat.getInstance().getTextureManager().getTexture(TexturePath.FONDO).getTexture(), 0, 0, 1200, 800, null, null);
		renderMessage(g);
		for (PlayerType pType : PlayerType.values()) {
			if (getGame().getGameData().getMachineLogic().hasEmote(pType)) {
				getGame().getGameData().getMachineLogic().getEmote(pType).render(g);
			}
		}
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
	public Button getButton(String name) {
		for (GameObject button : getGameObjects()) {
			if (button instanceof Button) {
				Button b = (Button) button;
				if (b.getName().equals(name)) {
					return b;
				}
			}
		}
		return null;
	}
	public void showEmotes(boolean show) {
		for (GameObject button : getGameObjects()) {
			if (button instanceof Button) {
				Button b = (Button) button;
				if (b.getName().equals("emotes")) {
					b.setShow(!show);
				} else if (b.getName().contains("emotebutton-")) {
					b.setShow(show);
				}
			}
		}
		updateEmotePage();
	}
	public void updateEmotePage() {
		int size = 0;
		for (int ii = 0; ii < 4; ii++) {
			for (int i = 0; i < 4; i++) {
				size++;
				int selected = ii + (i * 4) + 1 + (getGame().getEmoteLineSelected() * 4);
				ImageButton button = (ImageButton) getButton("emotebutton-" + size);
				if (button != null) {
					if (getGame().getAllEmotes().size()-1 >= selected) {
						button.setShow(true && getGame().isShowEmotePage());
						button.setTexture(getGame().getAllEmotes().get(selected));
					} else {
						button.setShow(false);
					}
				} else {

				}
			}
		}
	}
}
