package us.pepeyedu.SinkTheFloat.Game.Screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import us.pepeyedu.SinkTheFloat.SinkTheFloat;
import us.pepeyedu.SinkTheFloat.Game.Game;
import us.pepeyedu.SinkTheFloat.Game.Animations.Animation;
import us.pepeyedu.SinkTheFloat.Game.Objects.RectangleScreenObject;
import us.pepeyedu.SinkTheFloat.Game.Objects.Boats.Boat;
import us.pepeyedu.SinkTheFloat.Game.Objects.Boats.Destructor;
import us.pepeyedu.SinkTheFloat.Game.Objects.Boats.Fragata;
import us.pepeyedu.SinkTheFloat.Game.Objects.Boats.Portaaviones;
import us.pepeyedu.SinkTheFloat.Game.Objects.Boats.Submarino;
import us.pepeyedu.SinkTheFloat.Textures.Texture;
import us.pepeyedu.SinkTheFloat.Textures.TextureManager;
import us.pepeyedu.SinkTheFloat.Textures.TexturePath;
import us.pepeyedu.SinkTheFloat.Windows.KeyInput;
import us.pepeyedu.SinkTheFloat.Windows.MouseInput;
import us.pepeyedu.SinkTheFloat.Windows.Windows;
import us.pepeyedu.SinkTheFloat.Windows.Buttons.MouseButtons;
import us.pepeyedu.SinkTheFloat.Utils.*;

public class AttackScreen extends Screen {
	private int alertMessage = 0;
	private long hideAlertMessage = 0;
	private List<GameLocation> positionAttackeds = new ArrayList<GameLocation>();
	private long transactionTime = 0;
	public AttackScreen(Windows windows, Game game) {
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
			public void tick() {
				
			}
			@Override
			public void onClick(MouseButtons mouseButton) {
				
			}
			@Override
			public void onWheelMoved(MouseButtons mouseButton) {
				
			}
			@Override
			public void onButtonPressed(MouseButtons mouseButton) {
				
			}
			@Override
			public void onButtonReleased(MouseButtons mouseButton) {
				if (getGame().getGameData().getTurn().equals(PlayerType.YOU) && transactionTime == 0) {
					for (GameObject object : getGameObjects()) {
						if (object instanceof RectangleScreenObject) {
							RectangleScreenObject rso = (RectangleScreenObject) object;
							if (rso.isOver()) {
								if (!isAttacked(rso.getMatrizX(), rso.getMatrizY())) {
									attack(rso);
								} else {
									hideAlertMessage = System.currentTimeMillis() + 5000;
									alertMessage = 1;
								}
								break;
							}
						}
					}
				}
			}			
		});
	}
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
		g.drawString("Selecciona la ubicación para atacar", 435, 25);
		g.setFont(SinkTheFloat.getInstance().getFontManager().getFont("Airborne").deriveFont(Font.PLAIN, 40));
		g.drawString("Barcos vivos: " + getGame().getGameData().getAlivedEnemyBoats(), 30, 700);
		showMessage(alertMessage, g);
	}
	private void showMessage(int id, Graphics g) {
		if (id == 1) {
			g.setColor(new Color(50, 50, 50, 150));
			g.fillRect(425, 645, 290, 50);
			g.setColor(new Color(255, 20, 20));
			g.setFont(new Font("Aria", Font.PLAIN, 20));
			g.drawString("¡Estás repitiendo el ataque!", 450, 675);
		} else if (id == 2) {
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
	public void onOpen() {
		timer = 60 * getGame().getMaxTPS();
	}
	private int timer = 60 * getGame().getMaxTPS();
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
	@Override
	public void internalTick() {
		if ((int) (timer / getGame().getMaxTPS()) >= 0) {
			timer--;
		}
		if (hideAlertMessage != 0 && hideAlertMessage - System.currentTimeMillis() < 0) {
			hideAlertMessage = 0;
			alertMessage = 0;
		}
		if (transactionTime != 0 && transactionTime - System.currentTimeMillis() <= 0) {
			if (getGame().getGameData().getWinner() != null) {
				getGame().setScreen("win");
			} else {
				TransactionScreen ts = (TransactionScreen) getGame().getScreenManager().getScreen("transaction");
				ts.setMessage(2);
				getGame().setScreen("transaction");
			}
			transactionTime = 0;
			alertMessage = 0;
		}
	}
	public int[][] getMatriz() {
		return getGame().getGameData().getEnemyData();
	}
	public void newGame() {
		positionAttackeds.clear();
		restartObjects(Boat.class);
		restartObjects(RectangleScreenObject.class);
		restartAnimations();
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
			addGameObject(new Fragata(new GameLocation(100 + (50 * i), 500), getGame(), true));
		}
		for (int i = 0; i < 3; i++) {
			addGameObject(new Destructor(new GameLocation(125 + (50 * i), 450), getGame(), true));
		}
		for (int i = 0; i < 2; i++) {
			addGameObject(new Submarino(new GameLocation(150 + (50 * i), 350), getGame(), true));
		}
		addGameObject(new Portaaviones(new GameLocation(175, 250), getGame(), true));
		getGame().getGameData().restartEnemyData();
		randomizeBoats();
		timer = 60 * getGame().getMaxTPS();
		getGame().getGameData().getAlivedBoats(PlayerType.ENEMY);
	}
	private void randomizeBoats() {
		for (GameObject object : getGameObjects()) {
			if (object instanceof Boat) {
				Boat boat = (Boat) object;
				try {
					checkEnemyBoat(boat);
				} catch (StackOverflowError ex) {
					//System.out.println("Error al colocar el barco " + boat.getName());
					//ex.printStackTrace();
					getGame().getGameData().restartEnemyData();
					randomizeBoats();
				}
			}
		}
	}
	private boolean checkEnemyBoat(Boat boat) {
		Boolean correct = true;
		GameLocation randomLocation = new GameLocation(Utils.random.nextInt(getMatriz().length), Utils.random.nextInt(getMatriz().length));
		CardinalPoints rcp = CardinalPoints.getByID(Utils.random.nextInt(3) + 1);		
		if (getMatriz()[randomLocation.getX()][randomLocation.getY()] == 0) {
			boat.setMatrizPosition(randomLocation.getX(), randomLocation.getY());
			boat.rotate(rcp);
			for (int i = 0; i < boat.getDistance(); i++) {
				for (GameLocation round : boat.getRotation().getMatrizRound(randomLocation.getX(), randomLocation.getY(), i, boat.getDistance())) {
					if (isOnTable(round.getX(), round.getY())) {
						if (getMatriz()[round.getX()][round.getY()] != 0) {
							correct = false;
							break;
						}
					}
				}
			}
		} else {
			correct = false;
		}
		if (correct) {
			for (GameLocation boatloc : boat.getBoatPositions(boat.getMatrizX(), boat.getMatrizY()) ) {
				if (isOnTable(boatloc.getX(), boatloc.getY())) {
					if (getMatriz()[boatloc.getX()][boatloc.getY()] != 0) {
						System.out.println(boatloc.getX() + " - " + boatloc.getY() + " está el barco " + getMatriz()[boatloc.getX()][boatloc.getY()]);
						correct = false;
					}
				} else {
					correct = false;
					break;
				}
			}
		}
		if (correct) {
			for (GameLocation boatloc : boat.getBoatPositions(boat.getMatrizX(), boat.getMatrizY()) ) {
				if (isOnTable(boatloc.getX(), boatloc.getY())) {
					if (getMatriz()[boatloc.getX()][boatloc.getY()] == 0) {
						getMatriz()[boatloc.getX()][boatloc.getY()] = boat.getID();
					}
				} else {
					correct = false;
					break;
				}
			}
			if (correct) {
				RectangleScreenObject rObject = null;
				for (GameObject sobject : getGameObjects()) {
					if (sobject instanceof RectangleScreenObject) {
						RectangleScreenObject rectangleObject = (RectangleScreenObject) sobject;
						if (rectangleObject.getMatrizX() == boat.getMatrizX() && rectangleObject.getMatrizY() == boat.getMatrizY()) {
							rObject = rectangleObject;
							break;
						}
					}
				}
				boat.setX(rObject.getX());
				boat.setY(rObject.getY());
			}
		}
		return correct ? correct : checkEnemyBoat(boat);
	}
	private boolean isAttacked(int x, int y) {
		boolean attacked = false;
		for (GameLocation positionAttacked : positionAttackeds) {
			if (positionAttacked.getX() == x && positionAttacked.getY() == y) {
				return true;
			}
		}
		return attacked;
	}
	private void attack(RectangleScreenObject rso) {
		positionAttackeds.add(new GameLocation(rso.getMatrizX(), rso.getMatrizY()));
		if (rso.getData() == 0) {
			getGame().getGameData().getMachineLogic().onLose(PlayerType.YOU, this);
			rso.setAttacked(true);
		} else if (rso.getData() != 0) {
			getGame().getGameData().getMachineLogic().onAttack(PlayerType.YOU, this);
			TextureManager tm = SinkTheFloat.getInstance().getTextureManager();
			Boat boat = getGame().getGameData().getMachineLogic().getBoat(this, rso.getData(), rso.getMatrizX(), rso.getMatrizY());
			Animation explodeAnimation = new Animation(new Texture[] { tm.getTexture(TexturePath.EXPLOSION), tm.getTexture(TexturePath.EXPLOSION1), tm.getTexture(TexturePath.EXPLOSION2), tm.getTexture(TexturePath.EXPLOSION1), tm.getTexture(TexturePath.EXPLOSION) }, 200L, new GameLocation(rso.getX(), rso.getY()), rso.getDimension()) {
				@Override
				public void onFinish() {
					if (boat.isSink()) {
						removeAnimation(this);
						getGame().getGameData().getMachineLogic().onSink(PlayerType.YOU, boat, getScreen(), rso);
					} else {
						Animation fireAnimation = new Animation(new Texture[] { tm.getTexture(TexturePath.FUEGO), tm.getTexture(TexturePath.FUEGO1) }, 200L, new GameLocation(rso.getX(), rso.getY()), rso.getDimension()) {
							@Override
							public void onFinish() {}						
						};
						fireAnimation.setBucle(true);
						addAnimation(fireAnimation);
						rso.setAnimation(fireAnimation);
						removeAnimation(this);
					}
				}				
			};
			addAnimation(explodeAnimation);
			rso.setAnimation(explodeAnimation);
			boat.attack();
		}
	}
	public void won() {
		transactionTime = System.currentTimeMillis() + 4000;
		alertMessage = 2;
	}
	public void attackWinned() {
		timer = 60 * getGame().getMaxTPS();
	}
	public void attackLosed() {
		getGame().getGameData().setTurn(PlayerType.YOU);
		transactionTime = System.currentTimeMillis() + 4000;
		alertMessage = 2;
	}
	public boolean isOnTable(int x, int y) {
		return x >= 0 && x <= getMatriz().length-1 && y >= 0 && y <= getMatriz().length-1;
	}
	private AttackScreen getScreen() {
		return this;
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
}
