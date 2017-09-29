package ziil.core;

import java.util.Scanner;

/**
 * Based on the "World of Zuul" application by Michael Kolling and David J. Barnes.
 * 
 * Reads input from the command line and returns them as commands.
 * 
 * @author  Manuel Allenspach
 */
public class Parser 
{
    private Scanner reader;         // source of command input

    /**
     * Create a parser to read from the terminal window.
     */
    public Parser() 
    {
        reader = new Scanner(System.in);
    }

    /**
     * @return The next command from the user.
     */
    public Command getCommand() 
    {
        String word1 = null;
        String word2 = null;

        System.out.print("> ");     // print prompt

        String inputLine = reader.nextLine();
        
        // Don't close this, otherwise we can't read from the console anymore
        Scanner tokenizer = new Scanner(inputLine);
        
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();
            }
        }

        return new Command(word1, word2);
    }
}