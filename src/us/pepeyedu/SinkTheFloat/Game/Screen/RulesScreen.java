package us.pepeyedu.SinkTheFloat.Game.Screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import us.pepeyedu.SinkTheFloat.SinkTheFloat;
import us.pepeyedu.SinkTheFloat.Game.Game;
import us.pepeyedu.SinkTheFloat.Game.Objects.Screen.Button;
import us.pepeyedu.SinkTheFloat.Game.Objects.Screen.TextButton;
import us.pepeyedu.SinkTheFloat.Textures.TexturePath;
import us.pepeyedu.SinkTheFloat.Utils.GameLocation;
import us.pepeyedu.SinkTheFloat.Utils.GameObject;
import us.pepeyedu.SinkTheFloat.Utils.ObjectDimension;
import us.pepeyedu.SinkTheFloat.Utils.Utils;
import us.pepeyedu.SinkTheFloat.Windows.MouseInput;
import us.pepeyedu.SinkTheFloat.Windows.Windows;
import us.pepeyedu.SinkTheFloat.Windows.Buttons.MouseButtons;

public class RulesScreen extends Screen {
	private Rectangle rec;
	private Rectangle rec2;
	private Rectangle rec3;
	private Rectangle rec4;
	private Rectangle rec5;
	private Rectangle rec6;
	private Rectangle rec7;
	private Rectangle rec8;
	private Rectangle rec9;
	public RulesScreen(Windows windows, Game game) {
		super(windows, game);
		rec = new Rectangle(((getGame().getWindows().getX()/2)-125), (getGame().getWindows().getY()/2)-350, 250, 50);
		rec2 = new Rectangle(((getGame().getWindows().getX()/2)-125), (getGame().getWindows().getY()/2)-250, 250, 50);
		rec3 = new Rectangle(((getGame().getWindows().getX()/2)-125), (getGame().getWindows().getY()/2)-200, 250, 50);
		rec4 = new Rectangle(((getGame().getWindows().getX()/2)-125), (getGame().getWindows().getY()/2)-130, 250, 50);
		rec5 = new Rectangle(((getGame().getWindows().getX()/2)-125), (getGame().getWindows().getY()/2)-80, 250, 50);
		rec6 = new Rectangle(((getGame().getWindows().getX()/2)-125), (getGame().getWindows().getY()/2), 250, 50);
		rec7 = new Rectangle(((getGame().getWindows().getX()/2)-125), (getGame().getWindows().getY()/2)+60, 250, 50);
		rec8 = new Rectangle(((getGame().getWindows().getX()/2)-125), (getGame().getWindows().getY()/2)+130, 250, 50);
		rec9 = new Rectangle(((getGame().getWindows().getX()/2)+80), (getGame().getWindows().getY()/2)+300, 250, 50);
		setMouseInput(new MouseInput() {
			@Override
			public void tick() {
				
			}
			@Override
			public void onClick(MouseButtons mouseButton) {
				
			}
			@Override
			public void onWheelMoved(MouseButtons mouseButton) {
				
			}
			@Override
			public void onButtonPressed(MouseButtons mouseButton) {
				for (GameObject object : getGameObjects()) {
					if (object instanceof Button) {
						Button button = (Button) object;
						if (button.isOver()) {
							button.onClick();
						}
					}
				}
			}
			@Override
			public void onButtonReleased(MouseButtons mouseButton) {
				
			}			
		});
		addGameObject(new TextButton("Volver", "back", new GameLocation(50, 650), game, new ObjectDimension(200, 70)) {
			@Override
			public void onClick() {
				getGame().setScreen("main");
			}
			@Override
			public void onOver() {}			
		});
	}

	@Override
	public void internalTick() {}

	@Override
	protected void paintLevel(Graphics g) {		
		g.drawImage(SinkTheFloat.getInstance().getTextureManager().getTexture(TexturePath.FONDO).getTexture(), 0, 0, 1200, 800, null, null);
		g.setColor(Color.WHITE);
		Utils.drawCenteredString(g, "Reglas:", rec, SinkTheFloat.getInstance().getFontManager().getFont("Airborne").deriveFont(Font.PLAIN, 80));
		Utils.drawCenteredString(g, "1.- No puedes colocar barcos.", rec2, SinkTheFloat.getInstance().getFontManager().getFont("Airborne").deriveFont(Font.PLAIN, 50));
		Utils.drawCenteredString(g, "en las zonas adyacentes a otro.", rec3, SinkTheFloat.getInstance().getFontManager().getFont("Airborne").deriveFont(Font.PLAIN, 50));
		Utils.drawCenteredString(g, "2.- Hunde los barcos de la maquina", rec4, SinkTheFloat.getInstance().getFontManager().getFont("Airborne").deriveFont(Font.PLAIN, 50));
		Utils.drawCenteredString(g, "para ganar.", rec5, SinkTheFloat.getInstance().getFontManager().getFont("Airborne").deriveFont(Font.PLAIN, 50));
		Utils.drawCenteredString(g, "3.- Si pierdes le debes una ronda", rec6, SinkTheFloat.getInstance().getFontManager().getFont("Airborne").deriveFont(Font.PLAIN, 50));
		Utils.drawCenteredString(g, "de cervezas a los creadores de este juego.", rec7, SinkTheFloat.getInstance().getFontManager().getFont("Airborne").deriveFont(Font.PLAIN, 50)); 
		Utils.drawCenteredString(g, "4.- Soraya debe poner un 10 a los creadores.", rec8, SinkTheFloat.getInstance().getFontManager().getFont("Airborne").deriveFont(Font.PLAIN, 50)); 
		Utils.drawCenteredString(g, "Creadores: Pepe y Eduardo", rec9, SinkTheFloat.getInstance().getFontManager().getFont("Airborne").deriveFont(Font.PLAIN, 30)); 
	}

}