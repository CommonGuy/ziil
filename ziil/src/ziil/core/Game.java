package ziil.core;

import java.util.Set;

/**
 * Based on the "World of Zuul" application by Michael Kolling and David J. Barnes.
 * 
 *  This is the main class. Here you can start a game.
 * 
 * @author Manuel Allenspach
 */

public class Game 
{
	private static final String GO_COMMAND = "go";
	private static final String QUIT_COMMAND = "quit";
	private static final String HELP_COMMAND = "help";
    private Parser parser;
    private Room currentRoom;
    private AbsoluteDirection currentDirection;
    
    public static void main(String[] args) {
    	new Game().play();
    }
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        currentDirection = AbsoluteDirection.NORTH;
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
    	RoomGenerator generator = new RoomGenerator();
    	currentRoom = generator.generateRooms(10);
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing. Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + HELP_COMMAND + "' if you need help.\n");
        System.out.println(getRoomDescription(currentRoom, currentDirection));
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean finished = false;

        String commandWord = command.getCommandWord();
        switch (commandWord) {
        	case "help":
	        	printHelp();
	        	break;
        	case "go":
        		finished = goRoom(command);
        		break;
        	case "quit":
        		finished = true;
        		break;
    		default:
                System.out.println("I don't know what you mean...");
        };

    	return finished;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are: " + GO_COMMAND + " " + QUIT_COMMAND + " " + HELP_COMMAND);
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private boolean goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return false;
        }

        AbsoluteDirection absDirection = getDirectionFromInput(command.getSecondWord());
        if (absDirection == null) {
            System.out.println("This isn't a valid direction!");
            return false;
        }
        
        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(absDirection);

        if (nextRoom == null) {
            System.out.println("There is no door!");
            return false;
        }
        
        if (nextRoom.isEndRoom()) {
        	System.out.println("Congratulations! You found the exit.");
        	return true;
        }

        currentRoom = nextRoom;
        currentDirection = absDirection;
        System.out.println(getRoomDescription(currentRoom, currentDirection));
        return false;
    }

    private String getRoomDescription(Room room, AbsoluteDirection direction)
    {
        String returnString = "You are " + room.getDescription() + ".\nExits:";
        Set<AbsoluteDirection> keys = room.getExits();
        for(AbsoluteDirection exit : keys) {
            returnString += " " + RelativeDirection.fromAbsoluteDirections(direction, exit).toString();
        }
        return returnString;
    }
    
    private AbsoluteDirection getDirectionFromInput(String input) {
    	RelativeDirection[] relativeDirections = RelativeDirection.values();
    	for (RelativeDirection relDirection : relativeDirections) {
    		if (relDirection.toString().equals(input)) {
    			return relDirection.toAbsoluteDirection(currentDirection);
    		}
    	}
    	return null;
    }
}
