package us.pepeyedu.SinkTheFloat.Game.Screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import us.pepeyedu.SinkTheFloat.Game.Game;
import us.pepeyedu.SinkTheFloat.Game.Animations.Animation;
import us.pepeyedu.SinkTheFloat.Game.Objects.Boats.Boat;
import us.pepeyedu.SinkTheFloat.Utils.GameLocation;
import us.pepeyedu.SinkTheFloat.Utils.GameObject;
import us.pepeyedu.SinkTheFloat.Windows.KeyInput;
import us.pepeyedu.SinkTheFloat.Windows.MouseInput;
import us.pepeyedu.SinkTheFloat.Windows.Windows;

public abstract class Screen {
	private Windows windows;
	private Game game;
	private MouseInput mouseInput;
	private GameLocation mouseLoc = new GameLocation(0, 0);
	private KeyInput keyInput;
	private List<GameObject> objects = new ArrayList<GameObject>();
	private List<Animation> animations = new ArrayList<Animation>();
	public Screen(Windows windows, Game game) {
		this.windows = windows;
		this.game = game;
	}
	public Screen(Windows windows, MouseInput mouseInput, KeyInput keyInput) {
		this.windows = windows;
		this.mouseInput = mouseInput;
		this.keyInput = keyInput;
	}
	public void buildLevel(Graphics g) {
		paintLevel(g);
		ArrayList<GameObject> objects_copy = (ArrayList<GameObject>) ((ArrayList<GameObject>) objects).clone();
		for (GameObject object : objects_copy) {
			object.render(g);
		}
		ArrayList<Animation> animation_copy = (ArrayList<Animation>) ((ArrayList<Animation>) animations).clone();
		for (Animation anim : animation_copy) {
			anim.render(g);
		}
		g.setColor(getFPSTPSColor(getGame().getTPS(), getGame().getFPS()));
		g.setFont(new Font("Aria", Font.PLAIN, 10));
		g.drawString("FPS: " + getGame().getFPS() + " TPS: " + getGame().getTPS(), 0, 10);
	}
	public void tick() {
		if (getMouseInput() != null) {
			getMouseInput().tick();
			mouseLoc = new GameLocation(getMouseInput().getX(), getMouseInput().getY());
		}
		if (getKeyInput() != null) {
			getKeyInput().tick();
		}
		ArrayList<GameObject> objects_copy = (ArrayList<GameObject>) ((ArrayList<GameObject>) objects).clone();
		for (GameObject object : objects_copy) {
			if (object != null) {
				object.tick();
			}
		}
		internalTick();
	}
	public void onOpen() {}
	public void onClose() {}
	public abstract void internalTick();
	protected abstract void paintLevel(Graphics g);
	protected Windows getWindows() {
		return windows;
	}
	protected Game getGame() {
		return game;
	}
	public Color getBackground() {
		return new Color(0, 0, 0);
	}
	public MouseInput getMouseInput() {
		return mouseInput;
	}
	public KeyInput getKeyInput() {
		return keyInput;
	}
	protected void setMouseInput(MouseInput mouseInput) {
		this.mouseInput = mouseInput;
	}
	protected void setKeyInput(KeyInput keyInput) {
		this.keyInput = keyInput;
	}
	public List<GameObject> getGameObjects() {
		return objects;
	}
	public void addGameObject(GameObject gameObject) {
		if (!objects.contains(gameObject)) {
			objects.add(gameObject);
		}
	}
	public void removeGameObject(GameObject gameObject) {
		if (objects.contains(gameObject)) {
			objects.remove(gameObject);
		}
	}
	public void restartObjects(Class<? extends GameObject> clase) {
		Iterator<GameObject> iterator = objects.iterator();
		while (iterator.hasNext()) {
			GameObject object = iterator.next();
			if (object.getClass().getSuperclass() == clase) {
				iterator.remove();
			}
		}
	}
	public void addAnimation(Animation anim) {
		if (!animations.contains(anim)) {
			animations.add(anim);
		}
	}
	public void removeAnimation(Animation anim) {
		if (animations.contains(anim)) {
			animations.remove(anim);
		}
	}
	public void restartAnimations() {
		animations.clear();
	}
	public boolean isTouch(GameLocation mouse, GameObject object) {
		if (object instanceof Boat) {
			Boat boat = (Boat) object;
			for (int i = 0; i < boat.getDistance(); i++) {
				GameLocation loc = boat.getRotation().calcPos(boat.getX(), boat.getY(), i, boat.getDimension());
				if (mouse.getX() >= loc.getX() && mouse.getX() <= loc.getX() + boat.getDimension().getX() && mouse.getY() >= loc.getY() && mouse.getY() <= loc.getY() + boat.getDimension().getY()) {
					return true;
				}
			}
		} else {
			if (mouse.getX() >= object.getX() && mouse.getX() <= object.getX() + object.getDimension().getX() && mouse.getY() >= object.getY() && mouse.getY() <= object.getY() + object.getDimension().getY()) {
				return true;
			}
		}
		return false;
	}
	private Color getFPSTPSColor(int tps, int fps) { // vivan las matematicas :)
		double correctFPS = ((getGame().getFPS() != 0 ? getGame().getFPS() : 0.1) * 100) / getGame().getMaxFPS();
		double correctTPS = ((getGame().getTPS() != 0 ? getGame().getTPS() : 0.1) * 100) / getGame().getMaxTPS();
		int totalCorrect = (int) (((correctFPS + correctTPS) * 100) / 200);
		int totalCorrectPorcent = ((255 * totalCorrect) / 100);
		//System.out.println("totalCorrectPorcent: " + totalCorrectPorcent);
		if (totalCorrectPorcent < 0) { // el porcentage puede superar el 100% si los fps o tps son mayores que el maximo
			totalCorrectPorcent = 0;
		} else if (totalCorrectPorcent > 255) { // lo mismo que los fps pero con los tps xd
			totalCorrectPorcent = 255;
		}
		return new Color(255 - totalCorrectPorcent, totalCorrectPorcent, 0, 200);
	}
	public GameLocation getMouseLocation() {
		return mouseLoc;
	}
	public void restartMouseLocation() {
		mouseLoc = new GameLocation(0, 0);
		if (getMouseInput() != null) {
			getMouseInput().restart();
		}
	}
}
