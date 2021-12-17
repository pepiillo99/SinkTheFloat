package us.pepeyedu.SinkTheFloat.Game.Objects.Boats;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import us.pepeyedu.SinkTheFloat.SinkTheFloat;
import us.pepeyedu.SinkTheFloat.Game.Game;
import us.pepeyedu.SinkTheFloat.Textures.Texture;
import us.pepeyedu.SinkTheFloat.Utils.CardinalPoints;
import us.pepeyedu.SinkTheFloat.Utils.GameLocation;
import us.pepeyedu.SinkTheFloat.Utils.GameObject;
import us.pepeyedu.SinkTheFloat.Utils.ObjectDimension;
import us.pepeyedu.SinkTheFloat.Utils.Utils;

public abstract class Boat extends GameObject {
	private int id = 0;
	private String name;
	private int distance = 0;
	private Texture[] textures;
	private String boatColor = "normal";
	private int matrizX = -1;
	private int matrizY = -1;
	private CardinalPoints rotation = CardinalPoints.NORTH;
	private GameLocation lastLocation;
	private long restartColorTime = 0;
	private boolean isEnemyBoat = false;
	private int health = 0;
	public Boat(int id, int distance, String name, GameLocation gameLocation, Game game) {
		super(gameLocation, game, new ObjectDimension(50, 50));
		this.id = id;
		this.distance = distance;
		this.name = name;
		this.textures = new Texture[distance];
		lastLocation = gameLocation;
		for (int i = 0; i < distance; i++) {
			textures[i] = SinkTheFloat.getInstance().getTextureManager().getTexture(name + (i != 0 ? i : ""));
		}
		changeBoatColor("normal");
		health = distance;
	}
	public Boat(int id, int distance, String name, GameLocation gameLocation, Game game, boolean isEnemyBoat) {
		super(gameLocation, game, new ObjectDimension(50, 50));
		this.id = id;
		this.distance = distance;
		this.name = name;
		this.textures = new Texture[distance];
		this.isEnemyBoat = isEnemyBoat;
		lastLocation = gameLocation;
		for (int i = 0; i < distance; i++) {
			textures[i] = SinkTheFloat.getInstance().getTextureManager().getTexture(name + (i != 0 ? i : ""));
		}
		changeBoatColor("normal");
		health = distance;
	}
	@Override
	public void tick() {
		if (restartColorTime != 0) {
			if (restartColorTime - System.currentTimeMillis() <= 0) {
				changeBoatColor("normal");
			}
		}
	}
	@Override
	public void render(Graphics g) {
		if (!isEnemyBoat) {
			for (int i = 0; i < textures.length; i++) {
				GameLocation posCalculateed = rotation.calcPos(getX(), getY(), i, getDimension());
				if (textures[i] != null && textures[i].isLoaded()) {
					if (boatColor != null && textures[i].hasChangedTexture(boatColor)) {
						g.drawImage(rotateImageByDegrees(textures[i].getTextureChanged(boatColor).getTexture(), rotation.getDegree()), posCalculateed.getX(), posCalculateed.getY(), getDimension().getX(), getDimension().getY(), null, null);
					} else {
						g.drawImage(rotateImageByDegrees(textures[i].getTexture(), rotation.getDegree()), posCalculateed.getX(), posCalculateed.getY(), getDimension().getX(), getDimension().getY(), null, null);
						}
				} else {
					if (Utils.random.nextBoolean()) {
						g.setColor(Color.white);
					} else {
						g.setColor(Color.pink);
					}
					g.fillRect(posCalculateed.getX(), posCalculateed.getY(), getDimension().getX(), getDimension().getY());
				}
			}
		}
	}
	public int getID() {
		return id;
	}
	public int getDistance() {
		return distance;
	}
	public String getName() {
		return name;
	}
	public boolean isEnemyBoat() {
		return isEnemyBoat;
	}
	public int getHealth() {
		return health;
	}
	public boolean isSink() {
		return health <= 0;
	}
	public void attack() {
		health--;
	}
    private BufferedImage rotateImageByDegrees(BufferedImage img, double angle) {
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;
        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        return rotated;
    }
    public String getBoatColor() {
    	return boatColor;
    }
    public void changeBoatColor(String name) {
    	this.boatColor = name;
    }
	public CardinalPoints getRotation() {
		return rotation;
	}
	public void rotate(CardinalPoints cp) {
		rotation = cp;
	}
	public int getMatrizX() {
		return matrizX;
	}
	public int getMatrizY() {
		return matrizY;
	}
	public boolean isOnTable() {
		return matrizX != -1 && matrizY != -1;
	}
	public void setMatrizPosition(int x, int y) {
		this.matrizX = x;
		this.matrizY = y;
	}
	public List<GameLocation> getBoatPositions(int matrizX, int matrizY) {
		List<GameLocation> gameLocations = new ArrayList<GameLocation>();
		if (matrizX != -1 && matrizY != -1) {
			for (int i = 0; i < distance; i++) {
				gameLocations.add(rotation.calcPos(matrizX, matrizY, i, new ObjectDimension(1, 1)));
			}
		}
		return gameLocations;
	}
	public GameLocation getLastLocation() {
		return lastLocation;
	}
	public void setLastLocation(GameLocation lastLocation) {
		this.lastLocation = lastLocation;
	}
	public long getRestartColorTimer() {
		return restartColorTime;
	}
	public void setRestartColorTimer(long timer) {
		this.restartColorTime = timer;
	}
}
