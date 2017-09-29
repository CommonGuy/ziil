package ziil.core;

/**
 * Represents a relative direction, such as left or right
 * @author Manuel
 *
 */
public enum RelativeDirection {
	BACK,
	LEFT,
	RIGHT,
	STRAIGHT;
	
	/**
	 * Calculates the relative direction between two absolute directions
	 * @param origin The direction you are coming from
	 * @param destination The direction you want to go to
	 * @return The relative direction between the absolute directions
	 */
	public static RelativeDirection fromAbsoluteDirections(AbsoluteDirection origin, AbsoluteDirection destination) {
		int diffIndex = origin.getIndex() - destination.getIndex();
		
		if (origin == destination) {
			return STRAIGHT;
		} else if (Math.abs(diffIndex) == 2) {
			return BACK;
		} else if (diffIndex == -1 || diffIndex == 3) {
			return RIGHT;
		} else {
			return LEFT;
		}
	}
	
	/**
	 * Calculates the absolute direction for a direction and the relative direction you want to go to
	 * @param currentDirection The originating direction
	 * @return The absolute direction to go to
	 */
	public AbsoluteDirection toAbsoluteDirection(AbsoluteDirection currentDirection) {
		if (this == STRAIGHT) {
			return currentDirection;
		} else if (this == BACK) {
			return getAbsoluteDirectionFromIndex(currentDirection.getIndex() + 2);
		} else if (this == RIGHT) {
			return getAbsoluteDirectionFromIndex(currentDirection.getIndex() + 1);
		} else {
			return getAbsoluteDirectionFromIndex(currentDirection.getIndex() - 1);
		}
	}
	
	/**
	 * The String representation of this object
	 * @return The String representation
	 */
	@Override
	public String toString() {
		return this.name().toLowerCase();
	}
	
	private AbsoluteDirection getAbsoluteDirectionFromIndex(int index) {
		AbsoluteDirection[] allDirections = AbsoluteDirection.values();
		int warpedIndex = ((index % allDirections.length) + allDirections.length) % allDirections.length;
		
		for (AbsoluteDirection direction : allDirections) {
			if (direction.getIndex() == warpedIndex) {
				return direction;
			}
		}
		
		throw new IllegalStateException("Should never get here");
	}
}
