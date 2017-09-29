package ziil.core;

import java.awt.Point;

/**
 * Represents an absolute direction, such as north or east
 * @author Manuel
 *
 */
public enum AbsoluteDirection {
	NORTH(0),
	EAST(1),
	SOUTH(2),
	WEST(3);
	
	private final int index;
	
	private AbsoluteDirection(int index) {
		this.index = index;
	}
	
	/**
	 * Gets the index for this direction.
	 * It is zero-based beginning from north, then east, south, west
	 * @return The zero-based index
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * Calculates the direction between points.
	 * The two points must neighbour each other, otherwise an exception is thrown
	 * Invalid directions, such as north-west, also throw an exception
	 * @param point1
	 * @param point2
	 * @return
	 */
	public static AbsoluteDirection fromPoint(Point point1, Point point2) {
		int diffX = (int)(point1.getX() - point2.getX());
		int diffY = (int)(point1.getY() - point2.getY());
		
		if (diffX == 0) {
			return diffY < 0 ? AbsoluteDirection.SOUTH : AbsoluteDirection.NORTH;
		} else if (diffY == 0) {
			return diffX < 0 ? AbsoluteDirection.EAST : AbsoluteDirection.WEST;
		}
		
		throw new IllegalArgumentException("Can't get a direction from " + point1 + " to " + point2);
	}
	
	/**
	 * The String representation of this object
	 * @return The String representation
	 */
	@Override
	public String toString() {
		return this.name().toLowerCase();
	}
}
