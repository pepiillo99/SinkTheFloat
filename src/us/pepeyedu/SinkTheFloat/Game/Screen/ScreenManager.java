package us.pepeyedu.SinkTheFloat.Game.Screen;

import java.util.HashMap;

import us.pepeyedu.SinkTheFloat.Game.Game;

public class ScreenManager {
	private Game game;
	private HashMap<String, Screen> screens = new HashMap<String, Screen>();
	public ScreenManager(Game game) {
		this.game = game;
		screens.put("loading", new LoadingScreen(game.getWindows(), game));
	}
	public Screen getScreen(String screen) {
		if (screens.containsKey(screen)) {
			return screens.get(screen);
		} else {
			return null;
		}
	}
	protected <LoadingScreen> void registerNewScreen(String screenName, Screen screen) {
		screens.put(screenName, screen);
	}
}
