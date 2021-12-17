package us.pepeyedu.SinkTheFloat.Utils;

public enum PlayerType {
	YOU("Tu"),
	ENEMY("Rival");
	
	private String name;
	
	PlayerType(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
}
