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
    //private int score;
    
    
	/**
	 * Create the game and initialize its internal map.
	 */
	public Game() {
		world = new World();
		player  = new Player (world.getRoom("maze entrance"));
		previousRoom = null;
		turns = 0;
		//score = 0;
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
            case TAKE:
                takeItem(command);
                break;
            case DROP:
                dropItem(command);
                break;
            case EXAMINE:
                examineItem(command);
                break;
            case INVENTORY:
                printInventory();
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
     * Drops an item from the player's inventory into the current room.
     *
     * @param command The command to be processed.
     */
    private void dropItem(Command command) {
        if (!command.hasSecondWord()) {
            Writer.println("Drop which item?");
        } else {
            String itemName = command.getRestOfLine();
            Item item = player.removeItem(itemName);
            if (item == null) {
                Writer.println("You don't have that item.");
            } else {
                player.getCurrentRoom().addItem(item);
                Writer.println("You dropped the " + item.getName() + ".");
            }
        }
    }
    
    /**
     * Examines an item in the room or in the player's inventory.
     *
     * @param command The command to be processed.
     */
    private void examineItem(Command command) {
        if (!command.hasSecondWord()) {
            Writer.println("Examine which item?");
        } else {
            String itemName = command.getRestOfLine();
            Item item = player.getItem(itemName);
            if (item == null) {
                item = player.getCurrentRoom().getItem(itemName);
            }
            if (item == null) {
                Writer.println("There is no such item.");
            } else {
                Writer.println(item.toString());
            }
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
     * Locks a door in the specified direction using an item from inventory.
     *
     * @param command The command to be processed.
     */
    private void lockDoor(Command command) {
        if (!command.hasSecondWord()) {
            Writer.println("Lock which direction?");
        } else {
            String direction = command.getRestOfLine();
            Door door = player.getCurrentRoom().getExit(direction);
            if (door == null) {
                Writer.println("There is no door in that direction.");
            } else if (door.isLocked()) {
                Writer.println("That door is already locked.");
            } else if (door.getRequiredKey() == null) {
                Writer.println("That door cannot be locked.");
            } else {
                String keyName = door.getRequiredKey();
                Item key = player.getItem(keyName);
                if (key == null) {
                    Writer.println("You need the " + keyName + " to lock that door.");
                } else {
                    door.setLocked(true);
                    Writer.println("You locked the door to the " + direction + ".");
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
     * Packs an item from the room or inventory into a container in the inventory.
     * Uses multistep command — prompts for which container and which item.
     *
     * @param command The command to be processed.
     */
    private void packItem(Command command) {
        if (!command.hasSecondWord()) {
            Writer.println("Pack which item?");
        } else {
            String itemName = command.getRestOfLine();
 
            // Find the item in the room or inventory
            Item item = player.getCurrentRoom().getItem(itemName);
            boolean fromRoom = item != null;
            if (item == null) {
                item = player.getItem(itemName);
            }
            if (item == null) {
                Writer.println("There is no such item.");
                return;
            }
            if (item instanceof Container) {
                Writer.println("You can't pack a container into itself.");
                return;
            }
            
            // Ask which container
            Writer.print("Into which container? ");
            String containerName = Reader.getResponse();
            Item containerItem = player.getItem(containerName);
            if (!(containerItem instanceof Container)) {
                Writer.println("You don't have a container called " + containerName + ".");
                return;
            }
 
            Container container = (Container) containerItem;
            if (fromRoom) {
                player.getCurrentRoom().removeItem(itemName);
            } else {
                player.removeItem(itemName);
            }
            container.addItem(item);
            Writer.println("You packed the " + item.getName() + " into the " + container.getName() + ".");
        }
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
        Writer.println("   take drop examine inventory");
        Writer.println("   lock unlock pack unpack");
	}
	
	/**
     * Prints the player's current inventory.
     */
    private void printInventory() {
        Writer.println(player.getInventoryString());
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
		   /**
	     * Takes an item from the current room and adds it to the player's inventory.
	     *
	     * @param command The command to be processed.
	     */
	    private void takeItem(Command command) {
	        if (!command.hasSecondWord()) {
	            Writer.println("Take what?");
	        } else {
	            String itemName = command.getRestOfLine();
	            Item item = player.getCurrentRoom().getItem(itemName);
	            if (item == null) {
	                Writer.println("There is no such item here.");
	            } else {
	                boolean taken = player.addItem(item);
	                if (!taken) {
	                    if (item.getWeight() > 20.0) {
	                        Writer.println("That item is too heavy to lift!");
	                    } else {
	                        Writer.println("You are carrying too much to pick that up.");
	                    }
	                } else {
	                    player.getCurrentRoom().removeItem(itemName);
	                    player.addScore(item.getPoints());
	                    Writer.println("You picked up the " + item.getName() + ".");
	                }
	            }
	        }
	    }
	        /**
	         * Unlocks a door in the specified direction using an item from inventory.
	         * Uses multistep command — prompts for which key to use.
	         *
	         * @param command The command to be processed.
	         */
	        private void unlockDoor(Command command) {
	            if (!command.hasSecondWord()) {
	                Writer.println("Unlock which direction?");
	            } else {
	                String direction = command.getRestOfLine();
	                Door door = player.getCurrentRoom().getExit(direction);
	                if (door == null) {
	                    Writer.println("There is no door in that direction.");
	                } else if (!door.isLocked()) {
	                    Writer.println("That door is not locked.");
	                } else {
	                    Writer.print("With what? ");
	                    String keyName = Reader.getResponse();
	                    Item key = player.getItem(keyName);
	                    if (key == null) {
	                        Writer.println("You don't have that item.");
	                    } else if (!keyName.equalsIgnoreCase(door.getRequiredKey())) {
	                        Writer.println("That doesn't seem to unlock this door.");
	                    } else {
	                        door.setLocked(false);
	                        Writer.println("You unlocked the door to the " + direction + ".");
	                    }
	                }
	            }
	        }
	     
	        /**
	         * Unpacks an item from a container in the player's inventory.
	         * Uses multistep command — prompts for which container to unpack from.
	         *
	         * @param command The command to be processed.
	         */
	        private void unpackItem(Command command) {
	            if (!command.hasSecondWord()) {
	                Writer.println("Unpack which item?");
	            } else {
	                String itemName = command.getRestOfLine();
	     
	                // Ask which container
	                Writer.print("From which container? ");
	                String containerName = Reader.getResponse();
	                Item containerItem = player.getItem(containerName);
	                if (!(containerItem instanceof Container)) {
	                    Writer.println("You don't have a container called " + containerName + ".");
	                    return;
	                }
	     
	                Container container = (Container) containerItem;
	                Item item = container.removeItem(itemName);
	                if (item == null) {
	                    Writer.println("There is no " + itemName + " in the " + container.getName() + ".");
	                } else {
	                    player.getCurrentRoom().addItem(item);
	                    Writer.println("You unpacked the " + item.getName() + " from the "
	                        + container.getName() + " and left it in the room.");
	                }
	            }
	        }
	    }
