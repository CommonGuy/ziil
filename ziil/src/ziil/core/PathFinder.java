package ziil.core;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import ziil.util.PriorityQueue;

/**
 * A class for finding the shortest path between two rooms
 * @author Manuel
 *
 */
public class PathFinder {
	private PriorityQueue<Room> roomsToCheck;
	private Set<Room> visitedRooms;
	
	/**
	 * Returns the length of the shortest path between two rooms, if such a path exists
	 * @param startRoom The starting room
	 * @param destinationRoom The destination room.
	 * @return The length of the shortest path, if one has been found.
	 */
	public Optional<Integer> calculateShortestPathLength(Room startRoom, Room destinationRoom) {
		roomsToCheck = new PriorityQueue<>();
		visitedRooms = new HashSet<>();
		
		roomsToCheck.enqueue(startRoom, 0);
	    do
	    {
	        Entry<Room, Integer> currentItem = roomsToCheck.dequeue();
	        Room currentRoom = currentItem.getKey();
	        int pathLength = currentItem.getValue();
	        
	        if (currentRoom == destinationRoom) {
	        	return Optional.of(pathLength);
	        }
	            		
	        visitedRooms.add(currentRoom);
	        visitNeighbours(currentRoom, pathLength);
	    } while (!roomsToCheck.isEmpty());

	    return Optional.empty();
	}
	
	private void visitNeighbours(Room room, int currentCost) {
	    for (AbsoluteDirection direction : room.getExits()) {
	    	Room nextRoom = room.getExit(direction);
	        if (visitedRooms.contains(nextRoom)) {
	        	continue;
	        }

        	int newCost = currentCost + 1;
	        roomsToCheck.enqueue(nextRoom, newCost);
	    }
	}
}
