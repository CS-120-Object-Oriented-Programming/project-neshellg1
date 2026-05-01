package edu.kings;
/**
 * This class is the main class of the "Campus of Kings" application.
 * "Campus of Kings" is a very simple, text based adventure game. Users can walk
 * around some scenery. That's all. It should really be extended to make it more
 * interesting!
 *
 * This game class creates and initializes all the others: it creates all rooms,
 * creates the parser and starts the game. It also evaluates and executes the
 * commands that the parser returns.
 *
 * @author Maria Jump
 * @version 2015.02.01
 *
 * Used with permission from Dr. Maria Jump at Northeastern University
 */

public class Game {
	/** The world where the game takes place. */
	private World world;
	/** The player character. */
	private Player player;
	/**The previous room the player was in. */
    private Room previousRoom;
   
    /** The number of turns the player has taken. */
    private int turns;
    /** The player's current score. */
    private int score;
    
    
	/**
	 * Create the game and initialize its internal map.
	 */
	public Game() {
		world = new World();
		player  = new Player (world.getRoom("maze entrance"));
		previousRoom = null;
		turns = 0;
		score = 0;
	}
	
	/**
	 * Main play routine. Loops until end of play.
	 */
	public void play() {
		printWelcome();
		// Enter the main game loop. Here we repeatedly read commands and
		// execute them until the game is over.
		boolean wantToQuit = false;
		while (!wantToQuit) {
			Command command = Reader.getCommand();
			wantToQuit = processCommand(command);
			turns++;
			
			// other stuff that needs to happen every turn can be added here.
		}
		printGoodbye();
	}

	///////////////////////////////////////////////////////////////////////////
	// Helper methods for processing the commands

	/**
	 * Given a command, process (that is: execute) the command.
	 *
	 * @param command
	 *            The command to be processed.
	 * @return true If the command ends the game, false otherwise.
	 */
	private boolean processCommand(Command command) {
		boolean wantToQuit = false;

		if (command.isUnknown()) {
			Writer.println("I don't know what you mean...");
		} else {

			CommandEnum commandWord = command.getCommandWord();
			switch (commandWord) {
            case HELP:
                printHelp();
                break;
            case GO:
                goRoom(command);
                break;
            case QUIT:
                wantToQuit = quit(command);
                break;
            case LOOK:
            	look();
                break;
            case SCORE:
                printScore();
                break;
            case TURNS:
                printTurns();
                break;
            case BACK:
                back();
                break;
            case STATUS:
                status();
                break;
            default:
                Writer.println(commandWord + " is not implemented yet!");
                break;
			}
		}
		return wantToQuit;
	}

	///////////////////////////////////////////////////////////////////////////
	// Helper methods for implementing all of the commands.
	// It helps if you organize these in alphabetical order.
	/**
     * Takes the player back to the previous room.
     */
    private void back() {
        if (previousRoom == null) {
            Writer.println("You cannot go back, there is no previous room!");
        } else {
            Room temp = player.getCurrentRoom();
            player.setCurrentRoom(previousRoom);
            previousRoom = temp;
            printLocationInformation();
        }
    }

	/**
	 * Try to go to one direction. If there is an exit, enter the new room,
	 * otherwise print an error message.
	 *
	 * @param command
	 *            The command to be processed.
	 */
	private void goRoom(Command command) {
		if (!command.hasSecondWord()) {
			// if there is no second word, we don't know where to go...
			Writer.println("Go where?");
		} else {
			String direction = command.getRestOfLine();

			// Try to leave current.
			//*
			Door doorway = player.getCurrentRoom().getExit(direction);
			
			if (doorway == null) {
				Writer.println("There is no door!");
			} else {
				previousRoom = player.getCurrentRoom();
				Room newRoom = doorway.getDestination();
				player.setCurrentRoom(newRoom);
				
				printLocationInformation();
				
				 // Check for win condition
                if (newRoom.getName().equals("Exit Tunnel")) {
                    Writer.println("You see light ahead and hear your friends calling!");
                    Writer.println("You have escaped the maze!");
                }
			}
		}
	}
	/**
     * Prints out the location information.
     */
    private void look() {
        printLocationInformation();
    }
	/**
	 * Print out the closing message for the player.
	 */
	private void printGoodbye() {
		Writer.println("You have earned " + player.getScore() + " points in " + turns + " turns.");
		Writer.println("Thanks for helping Fred find his way. Good bye!");
	}

	/**
	 * Print out some help information. Here we print some stupid, cryptic
	 * message and a list of the command words.
	 */
	private void printHelp() {
        Writer.println("Fred is lost in a dark maze.");
        Writer.println("Find his gear and escape to his friends!");
        Writer.println();
        Writer.println("Your command words are:");
        Writer.println("   go quit help look score turns back status");

	}
	
	/**
     * Prints out the current location information.
     */
    private void printLocationInformation() {
    	Writer.println(player.getCurrentRoom().toString()); 
    }
    /**
     * Prints the player's current score.
     */
    private void printScore() {
    	Writer.println("Your current score is: " + player.getScore());
    }
    /**
     * Prints the current number of turns.
     */
    private void printTurns() {
        Writer.println("You have taken " + turns + " turns.");
    }
	/**
	 * Print out the opening message for the player.
	 */
	private void printWelcome() {
	    Writer.println();
	    Writer.println("Welcome to The Maze of Fred!");
	    Writer.println("Fred is lost in a dark maze. Help him find his gear and escape!");
	    Writer.println("Type 'help' if you need help.");
		Writer.println();
		printLocationInformation();
	}

	/**
	 * "Quit" was entered. Check the rest of the command to see whether we
	 * really quit the game.
	 *
	 * @param command
	 *            The command to be processed.
	 * @return true, if this command quits the game, false otherwise.
	 */
	private boolean quit(Command command) {
		boolean wantToQuit = true;
		if (command.hasSecondWord()) {
			Writer.println("Quit what?");
			wantToQuit = false;
		}
		return wantToQuit;
	}

	    /**
	     * Prints the current status of the game including turns, score, and location.
	     */
		private void status() {
			printTurns();
	        printScore();
	        printLocationInformation();
	    }
}
