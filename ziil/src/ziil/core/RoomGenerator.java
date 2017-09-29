package ziil.core;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

/**
 * Generates a maze-like room structure
 * @author Manuel
 *
 */
public class RoomGenerator {
	private static final int MIN_SIZE = 2;
	private static final String[] ROOM_DESCRIPTIONS = { "in an empty room" };
	private static final Random random = new Random();
	private final int size;
	private Map<Room, Point> roomPositions;
	
	/**
	 * Creates a room generator
	 * @param size The size of the maze (one length). The maze will have size*size rooms
	 */
	public RoomGenerator(int size) {
		if (size < MIN_SIZE) {
			throw new IllegalArgumentException("Size " + size + " is too low!");
		}
		this.size = size;
	}

	/**
	 * Generates room in a maze-like structure.
	 * @return The starting room
	 */
	public Room generateRooms() {
		createRooms(size);
		
		Room startingRoom = getStartingRoom();
		Stack<Room> stack = new Stack<Room>();
		Set<Room> visitedRooms = new HashSet<>();
		
		Room currentRoom = startingRoom;
		
		while (visitedRooms.size() != roomPositions.size()) {
			visitedRooms.add(currentRoom);
			Room neighbour = getRandomUnvisitedNeighbour(currentRoom, visitedRooms);
			if (neighbour != null) {
				stack.push(currentRoom);
				Point currentPosition = roomPositions.get(currentRoom);
				Point nextPosition = roomPositions.get(neighbour);
				currentRoom.setExit(AbsoluteDirection.fromPoint(currentPosition, nextPosition), neighbour);
				neighbour.setExit(AbsoluteDirection.fromPoint(nextPosition, currentPosition), currentRoom);
				currentRoom = neighbour;
			} else if (!stack.isEmpty()) {
				currentRoom = stack.pop();
			}
		}
		
		setEndRoom();
		
		return startingRoom;
	}
	
	private void createRooms(int size){
		roomPositions = new HashMap<>();
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				Room room = new Room(getRandomRoomDescription());
				roomPositions.put(room, new Point(x, y));
			}
		}
	}
	
	private Room getRandomUnvisitedNeighbour(Room room, Set<Room> visitedRooms) {
		Point roomPosition = roomPositions.get(room);
		Room[] possibleRooms = roomPositions
				.entrySet()
				.stream()
				.filter(r -> isNeighbour(roomPosition, r.getValue()) && !visitedRooms.contains(r.getKey()))
				.map(r -> r.getKey())
				.toArray(size -> new Room[size]);
		
		if (possibleRooms.length == 0) {
			return null;
		}
		
		return possibleRooms[random.nextInt(possibleRooms.length)];
	}
	
	private boolean isNeighbour(Point point1, Point point2) {
		int diffX = (int)Math.abs(point1.getX() - point2.getX());
		int diffY = (int)Math.abs(point1.getY() - point2.getY());
		
		return (diffX == 1 && diffY == 0) || (diffY == 1 && diffX == 0);
	}
	
	private Room getStartingRoom() {
		return roomPositions
				.entrySet()
				.stream()
				.filter(roomEntry -> roomEntry.getValue().getX() == 0 && roomEntry.getValue().getY() == 0)
				.findFirst()
				.get()
				.getKey();
	}
	
	private void setEndRoom() {
		int maxXY = size - 1;
		
		Room endRoom = roomPositions
				.entrySet()
				.stream()
				.filter(roomEntry -> roomEntry.getValue().getX() == maxXY && roomEntry.getValue().getY() == maxXY)
				.findFirst()
				.get()
				.getKey();
		
		endRoom.setEndRoom();
	}
	
	private String getRandomRoomDescription() {
		int randomIndex = random.nextInt(ROOM_DESCRIPTIONS.length);
		return ROOM_DESCRIPTIONS[randomIndex];
	}
}
