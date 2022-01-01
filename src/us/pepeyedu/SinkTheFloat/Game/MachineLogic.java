package us.pepeyedu.SinkTheFloat.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import us.pepeyedu.SinkTheFloat.SinkTheFloat;
import us.pepeyedu.SinkTheFloat.Game.Animations.Animation;
import us.pepeyedu.SinkTheFloat.Game.Emote.Emote;
import us.pepeyedu.SinkTheFloat.Game.Emote.EmoteCategory;
import us.pepeyedu.SinkTheFloat.Game.Objects.RectangleScreenObject;
import us.pepeyedu.SinkTheFloat.Game.Objects.Boats.Boat;
import us.pepeyedu.SinkTheFloat.Game.Screen.AttackScreen;
import us.pepeyedu.SinkTheFloat.Game.Screen.Screen;
import us.pepeyedu.SinkTheFloat.Game.Screen.YourBoatsScreen;
import us.pepeyedu.SinkTheFloat.Textures.Texture;
import us.pepeyedu.SinkTheFloat.Textures.TextureManager;
import us.pepeyedu.SinkTheFloat.Textures.TexturePath;
import us.pepeyedu.SinkTheFloat.Utils.CardinalPoints;
import us.pepeyedu.SinkTheFloat.Utils.GameAudio;
import us.pepeyedu.SinkTheFloat.Utils.GameLocation;
import us.pepeyedu.SinkTheFloat.Utils.GameObject;
import us.pepeyedu.SinkTheFloat.Utils.ObjectDimension;
import us.pepeyedu.SinkTheFloat.Utils.PlayerType;
import us.pepeyedu.SinkTheFloat.Utils.Utils;

public class MachineLogic {
	private List<GameLocation> bannedAttacks = new ArrayList<GameLocation>();
	private CardinalPoints boatCP = null;
	private List<CardinalPoints> bannedCP = new ArrayList<CardinalPoints>();
	private Game game;
	private int followAttacks = 0;
	private GameLocation lastAttack;
	private HashMap<PlayerType, Emote> emotes = new HashMap<PlayerType, Emote>();
	private HashMap<PlayerType, List<Emote>> queuedEmotes = new HashMap<PlayerType, List<Emote>>();
	public MachineLogic(Game game) {
		this.game = game;
	}
	public void onLose(PlayerType playerType, Screen screen) {
		new GameAudio("gota.wav") {
			@Override
			public void onFinish() {}				
		};
		if (screen instanceof YourBoatsScreen) {
			YourBoatsScreen ybs = (YourBoatsScreen) screen;
			ybs.attackLosed();
		} else if (screen instanceof AttackScreen) {
			AttackScreen ats = (AttackScreen) screen;
			ats.attackLosed();
		}
		if (playerType.equals(PlayerType.YOU)) {
			if (Utils.random.nextBoolean()) {
				addEmote(EmoteCategory.PRESUMIR.getRandom(), PlayerType.ENEMY);
			} else {
				addEmote(EmoteCategory.REIR.getRandom(), PlayerType.ENEMY);
			}
		} else if (playerType.equals(PlayerType.ENEMY)) {
			if (Utils.random.nextBoolean()) {
				addEmote(EmoteCategory.LLORAR.getRandom(), PlayerType.ENEMY);
			} else {
				addEmote(EmoteCategory.ENFADADO.getRandom(), PlayerType.ENEMY);
			}
		}
	}
	public void onAttack(PlayerType playerType, Screen screen) {
		if (screen instanceof YourBoatsScreen) {
			YourBoatsScreen ybs = (YourBoatsScreen) screen;
			ybs.attackWinned();
		} else if (screen instanceof AttackScreen) {
			AttackScreen ats = (AttackScreen) screen;
			ats.attackWinned();
		}
		new GameAudio("explosion.wav") {
			@Override
			public void onFinish() {}				
		};
		if (playerType.equals(PlayerType.YOU)) {
			if (Utils.random.nextBoolean()) {
				addEmote(EmoteCategory.LLORAR.getRandom(), PlayerType.ENEMY);
			} else {
				addEmote(EmoteCategory.ENFADADO.getRandom(), PlayerType.ENEMY);
			}
		} else if (playerType.equals(PlayerType.ENEMY)) {
			if (Utils.random.nextBoolean()) {
				addEmote(EmoteCategory.PRESUMIR.getRandom(), PlayerType.ENEMY);
			} else {
				addEmote(EmoteCategory.REIR.getRandom(), PlayerType.ENEMY);
			}
		}
	}
	public void onSink(PlayerType playerType, Boat boat, Screen screen, RectangleScreenObject rObject) {
		TextureManager tm = SinkTheFloat.getInstance().getTextureManager();
		if (playerType.equals(PlayerType.ENEMY)) {
			bannedCP.clear();
			boatCP = null;
			lastAttack = null;
			followAttacks = 0;
			for (int i = 0; i < boat.getDistance(); i++) {
				for (GameLocation gl : boat.getRotation().getMatrizRound(boat.getMatrizX(), boat.getMatrizY(), i, boat.getDistance())) {
					boolean onTable = false;
					if (screen instanceof YourBoatsScreen) {
						onTable = ((YourBoatsScreen) screen).isOnTable(gl.getX(), gl.getY());
					} else if (screen instanceof AttackScreen) {
						onTable = ((AttackScreen) screen).isOnTable(gl.getX(), gl.getY());
					}
					if (onTable && canAttack(gl.getX(), gl.getY())) {
						bannedAttacks.add(gl);
					}
				}
			}
		}
		for (GameLocation gl : boat.getBoatPositions(boat.getMatrizX(), boat.getMatrizY())) {
			RectangleScreenObject rso = null;
			if (screen instanceof YourBoatsScreen) {
				rso = ((YourBoatsScreen) screen).getRectangle(gl.getX(), gl.getY());
			} else if (screen instanceof AttackScreen) {
				rso = ((AttackScreen) screen).getRectangle(gl.getX(), gl.getY());
			}
			if (rso != null) {
				screen.removeAnimation(rso.getAnimation());
				Animation smokeAnimation = new Animation(new Texture[] { tm.getTexture(TexturePath.HUMO), tm.getTexture(TexturePath.HUMO1) }, 200L, new GameLocation(rso.getX(), rso.getY()), rso.getDimension()) {
					@Override
					public void onFinish() {}			
				};
				smokeAnimation.setBucle(true);
				screen.addAnimation(smokeAnimation);
				rso.setAnimation(smokeAnimation);
				int alived = SinkTheFloat.getInstance().getGame().getGameData().getAlivedBoats(playerType.equals(PlayerType.YOU) ? PlayerType.ENEMY : PlayerType.YOU);
				if (alived <= 0) {
					SinkTheFloat.getInstance().getGame().getGameData().setWinner(playerType);
					if (screen instanceof YourBoatsScreen) {
						YourBoatsScreen ybs = (YourBoatsScreen) screen;
						ybs.won();
					} else if (screen instanceof AttackScreen) {
						AttackScreen ats = (AttackScreen) screen;
						ats.won();
					}
				}
			}
		}
		if (playerType.equals(PlayerType.YOU)) {
			if (Utils.random.nextBoolean()) {
				addEmote(EmoteCategory.LLORAR.getRandom(), PlayerType.ENEMY);
			} else {
				addEmote(EmoteCategory.ENFADADO.getRandom(), PlayerType.ENEMY);
			}
		} else if (playerType.equals(PlayerType.ENEMY)) {
			if (Utils.random.nextBoolean()) {
				addEmote(EmoteCategory.PRESUMIR.getRandom(), PlayerType.ENEMY);
			} else {
				addEmote(EmoteCategory.REIR.getRandom(), PlayerType.ENEMY);
			}
		}
	}
	public void executeAttack() {
		Screen screen = game.getScreen();
		if (screen instanceof YourBoatsScreen) {
			YourBoatsScreen ybs = (YourBoatsScreen) screen;			
			if (lastAttack != null) {
				if (boatCP != null) {
					System.out.println(boatCP.name());
					GameLocation nextPosition = boatCP.calcPos(lastAttack.getX(), lastAttack.getY(), 1, new ObjectDimension(1, 1));
					if (ybs.isOnTable(nextPosition.getX(), nextPosition.getY())) {
						RectangleScreenObject rObject = ybs.getRectangle(nextPosition.getX(), nextPosition.getY());
						if (rObject.getData() == 0) {
							rObject.setAttacked(true);
							boatCP = boatCP.getContrary();
							System.out.println("Contrary: " + boatCP);
							lastAttack = boatCP.calcPos(lastAttack.getX(), lastAttack.getY(), followAttacks - 1, new ObjectDimension(1, 1));
							onLose(PlayerType.ENEMY, screen);
						} else {
							touchBoat(nextPosition.getX(), nextPosition.getY(), ybs, rObject);
						}
						bannedAttacks.add(new GameLocation(nextPosition.getX(), nextPosition.getY()));	
					} else {
						boatCP = boatCP.getContrary();
						lastAttack = boatCP.calcPos(lastAttack.getX(), lastAttack.getY(), followAttacks - 1, new ObjectDimension(1, 1));
						executeAttack();
					}	
				} else {
					CardinalPoints cp = CardinalPoints.getRandomNotContains(bannedCP);
					if (cp != null) {
						System.out.println(bannedCP + " randomCP: " + cp.name());
					} else {
						System.out.println("all banneds? " + bannedCP);
					}
					if (bannedCP.contains(cp)) {
						executeAttack();
					} else {
						GameLocation nextPosition = cp.calcPos(lastAttack.getX(), lastAttack.getY(), 1, new ObjectDimension(1, 1));
						if (ybs.isOnTable(nextPosition.getX(), nextPosition.getY()) && canAttack(nextPosition.getX(), nextPosition.getY())) {
							RectangleScreenObject rObject = ybs.getRectangle(nextPosition.getX(), nextPosition.getY());
							if (rObject.getData() == 0) {
								rObject.setAttacked(true);
								bannedCP.add(cp);
								onLose(PlayerType.ENEMY, screen);
							} else {
								System.out.println("CP correcto: " + cp.name());
								boatCP = cp;
								touchBoat(nextPosition.getX(), nextPosition.getY(), ybs, rObject);
							}
							bannedAttacks.add(new GameLocation(nextPosition.getX(), nextPosition.getY()));	
						} else {
							bannedCP.add(cp);
							executeAttack();
						}
					}
				}
			} else {
				int x = Utils.random.nextInt(game.getGameData().getEnemyData().length);
				int y = Utils.random.nextInt(game.getGameData().getEnemyData().length);
				if (canAttack(x, y)) {
					System.out.println("attacking " + x + " - " + y);
					bannedAttacks.add(new GameLocation(x, y));		
						RectangleScreenObject rObject = ybs.getRectangle(x, y);
						if (rObject.getData() == 0) {
							rObject.setAttacked(true);
							lastAttack = null;
							followAttacks = 0;
							onLose(PlayerType.ENEMY, screen);
						} else {
							touchBoat(x, y, ybs, rObject);
						}
				} else {
					executeAttack();
				}
			}
		}		
	}
	private void touchBoat(int x, int y, YourBoatsScreen screen, RectangleScreenObject rObject) {
		if (getBoat(screen, rObject.getData(), x, y) != null) {
			Boat boat  = getBoat(screen, rObject.getData(), x, y);
			TextureManager tm = SinkTheFloat.getInstance().getTextureManager();
			onAttack(PlayerType.ENEMY, screen);
			Animation explodeAnimation = new Animation(new Texture[] { tm.getTexture(TexturePath.EXPLOSION), tm.getTexture(TexturePath.EXPLOSION1), tm.getTexture(TexturePath.EXPLOSION2), tm.getTexture(TexturePath.EXPLOSION1), tm.getTexture(TexturePath.EXPLOSION) }, 200L, new GameLocation(rObject.getX(), rObject.getY()), rObject.getDimension()) {
				@Override
				public void onFinish() {
					System.out.println("Boat Health: " + boat.getHealth());
					if (boat.isSink()) {
						screen.removeAnimation(this);
						onSink(PlayerType.ENEMY, boat, screen, rObject);
					} else {
						Animation fireAnimation = new Animation(new Texture[] { tm.getTexture(TexturePath.FUEGO), tm.getTexture(TexturePath.FUEGO1) }, 200L, new GameLocation(rObject.getX(), rObject.getY()), rObject.getDimension()) {
							@Override
							public void onFinish() {}						
						};
						fireAnimation.setBucle(true);
						screen.addAnimation(fireAnimation);
						rObject.setAnimation(fireAnimation);
						screen.removeAnimation(this);
					}
				}				
			};
			screen.addAnimation(explodeAnimation);
			rObject.setAnimation(explodeAnimation);
			lastAttack = new GameLocation(x, y);
			followAttacks++;
			boat.attack();
		} else {
			throw new NullPointerException("El barco no existe!");
		}
	}
	public Boat getBoat(Screen screen, int id, int x, int y) {
		for (GameObject object : screen.getGameObjects()) {
			if (object instanceof Boat) {
				Boat boat = (Boat) object;
				if (boat.getID() == id) {
					for (GameLocation gl : boat.getBoatPositions(boat.getMatrizX(), boat.getMatrizY())) {
						if (gl.getX() == x && gl.getY() == y) {
							return boat;
						}
					}
				}
			}
		}
		return null;
	}
	private boolean canAttack(int x, int y) {
		for (GameLocation gl : bannedAttacks) {
			if (gl.getX() == x && gl.getY() == y) {
				return false;
			}
		}
		return true;
	}
	public boolean hasEmote(PlayerType pType) {
		return emotes.containsKey(pType);
	}
	public Emote getEmote(PlayerType pType) {
		return emotes.get(pType);
	}
	public int countPendenting(PlayerType pType) {
		if (queuedEmotes.containsKey(pType)) {
			return queuedEmotes.get(pType).size();
		}
		return 0;
	}
	public void addEmote(TexturePath texturePath, PlayerType pType) {
		if (hasEmote(pType)) {
			getEmote(pType).runToFinish();
			if (!queuedEmotes.containsKey(pType)) {
				queuedEmotes.put(pType, new ArrayList<Emote>());
			}
			queuedEmotes.get(pType).add(new Emote(texturePath, pType));
		} else {
			emotes.put(pType, new Emote(texturePath, pType));
		}
	}
	public void finishedEmote(PlayerType pType) {
		emotes.remove(pType);
		if (queuedEmotes.containsKey(pType) && !queuedEmotes.get(pType).isEmpty()) {
			emotes.put(pType, queuedEmotes.get(pType).get(0));
			queuedEmotes.get(pType).remove(0);
		}
	}
}
