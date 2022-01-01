package us.pepeyedu.SinkTheFloat.Game;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import us.pepeyedu.SinkTheFloat.Game.Screen.AttackScreen;
import us.pepeyedu.SinkTheFloat.Game.Screen.Screen;
import us.pepeyedu.SinkTheFloat.Game.Screen.ScreenManager;
import us.pepeyedu.SinkTheFloat.Game.Screen.YourBoatsScreen;
import us.pepeyedu.SinkTheFloat.Textures.TexturePath;
import us.pepeyedu.SinkTheFloat.Utils.GameAudio;
import us.pepeyedu.SinkTheFloat.Utils.GameObject;
import us.pepeyedu.SinkTheFloat.Windows.Windows;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 3210817684040472217L;
	private String screen = "";
	private boolean running = false;
	private Thread thread;
	private Windows windows;
	private ScreenManager screenManager;
	private int objectID = 0;
	private HashMap<Integer, GameObject> objects = new HashMap<Integer, GameObject>();
	private int tps = 1;
	private int fps = 1;
	private GameData gameData;
	private GameAudio soundtrack;
	private List<TexturePath> allEmotes = new ArrayList<TexturePath>();
	private int emoteLineSelected = 0;
	private boolean showEmotePage = false;
	public Game() {
		windows = new Windows("SinkTheFloat", 1200, 800, this);
		screenManager = new ScreenManager(this);
		setScreen("loading");
		start();
		for (TexturePath tp : TexturePath.values()) {
			if (!tp.equals(TexturePath.BOCADILLO) && tp.isEmote()) {
				allEmotes.add(tp);
			}
		}
	}
	public List<TexturePath> getAllEmotes() {
		return allEmotes;
	}
	public boolean isShowEmotePage() {
		return showEmotePage;
	}
	public void setShowEmotePage(boolean showEmotePage) {
		this.showEmotePage = showEmotePage;
	}
	public int getEmoteLineSelected() {
		return emoteLineSelected;
	}
	public void setEmoteLineSelected(int emoteLineSelected) {
		this.emoteLineSelected = emoteLineSelected;
		System.out.println(emoteLineSelected);
	}
	public void start() {
		windows.setVisible();
		createBufferStrategy(3);
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	public void stop() {
		try {
			thread.join();
			running = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private void tick() {
		for (GameObject object : getObjects()) {
			object.internalTick();
		}
		if (getScreen() != null) {
			getScreen().tick();
		}
	}
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(getScreen().getBackground());
		g.fillRect(0, 0, getWindows().getXToPaint(), getWindows().getYToPaint());
		getScreen().buildLevel(g);
		for (GameObject object : getObjects()) {
			object.render(g);
		}
		g.dispose();
		bs.show();
	}
	public Screen getScreen() {
		return screenManager.getScreen(screen);
	}
	public void setScreen(String screen) {
		if (screenManager.getScreen(screen) != null) {
			if (getScreen() != null) {
				getScreen().restartMouseLocation();
				getScreen().onClose();
				this.removeMouseListener(getScreen().getMouseInput());
				this.removeMouseWheelListener(getScreen().getMouseInput());
				this.removeMouseMotionListener(getScreen().getMouseInput());
				this.removeKeyListener(getScreen().getKeyInput());
			}
			this.screen = screen;
			this.addMouseListener(getScreen().getMouseInput());
			this.addMouseMotionListener(getScreen().getMouseInput());
			this.addMouseWheelListener(getScreen().getMouseInput());
			this.addKeyListener(getScreen().getKeyInput());
			getScreen().onOpen();
			getScreen().restartMouseLocation();
		} else {
			System.out.println("No se pudo cambiar la screen porque es nulla");
		}
	}
	public Windows getWindows() {
		return windows;
	}
	double maxTPS = 20.0;
	double maxFPS = 60.0;
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double nsTicks = 1000000000 / maxTPS;
		double deltaTicks = 0;
		double nsFPS = 1000000000 / maxFPS;
		double deltaFPS = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		int tick = 0;
		while(running) {
			long now = System.nanoTime();
			deltaTicks += (now - lastTime) / nsTicks;
			deltaFPS += (now - lastTime) / nsFPS;
			lastTime = now;
			while(deltaTicks >= 1) {
				if (running) {
					tick++;
					tick();
					deltaTicks--;
				}
			}
			if (maxFPS != 0) {
				while(deltaFPS >= 1) {
					if (running) {
						if (getScreen() != null) {
							render();
						}
						frames++;
						deltaFPS--;
					}
				}
			} else {
				if (running) {
					if (getScreen() != null) {
						render();
					}
					frames++;
				}
			}
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				System.out.println("Ticks: " + tick);
				fps = frames;
				tps = tick;
				tick = 0;
				frames = 0;
			}
		}
		stop();
	}
	public int getNextObjectID() {
		return objectID++;
	}
	public Collection<GameObject> getObjects() {
		return objects.values();
	}
	public GameObject getObject(int id) {
		if (objects.containsKey(id)) {
			return objects.get(id);
		} else {
			return null;
		}
	}
	public void addObject(GameObject object) {
		objects.put(object.getID(), object);
	}
	public void removeObject(int id) {
		objects.remove(id);
	}
	public void removeObject(GameObject object) {
		objects.remove(object.getID());
	}
	public int getTPS() {
		return tps;
	}
	public int getFPS() {
		return fps;
	}
	public int getMaxFPS() {
		return (int) maxFPS;
	}
	public int getMaxTPS() {
		return (int) maxTPS;
	}
	public ScreenManager getScreenManager() {
		return screenManager;
	}
	public GameData getGameData() {
		return gameData;
	}
	public void newGame() {
		gameData = new GameData(this);
		YourBoatsScreen ybs = (YourBoatsScreen) getScreenManager().getScreen("you");
		AttackScreen ats = (AttackScreen) getScreenManager().getScreen("attack");
		ybs.newGame();
		ats.newGame();
	}
	public GameAudio getSoundTrack() {
		return soundtrack;
	}
	public void setSoundTrack(GameAudio soundTrack) {
		if (soundtrack == null) {
			this.soundtrack = soundTrack;
		}
	}
}
