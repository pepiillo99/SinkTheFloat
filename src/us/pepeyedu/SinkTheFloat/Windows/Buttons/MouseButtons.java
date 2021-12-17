package us.pepeyedu.SinkTheFloat.Windows.Buttons;

public enum MouseButtons {
	WHEEL_UP(-1),
	WHEEL_DOWN(-2),
	LEFT_CLICK(1),
	WHEEL_CLICK(2),
	RIGHT_CLICK(3),
	EXTRA_BUTTON(4); // del cuatro palante (en el caso de que el raton tuviese mas botones)

	private int id;
	MouseButtons(int id) {
		this.id = id;
	}
	public int getID() {
		return id;
	}
	public static MouseButtons getClickedButton(int id) {
		for (MouseButtons mb : values()) {
			if (mb.getID() == id) {
				return mb;
			}
		}
		return EXTRA_BUTTON;
	}
}
