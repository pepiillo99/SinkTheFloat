package us.pepeyedu.SinkTheFloat.Textures;

public enum TexturePath {
	WATER("Agua", "images/Agua.png"),
	DESCTRUCTOR("Destructor", "images/Boats/Destructor.png", true),
	DESCTRUCTOR1("Destructor1", "images/Boats/Destructor1.png", true),
	FRAGATA("Fragata", "images/Boats/Fragata.png", true),
	PORTAAVIONES("Portaaviones", "images/Boats/Portaaviones.png", true),
	PORTAAVIONES1("Portaaviones1", "images/Boats/Portaaviones1.png", true),
	PORTAAVIONES2("Portaaviones2", "images/Boats/Portaaviones2.png", true),
	PORTAAVIONES3("Portaaviones3", "images/Boats/Portaaviones3.png", true),
	SUBMARINO("Submarino", "images/Boats/Submarino.png", true),
	SUBMARINO1("Submarino1", "images/Boats/Submarino1.png", true),
	SUBMARINO2("Submarino2", "images/Boats/Submarino2.png", true),
	EXPLOSION("Explosion", "images/Animations/Explosion.png"),
	EXPLOSION1("Explosion1", "images/Animations/Explosion1.png"),
	EXPLOSION2("Explosion2", "images/Animations/Explosion2.png"),
	FUEGO("Fuego", "images/Animations/Fuego.png"),
	FUEGO1("Fuego1", "images/Animations/Fuego1.png"),
	HUMO("Humo", "images/Animations/Humo.png"),
	HUMO1("Humo1", "images/Animations/Humo1.png"),
	FONDO("Fondo", "images/fondo.png"),
	LOGO("Logo", "images/logo.png"),
	LOGO_SIN_TITULO("Logo_Sin_Titulo", "images/logo_sintitulo.png");
	

	private String name; 
	private String path;
	private boolean isBoat;
	
	TexturePath(String name, String path) {
		this.name = name;
		this.path = path;
	}
	
	TexturePath(String name, String path, boolean isBoat) {
		this.name = name;
		this.path = path;
		this.isBoat = isBoat;
	}
	public String getName() {
		return name;
	}
	public String getPath() {
		return path;
	}
	public boolean isBoat() {
		return isBoat;
	}
}