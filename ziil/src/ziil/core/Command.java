package ziil.core;

/**
 * Based on the "World of Zuul" application by Michael Kolling and David J. Barnes.
 * 
 * Holds user input as a "command"
 * 
 * @author  Manuel Allenspach
 */

public class Command
{
    private String commandWord;
    private String secondWord;

    /**
     * Create a command object.
     * @param firstWord The first word of the command.
     * @param secondWord The second word of the command.
     */
    public Command(String firstWord, String secondWord)
    {
        commandWord = firstWord;
        this.secondWord = secondWord;
    }

    /**
     * Return the command word (the first word) of this command. If the
     * command was not understood, the result is null.
     * @return The command word.
     */
    public String getCommandWord()
    {
        return commandWord;
    }

    /**
     * @return The second word of this command. Returns null if there was no
     * second word.
     */
    public String getSecondWord()
    {
        return secondWord;
    }

    /**
     * @return true if the command has a second word.
     */
    public boolean hasSecondWord()
    {
        return (secondWord != null);
    }
}

