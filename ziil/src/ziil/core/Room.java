package ziil.core;

import java.util.HashMap;
import java.util.Set;

/**
 * Based on the "World of Zuul" application by Michael Kolling and David J. Barnes.
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Manuel Allenspach
 */

public class Room 
{
    private final String description;
    private final HashMap<AbsoluteDirection, Room> exits;
    private boolean isEndRoom;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<AbsoluteDirection, Room>();
        isEndRoom = false;
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(AbsoluteDirection direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
    
    /**
     * Defines that this room is an exit. Entering this room wins the game.
     */
    public void setEndRoom() {
    	isEndRoom = true;
    }
    
    /**
     * Checks if the room is an end room (exit).
     * @return True if this room is an end/exit.
     */
    public boolean isEndRoom() {
    	return isEndRoom;
    }

    /**
     * Gets the description for this room.
     * @return The description.
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * Returns all exits for this room. This includes the one that leads back in the previous room
     * @return All exits.
     */
    public Set<AbsoluteDirection> getExits() {
    	return exits.keySet();
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(AbsoluteDirection direction) 
    {
        return exits.get(direction);
    }
}

