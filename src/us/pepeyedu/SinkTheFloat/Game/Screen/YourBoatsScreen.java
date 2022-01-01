package us.pepeyedu.SinkTheFloat.Game.Screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import us.pepeyedu.SinkTheFloat.SinkTheFloat;
import us.pepeyedu.SinkTheFloat.Game.Game;
import us.pepeyedu.SinkTheFloat.Game.Objects.RectangleScreenObject;
import us.pepeyedu.SinkTheFloat.Game.Objects.Boats.Boat;
import us.pepeyedu.SinkTheFloat.Game.Objects.Boats.Destructor;
import us.pepeyedu.SinkTheFloat.Game.Objects.Boats.Fragata;
import us.pepeyedu.SinkTheFloat.Game.Objects.Boats.Portaaviones;
import us.pepeyedu.SinkTheFloat.Game.Objects.Boats.Submarino;
import us.pepeyedu.SinkTheFloat.Game.Objects.Screen.Button;
import us.pepeyedu.SinkTheFloat.Game.Objects.Screen.ImageButton;
import us.pepeyedu.SinkTheFloat.Game.Objects.Screen.TextButton;
import us.pepeyedu.SinkTheFloat.Textures.TexturePath;
import us.pepeyedu.SinkTheFloat.Utils.GameLocation;
import us.pepeyedu.SinkTheFloat.Utils.GameObject;
import us.pepeyedu.SinkTheFloat.Utils.ObjectDimension;
import us.pepeyedu.SinkTheFloat.Utils.PlayerType;
import us.pepeyedu.SinkTheFloat.Windows.KeyInput;
import us.pepeyedu.SinkTheFloat.Windows.MouseInput;
import us.pepeyedu.SinkTheFloat.Windows.Windows;
import us.pepeyedu.SinkTheFloat.Windows.Buttons.MouseButtons;

public class YourBoatsScreen extends Screen {
	private Boat selectedBoat;
	private long lastRoating = 0;
	private int alertMessage = 0;
	private long hideAlertMessage = 0;
	private GameLocation clickDifference;
	private boolean boatPosizioned = false;
	private long transactionTime = 0;
	private int timeToAttack = 60;
	private boolean attacked = false;
	public YourBoatsScreen(Windows windows, Game game) {
		super(windows, game);
		setMouseInput(new MouseInput() {
			@Override
			public void tick() {
				if (selectedBoat != null) {
					RectangleScreenObject rObject = null;
					for (GameObject object : getGameObjects()) {
						if (object instanceof RectangleScreenObject) {
							RectangleScreenObject rectangleObject = (RectangleScreenObject) object;
							if (rectangleObject.isOver()) {
								rObject = rectangleObject;
								break;
							}
						}
					}
					if (rObject != null) {
						selectedBoat.setX(rObject.getX());
						selectedBoat.setY(rObject.getY());
						List<GameLocation> boatLocations = selectedBoat.getBoatPositions(rObject.getMatrizX(), rObject.getMatrizY());
						alertMessage = 0;
						hideAlertMessage = 0;
						for (GameLocation boatLoc : boatLocations) {
							if (isOnTable(boatLoc.getX(), boatLoc.getY())) {
								if (getMatriz()[boatLoc.getX()][boatLoc.getY()] != 0) {
									alertMessage = 2;
									selectedBoat.changeBoatColor("error");
								} else {
									for (int i = 0; i < selectedBoat.getDistance(); i++) {
										List<GameLocation> boatRound = selectedBoat.getRotation().getMatrizRound(rObject.getMatrizX(), rObject.getMatrizY(), i, selectedBoat.getDistance());
										for (GameLocation round : boatRound) {
											if (isOnTable(round.getX(), round.getY())) {
												if (getMatriz()[round.getX()][round.getY()] != 0) {
													alertMessage = 3;
													selectedBoat.changeBoatColor("error");
													break;
												}
											}
										}
									}
								}
							} else {
								alertMessage = 1;
								selectedBoat.changeBoatColor("error");
							}
						}
						if (alertMessage == 0 && selectedBoat.getBoatColor().equals("error")) {
							selectedBoat.changeBoatColor("normal");
						}
					} else {
						if (selectedBoat.getBoatColor().equals("error")) {
							selectedBoat.changeBoatColor("normal");
						}
						alertMessage = 0;
						selectedBoat.setX(getX() - (clickDifference.getX()));
						selectedBoat.setY(getY() - (clickDifference.getY()));
					}
				}
			}
			@Override
			public void onClick(MouseButtons mouseButton) {}
			@Override
			public void onWheelMoved(MouseButtons mouseButton) {
				if (System.currentTimeMillis() - lastRoating >= 150) {
					if (mouseButton.equals(MouseButtons.WHEEL_UP)) {
						if (selectedBoat != null) {
							selectedBoat.rotate(selectedBoat.getRotation().getNext());
						}
					} else if (mouseButton.equals(MouseButtons.WHEEL_DOWN)) {
						if (selectedBoat != null) {
							selectedBoat.rotate(selectedBoat.getRotation().getPrevious());
						}
					}
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
			public void onButtonPressed(MouseButtons mouseButton) {
				if (!boatPosizioned) {
					GameLocation mouseLocation = new GameLocation(getMouseInput().getX(), getMouseInput().getY());
					for (GameObject object : getGameObjects()) {
						if (object instanceof Boat) {
							if (isTouch(mouseLocation, object)) {
								if (mouseButton.equals(MouseButtons.LEFT_CLICK)) {
									selectedBoat = (Boat) object;
									if (selectedBoat.getRestartColorTimer() != 0) {
										selectedBoat.setRestartColorTimer(0);
									}
									if (!selectedBoat.isOnTable()) {
										selectedBoat.setLastLocation(new GameLocation(selectedBoat.getX(), selectedBoat.getY()));
									}
									System.out.println("boat selected");
									if (selectedBoat.isOnTable()) {
										List<GameLocation> boatLocations = selectedBoat.getBoatPositions(selectedBoat.getMatrizX(), selectedBoat.getMatrizY());
										for (GameLocation boatLocation : boatLocations) {
											getMatriz()[boatLocation.getX()][boatLocation.getY()] = 0;
										}
									}
								}
								clickDifference = new GameLocation(getX() - selectedBoat.getX(), getY() - selectedBoat.getY());
							}
						}
					}
				}
			}
			@Override
			public void onButtonReleased(MouseButtons mouseButton) {
				clickDifference = null;
				if (selectedBoat != null) {
					RectangleScreenObject rObject = null;
					for (GameObject object : getGameObjects()) {
						if (object instanceof RectangleScreenObject) {
							RectangleScreenObject rectangleObject = (RectangleScreenObject) object;
							if (rectangleObject.isOver()) {
								rObject = rectangleObject;
								break;
							}
						}
					}
					if (rObject != null) {
						selectedBoat.setMatrizPosition(rObject.getMatrizX(), rObject.getMatrizY());
						boolean correct = true;
						for (GameLocation boatLoc : selectedBoat.getBoatPositions(selectedBoat.getMatrizX(), selectedBoat.getMatrizY())) {
							if (isOnTable(boatLoc.getX(), boatLoc.getY())) {
								if (getMatriz()[boatLoc.getX()][boatLoc.getY()] != 0) {
									correct = false;
									break;
								}
							} else {
								correct = false;
								break;
							}
						}
						if (correct) {
							for (int i = 0; i < selectedBoat.getDistance(); i++) {
								List<GameLocation> boatRound = selectedBoat.getRotation().getMatrizRound(rObject.getMatrizX(), rObject.getMatrizY(), i, selectedBoat.getDistance());
								for (GameLocation round : boatRound) {
									if (isOnTable(round.getX(), round.getY())) {
										if (getMatriz()[round.getX()][round.getY()] != 0) {
											correct = false;
											break;
										}
									}
								}
							}
						}
						if (correct) {
							for (GameLocation boatLoc : selectedBoat.getBoatPositions(selectedBoat.getMatrizX(), selectedBoat.getMatrizY())) {
								getMatriz()[boatLoc.getX()][boatLoc.getY()] = selectedBoat.getID();
							}
						} else {
							selectedBoat.setX(selectedBoat.getLastLocation().getX());
							selectedBoat.setY(selectedBoat.getLastLocation().getY());
							selectedBoat.setMatrizPosition(-1, -1);
						}
					} else {
						selectedBoat.setMatrizPosition(-1, -1);
					}
					int boatOutOfTable = 0;
					for (GameObject object : getGameObjects()) {
						if (object instanceof Boat) {
							Boat boat = (Boat) object;
							if (!boat.isOnTable()) {
								boatOutOfTable++;
							} 
						}
					}
					for (GameObject object : getGameObjects()) {
						if (object instanceof TextButton) {
							TextButton button = (TextButton) object;
							if (button.getText().equals("Siguiente")) {
								if (boatOutOfTable == 0) {
									button.setShow(true);
								} else {
									button.setShow(false);
								}
							}
						}
					}
					selectedBoat.setRestartColorTimer(System.currentTimeMillis() + 5000);
					selectedBoat = null;
					if (alertMessage != 0) {
						hideAlertMessage = System.currentTimeMillis() + 5000;
					}
					System.out.println("boat desel");
				}
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
		setKeyInput(new KeyInput() {
			@Override
			public void tick() {}
			@Override
			public void onKeyPressed(int key) {}
			@Override
			public void onKeyReleased(int key) {}			
		});
		Button button = new TextButton("Siguiente", "next", new GameLocation(900, 600), getGame(), new ObjectDimension(250, 100)) {
			@Override
			public void onClick() {
				TransactionScreen ts = (TransactionScreen) getGame().getScreenManager().getScreen("transaction");
				ts.setMessage(-1);
				getGame().setScreen("transaction");
				boatPosizioned = true;
				setShow(false);
			}
			@Override
			public void onOver() {}
		};
		button.setShow(false);
		addGameObject(button);
		addGameObject(new ImageButton("...", "emotes", TexturePath.BOCADILLO, new GameLocation(1000, 650), getGame(), new ObjectDimension(50, 50)) {
			@Override
			public void onClick() {
				System.out.println("sda");
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
		attacked = false;
		timeToAttack = 60;
		timer = 60 * getGame().getMaxTPS();
		showEmotes(getGame().isShowEmotePage());
	}
	private int timer = 60 * getGame().getMaxTPS();
	private String abc = "ABCDEFGHIJ"; // caracteres que se mostraran en la tabla
	@Override
	protected void paintLevel(Graphics g) {
		g.drawImage(SinkTheFloat.getInstance().getTextureManager().getTexture(TexturePath.FONDO).getTexture(), 0, 0, 1200, 800, null, null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Aria", Font.PLAIN, 30));
		for (int x = 0; x < getMatriz().length; x++) {
			int posX = (x * 50) + (getWindows().getX() / 2) - ((getMatriz().length / 2) * 50);
			int posY = (getWindows().getY() / 2) - (((getMatriz().length + 2) / 2) * 50);
			int posX2 = (getWindows().getX() / 2) - ((getMatriz().length / 2) * 50);
			int posY2 = (x * 50) + (getWindows().getY() / 2) - (((getMatriz().length + 2) / 2) * 50);
			g.drawString(String.valueOf(x + 1), (x == 9 ? posX2 - 42 : posX2 - 30), posY2 + 35);
			g.drawString(String.valueOf(abc.charAt(x)), posX + 15, posY - 10);
		}
		showTime(g);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Aria", Font.PLAIN, 20));
		g.drawString("Coloca tus barcos en el tablero", 450, 25);
		if (boatPosizioned) {
			g.setFont(SinkTheFloat.getInstance().getFontManager().getFont("Airborne").deriveFont(Font.PLAIN, 40));
			g.drawString("Barcos vivos: " + getGame().getGameData().getAlivedBoats(), 30, 700);
		}
		showMessage(alertMessage, g);
		for (PlayerType pType : PlayerType.values()) {
			if (getGame().getGameData().getMachineLogic().hasEmote(pType)) {
				getGame().getGameData().getMachineLogic().getEmote(pType).render(g);
			}
		}
	}
	private void showMessage(int id, Graphics g) {
		if (id == 1) {
			g.setColor(new Color(50, 50, 50, 150));
			g.fillRect(425, 645, 350, 50);
			g.setColor(new Color(255, 20, 20));
			g.setFont(new Font("Aria", Font.PLAIN, 20));
			g.drawString("El barco no puede salir del tablero", 450, 675);
		} else if (id == 2) {
			g.setColor(new Color(50, 50, 50, 150));
			g.fillRect(425, 645, 400, 50);
			g.setColor(new Color(255, 20, 20));
			g.setFont(new Font("Aria", Font.PLAIN, 20));
			g.drawString("El barco no puede chocar con otro barco", 450, 675);
		} else if (id == 3) {
			g.setColor(new Color(50, 50, 50, 150));
			g.fillRect(425, 645, 360, 50);
			g.setColor(new Color(255, 20, 20));
			g.setFont(new Font("Aria", Font.PLAIN, 20));
			g.drawString("No coloques un barco cerca de otro", 450, 675);
		} else if (id == 4) {
			g.setColor(new Color(50, 50, 50, 150));
			g.fillRect(425, 645, 290, 50);
			g.setColor(new Color(255, 20, 20));
			g.setFont(new Font("Aria", Font.PLAIN, 20));
			if (getGame().getGameData().getWinner() != null) {
				g.drawString("Mostrando resultados en " + ((transactionTime - System.currentTimeMillis()) / 1000) + "s", 440, 675);
			} else {
				g.drawString("Cambiando de atacante en " + ((transactionTime - System.currentTimeMillis()) / 1000) + "s", 440, 675);
			}
		}
	}
	@Override
	public void internalTick() {
		if ((int) (timer / getGame().getMaxTPS()) >= 0) {
			timer--;
		}
		if (alertMessage != 0 && hideAlertMessage != 0) {
			if (hideAlertMessage - System.currentTimeMillis() <= 0) {
				hideAlertMessage = 0;
				alertMessage = 0;
			}
		}
		if (boatPosizioned) {
			if (attacked) {
				if (transactionTime != 0 && transactionTime - System.currentTimeMillis() <= 0) {
					if (getGame().getGameData().getWinner() != null) {
						getGame().setScreen("win");
					} else {
						TransactionScreen ts = (TransactionScreen) getGame().getScreenManager().getScreen("transaction");
						ts.setMessage(1);
						getGame().setScreen("transaction");
						transactionTime = 0; 
						alertMessage = 0;
					}
				}
			} else {
				if (timeToAttack <= 0) {
					try {
						getGame().getGameData().getMachineLogic().executeAttack();
					} catch (StackOverflowError ex) {
						System.out.println("Error al atacar, no quedan casillas disponibles?");
					}
				} else {
					timeToAttack--;
				}
			}
		}
		for (PlayerType pType : PlayerType.values()) {
			if (getGame().getGameData().getMachineLogic().hasEmote(pType)) {
				getGame().getGameData().getMachineLogic().getEmote(pType).tick();
			}
		}
	}
	private void showTime(Graphics g) {
		int alertTime = timer/getGame().getMaxTPS();
		int internalAlertTime = timer/(getGame().getMaxTPS()/2);
		if (alertTime == 30 || alertTime == 25 || alertTime == 20 || alertTime == 15 || alertTime == 10) {
			g.setColor(new Color(255, 50, 50, 200));
		} else if (internalAlertTime%2 != 0 && internalAlertTime <= 10) {
			g.setColor(new Color(255, 50, 50, 200));
		} else {
			g.setColor(new Color(255, 255, 255, 200));
		}
		g.setFont(SinkTheFloat.getInstance().getFontManager().getFont("Airborne").deriveFont(Font.PLAIN, timer >= 0 ? 50 : 35));
		g.drawString(timer >= 0 ? "Tiempo: " + (timer / getGame().getMaxTPS()) : "¡Tiempo agotado!", 880, 350);
	}
	public RectangleScreenObject getRectangle(int x, int y) {
		for (GameObject object : getGameObjects()) {
			if (object instanceof RectangleScreenObject) {
				RectangleScreenObject rObject = (RectangleScreenObject) object;
				if (rObject.getMatrizX() == x && rObject.getMatrizY() == y) {
					return rObject;
				}
			}
		}
		return null;
	}
	public int[][] getMatriz() {
		return getGame().getGameData().getYourData();
	}
	public void newGame() {
		boatPosizioned = false;
		restartObjects(Boat.class);
		restartObjects(RectangleScreenObject.class);
		for (GameObject object : getGameObjects()) {
			if (object instanceof TextButton) {
				TextButton button = (TextButton) object;
				if (button.getText().equals("Siguiente")) {
					button.setShow(false);
				}
			}
		}
		for (int x = 0; x < getMatriz().length; x++) {
			for (int y = 0; y < getMatriz()[x].length; y++) {
				int posX = (x * 50) + (getWindows().getX() / 2) - ((getMatriz().length / 2) * 50);
				int posY = (y * 50) + (getWindows().getY() / 2) - (((getMatriz().length + 2) / 2) * 50);
				addGameObject(new RectangleScreenObject(x, y, new GameLocation(posX, posY), getGame(), new ObjectDimension(49, 49)) {
					@Override
					public int getData() {
						return getMatriz()[getMatrizX()][getMatrizY()]; // x & y es una valiable protegida de GameObject
					}
					
				});
			}
		}
		for (int i = 0; i < 4; i++) {
			addGameObject(new Fragata(new GameLocation(100 + (50 * i), 500), getGame()));
		}
		for (int i = 0; i < 3; i++) {
			addGameObject(new Destructor(new GameLocation(125 + (50 * i), 450), getGame()));
		}
		for (int i = 0; i < 2; i++) {
			addGameObject(new Submarino(new GameLocation(150 + (50 * i), 350), getGame()));
		}
		addGameObject(new Portaaviones(new GameLocation(175, 250), getGame()));
		getGame().getGameData().getAlivedBoats(PlayerType.YOU);
		getGame().getGameData().getMachineLogic().addEmote(TexturePath.EMOTE_CACA, PlayerType.ENEMY);
		getGame().getGameData().getMachineLogic().addEmote(TexturePath.EMOTE_CALABAZA, PlayerType.ENEMY);
		getGame().getGameData().getMachineLogic().addEmote(TexturePath.EMOTE_DEMONIO, PlayerType.ENEMY);
		showEmotes(false);
	}
	public boolean isOnTable(int x, int y) {
		return x >= 0 && x <= getMatriz().length-1 && y >= 0 && y <= getMatriz().length-1;
	}
	public boolean isBoatPosizioned() {
		return boatPosizioned;
	}
	public void won() {
		transactionTime = System.currentTimeMillis() + 4000;
		alertMessage = 2;	
	}
	public void attackWinned() {
		timer = 60 * getGame().getMaxTPS();
		if (getGame().getGameData().getWinner() != null) {
			transactionTime = System.currentTimeMillis() + 4000;
			alertMessage = 4;
			attacked = true;
		} else {
			attacked = false;
			timeToAttack = 60;
		}
	}
	public void attackLosed() {
		getGame().getGameData().setTurn(PlayerType.ENEMY);
		transactionTime = System.currentTimeMillis() + 4000;
		alertMessage = 4;
		attacked = true;
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