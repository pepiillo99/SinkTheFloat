package us.pepeyedu.SinkTheFloat.Game.Screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import us.pepeyedu.SinkTheFloat.SinkTheFloat;
import us.pepeyedu.SinkTheFloat.Game.Game;
import us.pepeyedu.SinkTheFloat.Game.Objects.Screen.Button;
import us.pepeyedu.SinkTheFloat.Textures.TexturePath;
import us.pepeyedu.SinkTheFloat.Utils.GameLocation;
import us.pepeyedu.SinkTheFloat.Utils.GameObject;
import us.pepeyedu.SinkTheFloat.Utils.ObjectDimension;
import us.pepeyedu.SinkTheFloat.Utils.PlayerType;
import us.pepeyedu.SinkTheFloat.Utils.Utils;
import us.pepeyedu.SinkTheFloat.Windows.MouseInput;
import us.pepeyedu.SinkTheFloat.Windows.Windows;
import us.pepeyedu.SinkTheFloat.Windows.Buttons.MouseButtons;

public class WinLoseScreen extends Screen {
	public WinLoseScreen(Windows windows, Game game) {
		super(windows, game);
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
		addGameObject(new Button("Volver", new GameLocation(50, 650), game, new ObjectDimension(200, 70)) {
			@Override
			public void onClick() {
				getGame().setScreen("main");
			}
			@Override
			public void onOver() {
				// TODO Auto-generated method stub
				
			}			
		});
	}

	@Override
	public void internalTick() {}

	@Override
	protected void paintLevel(Graphics g) {
		Rectangle rec = new Rectangle(((getGame().getWindows().getX()/2)-125), (getGame().getWindows().getY()/2)-100, 250, 50);		
		g.drawImage(SinkTheFloat.getInstance().getTextureManager().getTexture(TexturePath.FONDO).getTexture(), 0, 0, 1200, 800, null, null);
		g.setColor(new Color(255, 255, 255, 255));
		Utils.drawCenteredString(g, "¡Has " + (getGame().getGameData().getWinner() == PlayerType.YOU ? "ganado" : "perdido") + "!", rec, SinkTheFloat.getInstance().getFontManager().getFont("Airborne").deriveFont(Font.PLAIN, 100)); 
	}

}
