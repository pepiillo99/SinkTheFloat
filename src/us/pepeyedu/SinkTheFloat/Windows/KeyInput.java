package us.pepeyedu.SinkTheFloat.Windows;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class KeyInput extends KeyAdapter {
	private List<Integer> pressedKeys = new ArrayList<Integer>();
	public abstract void tick();
	public abstract void onKeyPressed(int key);
	public abstract void onKeyReleased(int key);
	public boolean checkIsPressed(int key) {
		return pressedKeys.contains(key);
	}
	private void addPressedKey(int key)  {
		if (!pressedKeys.contains(key)) {
			pressedKeys.add(key);
		}
	}
	private void removePressedKey(int key) {
		pressedKeys.remove((Object) key);
	}
	public void keyPressed(KeyEvent event) {
		int key = event.getKeyCode();
		addPressedKey(key);
		onKeyPressed(key);
	}
	public void keyReleased(KeyEvent event) {
		int key = event.getKeyCode();
		removePressedKey(key);
		onKeyReleased(key);
	}
}
