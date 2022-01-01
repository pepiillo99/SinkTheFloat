package us.pepeyedu.SinkTheFloat.Game.Objects.Screen;

import us.pepeyedu.SinkTheFloat.Game.Game;
import us.pepeyedu.SinkTheFloat.Utils.GameLocation;
import us.pepeyedu.SinkTheFloat.Utils.GameObject;
import us.pepeyedu.SinkTheFloat.Utils.ObjectDimension;

public abstract class Button extends GameObject {
	private String name = "button";
	private boolean over = false;
	private boolean show = true;
	public Button(String name, GameLocation gameLocation, Game game, ObjectDimension dimension) {
		super(gameLocation, game, dimension);
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public boolean isShow() {
		return show;
	}
	public void setShow(boolean show) {
		this.show = show;
	}
	@Override
	public void tick() {
		boolean newover = getGame().getScreen().isTouch(getGame().getScreen().getMouseLocation(), this);
		if (!over && newover) {
			onOver();
		}
		over = newover;
	}
	public boolean isOver() {
		return over;
	}
	public abstract void onClick();
	public abstract void onOver();
}
