package us.pepeyedu.SinkTheFloat.Game;

import us.pepeyedu.SinkTheFloat.Game.Objects.Boats.Boat;
import us.pepeyedu.SinkTheFloat.Game.Screen.AttackScreen;
import us.pepeyedu.SinkTheFloat.Game.Screen.YourBoatsScreen;
import us.pepeyedu.SinkTheFloat.Utils.GameObject;
import us.pepeyedu.SinkTheFloat.Utils.PlayerType;

public class GameData {
	private int[][] yourMatriz = new int[10][10];
	private int[][] enemyMatriz = new int[10][10];
	private PlayerType turn = null;
	private Game game;
	private MachineLogic machineLogic;
	private int alivedBoats = 0;
	private int alivedEnemyBoats = 0;
	private PlayerType winner = null;
	public GameData(Game game) {
		this.game = game;
		machineLogic = new MachineLogic(game);
	}
	public int[][] getYourData() {
		return yourMatriz;
	}
	public int[][] getEnemyData() {
		return enemyMatriz;
	}
	public void setEnemyData(int data, int posx, int posy) {
		enemyMatriz[posx][posy] = data;
	}
	public void setYourData(int data, int posx, int posy) {
		yourMatriz[posx][posy] = data;
	}
	public void restartEnemyData() {
		enemyMatriz = new int[10][10];
	}
	public PlayerType getTurn() {
		return turn;
	}
	public void setTurn(PlayerType turn) {
		this.turn = turn;
	}
	public MachineLogic getMachineLogic() {
		return machineLogic;
	}
	public int getHealths(PlayerType playerType) {
		int health = 0;
		if (playerType.equals(PlayerType.YOU)) {
			YourBoatsScreen ybs = (YourBoatsScreen) game.getScreenManager().getScreen("you");
			for (GameObject go : ybs.getGameObjects()) {
				if (go instanceof Boat) {
					Boat boat = (Boat) go;
					health += boat.getHealth();
				}
			}
		} else {
			AttackScreen as = (AttackScreen) game.getScreenManager().getScreen("attack");
			for (GameObject go : as.getGameObjects()) {
				if (go instanceof Boat) {
					Boat boat = (Boat) go;
					health += boat.getHealth();
				}
			}
		}
		return health;
	}
	public int getAlivedBoats(PlayerType playerType) {
		int alived = 0;
		if (playerType.equals(PlayerType.YOU)) {
			YourBoatsScreen ybs = (YourBoatsScreen) game.getScreenManager().getScreen("you");
			for (GameObject go : ybs.getGameObjects()) {
				if (go instanceof Boat) {
					Boat boat = (Boat) go;
					if (!boat.isSink()) {
						alived++;
					}
				}
			}
			this.alivedBoats = alived;
		} else {
			AttackScreen as = (AttackScreen) game.getScreenManager().getScreen("attack");
			for (GameObject go : as.getGameObjects()) {
				if (go instanceof Boat) {
					Boat boat = (Boat) go;
					if (!boat.isSink()) {
						alived++;
					}
				}
			}
			this.alivedEnemyBoats = alived;
		}
		return alived;
	}
	public int getAlivedBoats() {
		return alivedBoats;
	}
	public int getAlivedEnemyBoats() {
		return alivedEnemyBoats;
	}
	public PlayerType getWinner() {
		return winner;
	}
	public void setWinner(PlayerType winner) {
		this.winner = winner;
	}
}
