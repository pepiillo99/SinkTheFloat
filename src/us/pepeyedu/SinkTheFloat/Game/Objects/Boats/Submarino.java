package us.pepeyedu.SinkTheFloat.Game.Objects.Boats;

import us.pepeyedu.SinkTheFloat.Game.Game;
import us.pepeyedu.SinkTheFloat.Utils.GameLocation;

public class Submarino extends Boat {
	public Submarino(GameLocation gameLocation, Game game) {
		super(3, 3, "Submarino", gameLocation, game);
	}
	public Submarino(GameLocation gameLocation, Game game, boolean isEnemyBoat) {
		super(3, 3, "Submarino", gameLocation, game, isEnemyBoat);
	}
}
