package us.pepeyedu.SinkTheFloat.Game.Objects.Boats;

import us.pepeyedu.SinkTheFloat.Game.Game;
import us.pepeyedu.SinkTheFloat.Utils.GameLocation;

public class Fragata extends Boat {
	public Fragata(GameLocation gameLocation, Game game) {
		super(1, 1, "Fragata", gameLocation, game);
	}
	public Fragata(GameLocation gameLocation, Game game, boolean isEnemyBoat) {
		super(1, 1, "Fragata", gameLocation, game, isEnemyBoat);
	}
}
