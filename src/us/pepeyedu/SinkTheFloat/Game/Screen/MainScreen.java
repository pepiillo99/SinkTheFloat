package us.pepeyedu.SinkTheFloat.Game.Screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import us.pepeyedu.SinkTheFloat.SinkTheFloat;
import us.pepeyedu.SinkTheFloat.Game.Game;
import us.pepeyedu.SinkTheFloat.Game.Objects.Screen.Button;
import us.pepeyedu.SinkTheFloat.Game.Objects.Screen.TextButton;
import us.pepeyedu.SinkTheFloat.Textures.TexturePath;
import us.pepeyedu.SinkTheFloat.Utils.GameLocation;
import us.pepeyedu.SinkTheFloat.Utils.GameObject;
import us.pepeyedu.SinkTheFloat.Utils.ObjectDimension;
import us.pepeyedu.SinkTheFloat.Utils.Utils;
import us.pepeyedu.SinkTheFloat.Windows.KeyInput;
import us.pepeyedu.SinkTheFloat.Windows.MouseInput;
import us.pepeyedu.SinkTheFloat.Windows.Windows;
import us.pepeyedu.SinkTheFloat.Windows.Buttons.MouseButtons;

public class MainScreen extends Screen {
	public MainScreen(Windows windows, Game game) {
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
		setKeyInput(new KeyInput() {
			@Override
			public void tick() {

			}
			@Override
			public void onKeyPressed(int key) {
				
			}
			@Override
			public void onKeyReleased(int key) {
				
			}			
		});
		addGameObject(new TextButton("Jugar", "play", new GameLocation(50, 600), game, new ObjectDimension(200, 70)) {
			@Override
			public void onClick() {
				getGame().newGame();
				getGame().setScreen("you");
			}
			@Override
			public void onOver() {
				// TODO Auto-generated method stub
				
			}			
		});
		addGameObject(new TextButton("Reglas", "rules", new GameLocation(475, 600), game, new ObjectDimension(200, 70)) {
			@Override
			public void onClick() {
				getGame().setScreen("rules");
			}
			@Override
			public void onOver() {
				// TODO Auto-generated method stub
				
			}			
		});
		addGameObject(new TextButton("Opciones", "options", new GameLocation(920, 600), game, new ObjectDimension(220, 70)) {
			@Override
			public void onClick() {
				
			}
			@Override
			public void onOver() {
				// TODO Auto-generated method stub
				
			}			
		});
	}
	@Override
	public void internalTick() {
		
	}
	@Override
	protected void paintLevel(Graphics g) {
		g.drawImage(SinkTheFloat.getInstance().getTextureManager().getTexture(TexturePath.FONDO).getTexture(), 0, 0, 1200, 800, null, null);
		g.setColor(new Color(Utils.random.nextInt(255), Utils.random.nextInt(255), Utils.random.nextInt(255)));
		g.setFont(SinkTheFloat.getInstance().getFontManager().getFont("Airborne").deriveFont(Font.PLAIN, 40));
		g.drawString("Hundir la flota", 415, 525);
		g.drawImage(SinkTheFloat.getInstance().getTextureManager().getTexture(TexturePath.LOGO_SIN_TITULO).getTexture(), 375, 75, 447, 395, null, null);
	}
}
