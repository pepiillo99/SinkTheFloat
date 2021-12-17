package us.pepeyedu.SinkTheFloat.Game.Screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import us.pepeyedu.SinkTheFloat.SinkTheFloat;
import us.pepeyedu.SinkTheFloat.Game.Game;
import us.pepeyedu.SinkTheFloat.Textures.Texture;
import us.pepeyedu.SinkTheFloat.Textures.TextureChanged;
import us.pepeyedu.SinkTheFloat.Textures.TexturePath;
import us.pepeyedu.SinkTheFloat.Utils.Callback;
import us.pepeyedu.SinkTheFloat.Utils.GameAudio;
import us.pepeyedu.SinkTheFloat.Windows.Windows;

public class LoadingScreen extends Screen {
	private int contentLoaded = 0;
	private int toLoad = 7;
	private int loaded = 0;
	private int leavetime = 0;
	private BufferedImage logoTexture;
	public LoadingScreen(Windows windows, Game game) {
		super(windows, game);
		new Thread() {
			@Override
			public void run() {				
				try {
					logoTexture= ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/logo.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				toLoad += TexturePath.values().length;
				try {
					sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for (TexturePath tp : TexturePath.values()) {
					Texture tex = new Texture(tp.getName(), tp.getPath());
					SinkTheFloat.getInstance().getTextureManager().registerTexture(tex);
					if (tp.isBoat()) {
						tex.createTextureChanged("normal", new TextureChanged() {
							@Override
							public void change() {
								//changeColor(Color.BLACK, Color.WHITE);
							}				
						});
						tex.createTextureChanged("error", new TextureChanged() {
							@Override
							public void change() {
								changeColor(Color.BLACK, Color.RED);
							}				
						});
					}
					tex.load(new Callback<Boolean>() {
						@Override
						public void done(Boolean result, Exception ex) {
							if (result) {
								addToLoad();
							} else {
								System.out.println("Error al cargar " + tp.getName() + " " + ex.getMessage());
							}
						}							
					});				
				}
				getGame().getScreenManager().registerNewScreen("attack", new AttackScreen(game.getWindows(), game));
				addToLoad();
				getGame().getScreenManager().registerNewScreen("you", new YourBoatsScreen(game.getWindows(), game));
				addToLoad();
				getGame().getScreenManager().registerNewScreen("main", new MainScreen(game.getWindows(), game));
				addToLoad();
				getGame().getScreenManager().registerNewScreen("transaction", new TransactionScreen(game.getWindows(), game));
				addToLoad();
				getGame().getScreenManager().registerNewScreen("rules", new RulesScreen(game.getWindows(), game));
				addToLoad();
				getGame().getScreenManager().registerNewScreen("win", new WinLoseScreen(game.getWindows(), game));
				addToLoad();
				SinkTheFloat.getInstance().getFontManager().registerFont("Airborne", "Airborne.ttf");
				addToLoad();
			}
		}.start();
	}
	private void addToLoad() {
		contentLoaded++;
		loaded = contentLoaded * 100 / toLoad;		
	}
	@Override
	public void internalTick() {
		if (loaded == 100) {
			leavetime++;
			if (leavetime >= getGame().getMaxTPS() * 2) {
				getGame().setScreen("main");
				getGame().setSoundTrack(new GameAudio("fondo.wav") {
					@Override
					public void onStart() {
						setVolume(0.05F);
					}
					@Override
					public void onFinish() {
						run();
					}
				});
			}
		}
	}
	@Override
	protected void paintLevel(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(0, 0, getWindows().getX(), getWindows().getY());
		g.setColor(loaded != 100 ? Color.WHITE : Color.GREEN);
		g.drawRect(getWindows().getX() / 4, (int) (getWindows().getY() / 1.5), getWindows().getX() / 2, 50);
		g.setColor(loaded != 100 ? Color.WHITE : Color.GREEN);
		int maxLoad = (getWindows().getX() / 2) - 8;
		int calc = (maxLoad * loaded) / 100;
		g.fillRect((getWindows().getX() / 4) + 4, (int) (getWindows().getY() / 1.5) + 4, calc, 50 - 8);
		g.setColor(loaded != 100 ? Color.WHITE : Color.GREEN);
		g.setFont(new Font("Aria", Font.PLAIN, 20));
		g.drawString(loaded != 100 ? (loaded + "%") : "100% :-)", 565, 630);
		g.drawImage(logoTexture, 375, 75, 450, 450, null, null);
	}
}
