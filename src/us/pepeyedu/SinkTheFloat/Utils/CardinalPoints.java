package us.pepeyedu.SinkTheFloat.Utils;

import java.util.ArrayList;
import java.util.List;

public enum CardinalPoints {
	NORTH(1, 0),
	EAST(2, 90),
	SOUTH(3, 180),
	WEST(4, 270);
	
	private int id;
	private int degree;
	
	CardinalPoints(int id, int degree) {
		this.id = id;
		this.degree = degree;
	}
	protected int getID() {
		return id;
	}
	public int getDegree() {
		return degree;
	}
	public CardinalPoints getContrary() {
		if (id + 2 < 4) {
			return CardinalPoints.getByID(id + 2);
		} else {
			if (id - 2 == 0) {
				return WEST;
			} else {
				return CardinalPoints.getByID(id - 2);
			}
		}
	}
	public static CardinalPoints getByID(int id) {
		for (CardinalPoints cp : values()) {
			if (cp.getID() == id) {
				return cp;
			}
		}
		return null;
	}
	public static CardinalPoints getRandomNotContains(List<CardinalPoints> cp) {
		List<CardinalPoints> cpp = new ArrayList<CardinalPoints>();
		for (CardinalPoints cppp : CardinalPoints.values()) {
			if (!cp.contains(cppp)) {
				cpp.add(cppp);
			}
		}
		if (cpp.isEmpty()) {
			return null;
		} else {
			return cpp.get(Utils.random.nextInt(cpp.size()));
		}
	}
	public CardinalPoints getNext() {
		for (CardinalPoints cp : values()) {
			if (cp.getID() == getID() + 1) {
				return cp;
			}
		}
		return NORTH;
	}
	public CardinalPoints getPrevious() {
		for (CardinalPoints cp : values()) {
			if (cp.getID() == getID() - 1) {
				return cp;
			}
		}
		return WEST;
	}
	public GameLocation calcPos(int x, int y, int actualDistance, ObjectDimension dimension) {
		switch (this) {
			case NORTH:
				return new GameLocation(x, y - (actualDistance * dimension.getY()));
			case EAST:
				return new GameLocation(x + (actualDistance * dimension.getX()), y);
			case SOUTH:
				return new GameLocation(x, y + (actualDistance * dimension.getY()));
			case WEST:
				return new GameLocation(x - (actualDistance * dimension.getX()), y);
			default:
				return null;
		}
	}
	public List<GameLocation> getMatrizRound(int x, int y, int actualDistance, int maxDistance) {
		List<GameLocation> sol = new ArrayList<GameLocation>();
		switch (this) {
		case NORTH:
			if (actualDistance == 0) { // principio
				sol.add(new GameLocation(x - 1, y - actualDistance + 1));
				sol.add(new GameLocation(x + 1, y - actualDistance + 1));
				sol.add(new GameLocation(x, y - actualDistance + 1));
			} else if (actualDistance == maxDistance - 1) { // final
				sol.add(new GameLocation(x - 1, y - actualDistance - 1));
				sol.add(new GameLocation(x + 1, y - actualDistance - 1));
				sol.add(new GameLocation(x, y - actualDistance - 1));
			}
			if (maxDistance == 1) {
				sol.add(new GameLocation(x - 1, y - actualDistance - 1));
				sol.add(new GameLocation(x + 1, y - actualDistance - 1));
				sol.add(new GameLocation(x, y - actualDistance - 1));
			}
			sol.add(new GameLocation(x - 1, y - actualDistance));
			sol.add(new GameLocation(x + 1, y - actualDistance));
			break;
		case SOUTH:
			if (actualDistance == 0) { // principio
				sol.add(new GameLocation(x + 1, y + actualDistance - 1));
				sol.add(new GameLocation(x - 1, y + actualDistance - 1));
				sol.add(new GameLocation(x, y + actualDistance - 1));
			} else if (actualDistance == maxDistance - 1) { // final
				sol.add(new GameLocation(x + 1, y + actualDistance + 1));
				sol.add(new GameLocation(x - 1, y + actualDistance + 1));
				sol.add(new GameLocation(x, y + actualDistance + 1));
			}
			if (maxDistance == 1) {
				sol.add(new GameLocation(x + 1, y + actualDistance + 1));
				sol.add(new GameLocation(x - 1, y + actualDistance + 1));
				sol.add(new GameLocation(x, y + actualDistance + 1));
			}
			sol.add(new GameLocation(x + 1, y + actualDistance));
			sol.add(new GameLocation(x - 1, y + actualDistance));
			break;
		case EAST:
			if (actualDistance == 0) {// principio
				sol.add(new GameLocation(x + actualDistance - 1, y - 1));
				sol.add(new GameLocation(x + actualDistance - 1, y + 1));
				sol.add(new GameLocation(x + actualDistance - 1, y));
			} else if (actualDistance == maxDistance - 1) { // final
				sol.add(new GameLocation(x + actualDistance + 1, y - 1));
				sol.add(new GameLocation(x + actualDistance + 1, y + 1));
				sol.add(new GameLocation(x + actualDistance + 1, y));
			}
			if (maxDistance == 1) {
				sol.add(new GameLocation(x + actualDistance + 1, y - 1));
				sol.add(new GameLocation(x + actualDistance + 1, y + 1));
				sol.add(new GameLocation(x + actualDistance + 1, y));
			}
			sol.add(new GameLocation(x + actualDistance, y - 1));
			sol.add(new GameLocation(x + actualDistance, y + 1));
			break;
		case WEST:
			if (actualDistance == 0) {// principio
				sol.add(new GameLocation(x - actualDistance + 1, y - 1));
				sol.add(new GameLocation(x - actualDistance + 1, y + 1));
				sol.add(new GameLocation(x - actualDistance + 1, y));
			} else if (actualDistance == maxDistance - 1) { // final
				sol.add(new GameLocation(x - actualDistance - 1, y - 1));
				sol.add(new GameLocation(x - actualDistance - 1, y + 1));
				sol.add(new GameLocation(x - actualDistance - 1, y));
			}
			if (maxDistance == 1) {
				sol.add(new GameLocation(x - actualDistance - 1, y - 1));
				sol.add(new GameLocation(x - actualDistance - 1, y + 1));
				sol.add(new GameLocation(x - actualDistance - 1, y));
			}
			sol.add(new GameLocation(x - actualDistance, y - 1));
			sol.add(new GameLocation(x - actualDistance, y + 1));
			break;
		}
		return sol;
	}
}
