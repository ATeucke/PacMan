package prog2.project5.enums;


/**
 * Represents the extra items that occur during the game.
 * 
 */
public enum ExtraItem {
	CHERRY(50), BANANA(100), ORANGE(200), STRAWBERRY(400);

	private final int points;

	private ExtraItem(int points) {
		this.points = points;

	}

	public int getPoints() {
		return points;
	}

}