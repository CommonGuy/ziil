package ziil.core;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

/**
 * Generates a maze-like room structure
 * @author Manuel
 *
 */
public class RoomGenerator {
	private static final String[] ROOM_DESCRIPTIONS = { "in an empty room" };
	private static final Random random = new Random();

	/**
	 * Generates room in a maze-like structure.
	 * @param size The size of the maze (one length). The maze will have size*size rooms
	 * @return The starting room, which is in the middle of the maze
	 */
	public Room generateRooms(int size) {
		if (size < 5) {
			throw new IllegalArgumentException("Size " + size + " is way too low!");
		}
		
		Map<Room, Point> roomPositions = createRooms(size);
		Room startingRoom = getStartingRoom(roomPositions, size);
		
		Stack<Room> stack = new Stack<Room>();
		Set<Room> visitedRooms = new HashSet<>();
		
		Room currentRoom = startingRoom;
		
		while (visitedRooms.size() != roomPositions.size()) {
			visitedRooms.add(currentRoom);
			Room neighbour = getRandomUnvisitedNeighbour(currentRoom, roomPositions, visitedRooms);
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
		
		setEndRooms(roomPositions, size);
		
		return startingRoom;
	}
	
	private Map<Room, Point> createRooms(int size){
		Map<Room, Point> roomPositions = new HashMap<>();
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				Room room = new Room(getRandomRoomDescription());
				roomPositions.put(room, new Point(x, y));
			}
		}
		return roomPositions;
	}
	
	private Room getRandomUnvisitedNeighbour(Room room, Map<Room, Point> roomPositions, Set<Room> visitedRooms) {
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
	
	private Room getStartingRoom(Map<Room, Point> roomPositions, int size) {
		int middle = size / 2;
		return roomPositions
				.entrySet()
				.stream()
				.filter(roomEntry -> roomEntry.getValue().getX() == middle && roomEntry.getValue().getY() == middle)
				.findFirst()
				.get()
				.getKey();
	}
	
	private void setEndRooms(Map<Room,Point> roomPositions, int size) {
		int maxXY = size -1;
		for (Entry<Room, Point> roomPosition : roomPositions.entrySet()) {
			int x = (int)roomPosition.getValue().getX();
			int y = (int)roomPosition.getValue().getY();
			
			if (x == 0 || x == maxXY || y == 0 || y == maxXY) {
				roomPosition.getKey().setEndRoom();
			}
		}
	}
	
	private String getRandomRoomDescription() {
		int randomIndex = random.nextInt(ROOM_DESCRIPTIONS.length);
		return ROOM_DESCRIPTIONS[randomIndex];
	}
}
