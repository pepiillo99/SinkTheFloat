package us.pepeyedu.SinkTheFloat.Game.Objects.Boats;

import us.pepeyedu.SinkTheFloat.Game.Game;
import us.pepeyedu.SinkTheFloat.Utils.GameLocation;

public class Destructor extends Boat {
	public Destructor(GameLocation gameLocation, Game game) {
		super(2, 2, "Destructor", gameLocation, game);
	}
	public Destructor(GameLocation gameLocation, Game game, boolean isEnemyBoat) {
		super(2, 2, "Destructor", gameLocation, game, isEnemyBoat);
	}
}
