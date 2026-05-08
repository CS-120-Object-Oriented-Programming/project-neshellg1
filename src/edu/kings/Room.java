package edu.kings;
import java.util.HashMap;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "Campus of Kings" application. "Campus of Kings" is a
 * very simple, text based adventure game.
 *
 * A "Room" represents one location in the scenery of the game. It is connected
 * to other rooms via doors. The doors are labeled north, east, south, west.
 * For each direction, the room stores a reference to an instance of door.
 *
 * @author Maria Jump
 * @version 2015.02.01
 *
 * Used with permission from Dr. Maria Jump at Northeastern University
 */
public class Room {
	/** Counter for the total number of rooms created in the world. */
	private static int counter;
	/** The name of this room.  Room names should be unique. */
	private String name;
	/** The description of this room. */
	private String description;
	/** The exits of this room, mapping direction strings to Doors. */
    private HashMap<String, Door> exits;
 // already imported — add this field:
    private HashMap<String, Item> items;
	
	/**
	 * Static initializer.
	 */
	static {
		counter = 0;
	}
	
	/**
	 * Create a room described "description". Initially, it has no exits.
	 * "description" is something like "a kitchen" or "an open court yard".
	 *
	 * @param name  The room's name.
	 * @param description
	 *            The room's description.
	 */
	public Room(String name, String description) {
		this.name = name;
		this.description = description;
		exits = new HashMap<>();
		items = new HashMap<>();
		counter++;
	}

	/**
	 * Returns the name of this room.
	 *
	 * @return The name of this room.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the description of this room.
	 *
	 * @return The description of this room.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Returns the number of rooms that have been created in the world.
	 * @return The number of rooms that have been created in the world.
	 */
	public static int getCounter() {
		return counter;
	}
	
	
	
	/**
     * Defines an exit from this room.
     *
     * @param direction The direction of the exit.
     * @param neighbor  The door in the given direction.
     */
    public void setExit(String direction, Door neighbor) {
        exits.put(direction, neighbor);
    }

    /**
     * Gets a door in a specified direction if it exists.
     *
     * @param direction The direction to check.
     * @return The door in the specified direction or null if it does not exist.
     */
    public Door getExit(String direction) {
        return exits.get(direction);
    }
    
    /**
     * Adds an item to this room.
     *
     * @param item The item to add.
     */
    public void addItem(Item item) {
        items.put(item.getName().toLowerCase(), item);
    }
    
    /**
     * Gets an item from this room by name.
     *
     * @param name The name of the item to find.
     * @return The item, or null if no item with that name exists.
     */
    public Item getItem(String name) {
        return items.get(name.toLowerCase());
    }
    
    /**
     * Removes an item from this room by name.
     *
     * @param name The name of the item to remove.
     * @return The removed item, or null if no item with that name exists.
     */
    public Item removeItem(String name) {
        return items.remove(name.toLowerCase());
    }
    
    /**
     * Returns a string description including all the details of a Room.
     *
     * @return A string representing all the details of a Room.
     */
    public String toString() {
        String result = name + ":\n";
        result += "  " + description + "\n";
        result += "  Exits:";
        for (String direction : exits.keySet()) {
            result += " " + direction;
        }
        if (!items.isEmpty()) {
            result += "\n  Items:";
            for (String itemName : items.keySet()) {
                result += " " + itemName;
            }
        }
        return result;
    }
	
}
