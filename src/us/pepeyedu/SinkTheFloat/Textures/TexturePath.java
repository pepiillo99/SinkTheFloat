package us.pepeyedu.SinkTheFloat.Textures;

public enum TexturePath {
	BOCADILLO("Bocadillo", "images/Emotes/bocadillo.png", false, true),
	EMOTE_ALIEN("Emote alien", "images/Emotes/emote_alien.png", false, true),
	EMOTE_ANGEL("Emote angel", "images/Emotes/emote_angel.png", false, true),
	EMOTE_BESO("Emote beso", "images/Emotes/emote_beso.png", false, true),
	EMOTE_DEMONIO("Emote demonio", "images/Emotes/emote_demonio.png", false, true),
	EMOTE_FANTASMA("Emote fantasma", "images/Emotes/emote_fantasma.png", false, true),
	EMOTE_GAFAS("Emote gafas", "images/Emotes/emote_gafas.png", false, true),
	EMOTE_LLORANDO("Emote llorando", "images/Emotes/emote_llorando.png", false, true),
	EMOTE_MUERTO("Emote muerto", "images/Emotes/emote_muerto.png", false, true),
	EMOTE_PAYASO("Emote payaso", "images/Emotes/emote_payaso.png", false, true),
	EMOTE_RISA("Emote risa", "images/Emotes/emote_risa.png", false, true),
	EMOTE_CACA("Emote caca", "images/Emotes/emote_caca.png", false, true),
	EMOTE_MOLESTO("Emote molesto", "images/Emotes/emote_molesto.png", false, true),
	EMOTE_CALLADO("Emote callado", "images/Emotes/emote_callado.png", false, true),
	EMOTE_GUINO("Emote guino", "images/Emotes/emote_guino.png", false, true),
	EMOTE_MUCHARISA("Emote mucharisa", "images/Emotes/emote_mucharisa.png", false, true),
	EMOTE_MAJO("Emote majo", "images/Emotes/emote_majo.png", false, true),
	EMOTE_VERGONZOSO("Emote vergonzoso", "images/Emotes/emote_vergonzoso.png", false, true),
	EMOTE_FELIZ("Emote feliz", "images/Emotes/emote_feliz.png", false, true),
	EMOTE_MUERTE("Emote muerte", "images/Emotes/emote_muerte.png", false, true),
	EMOTE_VIRUS("Emote virus", "images/Emotes/emote_virus.png", false, true),
	EMOTE_ROBOT("Emote robot", "images/Emotes/emote_robot.png", false, true),
	EMOTE_CALABAZA("Emote calabaza", "images/Emotes/emote_calabaza.png", false, true),
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
	private boolean isEmote;
	
	TexturePath(String name, String path) {
		this.name = name;
		this.path = path;
	}
	TexturePath(String name, String path, boolean isBoat) {
		this.name = name;
		this.path = path;
		this.isBoat = isBoat;
	}	
	TexturePath(String name, String path, boolean isBoat, boolean isEmote) {
		this.name = name;
		this.path = path;
		this.isBoat = isBoat;
		this.isEmote = isEmote;
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
	public boolean isEmote() {
		return isEmote;
	}
}