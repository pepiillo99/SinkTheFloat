package us.pepeyedu.SinkTheFloat.Game.Objects.Boats;

import us.pepeyedu.SinkTheFloat.Game.Game;
import us.pepeyedu.SinkTheFloat.Utils.GameLocation;

public class Portaaviones extends Boat {
	public Portaaviones(GameLocation gameLocation, Game game) {
		super(4, 4, "Portaaviones", gameLocation, game);
	}
	public Portaaviones(GameLocation gameLocation, Game game, boolean isEnemyBoat) {
		super(4, 4, "Portaaviones", gameLocation, game, isEnemyBoat);
	}
}
