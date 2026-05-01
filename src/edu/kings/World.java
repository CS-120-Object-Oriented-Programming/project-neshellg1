package edu.kings;
import java.util.HashMap;

/**
 * This class represents the entire world that makes up the "Campus of Kings"
 * application. "Campus of Kings" is a very simple, text based adventure game.
 * Users can walk around some scenery. That's all. It should really be extended
 * to make it more interesting!
 *
 * This world class creates the world where the game takes place.
 *
 * @author Maria Jump
 * @version 2015.02.01
 *
 * Used with permission from Dr. Maria Jump at Northeastern University
 */
public class World {
	/** The rooms in the world. */
	private HashMap<String, Room> rooms;

	/**
	 * Constructor for the world.
	 */
	public World() {
		rooms = new HashMap<String, Room>();
		createRooms();
	}

	/**
	 * This method takes care of creating all of the aspects of the world for
	 * the "Campus of Kings" application.
	 *
	 * @param name
	 *            The provided name of the room.
	 * @return The room associated with the provided name
	 */
	public Room getRoom(String name) {
		return rooms.get(name.toLowerCase());
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// Start of private helper methods

	/**
	 * Helper method for recreating a Room. Ensure that the room is created and
	 * installed in to the collection of Rooms
	 *
	 * @param theRoom
	 *            The room to add to the world.
	 */
	private void addRoom(Room theRoom) {
		rooms.put(theRoom.getName().toLowerCase(), theRoom);
	}

	/**
	 * Helper method for creating doors between rooms.
	 *
	 * @param from
	 *            The room where the door originates.
	 * @param north
	 *            The room to the north of the originating room.
	 */
	private void createDoor(Room from, String direction, Room to) {
		Door door = new Door(to);
		from.setExit (direction, door);
	}

	/**
	 * This method creates all of the individual places in this world and all
	 * the doors connecting them.
	 */
	private void createRooms() {
		// Creating all the rooms.
		Room entrance = new Room("Maze Entrance",
	            "You wake up at the entrance of a dark maze. The air is cold and damp. Paths lead north and east.");
		Room northCorridor = new Room("North Corridor",
	            "A long dark corridor stretches before you. You can hear dripping water.");
	    Room eastCorridor = new Room("East Corridor",
	            "A narrow passage. Something glints on the ground — it's a flashlight!");
	    Room centralChamber = new Room("Central Chamber",
	            "A large open chamber. Tunnels branch off in every direction. This must be the heart of the maze.");
	    Room trapRoom = new Room("Trap Room",
	            "You step on a loose stone — a trap! You lose your footing but recover. Dead end.");
	    Room northDeadEnd = new Room("North Dead End",
	            "A dead end. But wait — you spot a backpack against the wall!");
	    Room westPassage = new Room("West Passage",
	            "A dusty passage heading west. Cobwebs brush your face.");
	    Room hiddenAlcove = new Room("Hidden Alcove",
	            "A hidden alcove off the west passage. There's a map here — it shows the maze layout!");
	    Room southTunnel = new Room("South Tunnel",
	            "A downward sloping tunnel. It smells like mud. Another trap waits here.");
	    Room deepChamber = new Room("Deep Chamber",
	            "The deepest part of the maze. A compass and rope sit in the corner. You're close to the exit.");
	    Room exitTunnel = new Room("Exit Tunnel",
	            "You can see faint light ahead! Your friends are calling your name. The exit is near!");

		// Adding all the rooms to the world.
        addRoom(entrance);
        addRoom(northCorridor);
        addRoom(eastCorridor);
        addRoom(centralChamber);
        addRoom(trapRoom);
        addRoom(northDeadEnd);
        addRoom(westPassage);
        addRoom(hiddenAlcove);
        addRoom(southTunnel);
        addRoom(deepChamber);
        addRoom(exitTunnel);

     // From entrance
        createDoor(entrance, "north", northCorridor);
        createDoor(entrance, "east", eastCorridor);

        // North corridor connects to central chamber and a dead end
        createDoor(northCorridor, "south", entrance);
        createDoor(northCorridor, "east", centralChamber);
        createDoor(northCorridor, "north", northDeadEnd);

     // East corridor (flashlight) connects back and to central chamber
        createDoor(eastCorridor, "west", entrance);
        createDoor(eastCorridor, "north", centralChamber);


        // Central chamber — hub of the maze
        createDoor(centralChamber, "west", northCorridor);
        createDoor(centralChamber, "south", eastCorridor);
        createDoor(centralChamber, "east", trapRoom);
        createDoor(centralChamber, "north", westPassage);
        createDoor(centralChamber, "down", southTunnel);

     // Trap room — dead end
        createDoor(trapRoom, "west", centralChamber);

        // North dead end (backpack) — dead end
        createDoor(northDeadEnd, "south", northCorridor);

        // West passage to hidden alcove (map)
        createDoor(westPassage, "south", centralChamber);
        createDoor(westPassage, "west", hiddenAlcove);

        // Hidden alcove — dead end
        createDoor(hiddenAlcove, "east", westPassage);

        // South tunnel (trap) to deep chamber
        createDoor(southTunnel, "up", centralChamber);
        createDoor(southTunnel, "south", deepChamber);

        // Deep chamber (compass + rope) to exit
        createDoor(deepChamber, "north", southTunnel);
        createDoor(deepChamber, "east", exitTunnel);

     // Exit tunnel — win condition
        createDoor(exitTunnel, "west", deepChamber);
	}
}
