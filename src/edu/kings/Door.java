package edu.kings;
/**
 * Class Door - a door or portal between two Rooms in an adventure game.
 *
 * This class is part of the "Campus of Kings" application. "Campus of Kings" is a
 * very simple, text based adventure game.
 *
 * A "Door" represents a door or portal between two locations of the game.
 * It stores a reference to the neighboring room and whether that door
 * or portal is locked.  Doors are not locked by default.
 *
 * @author Maria Jump
 * @version 2015.02.01
 *
 * Used with permission from Dr. Maria Jump at Northeastern University
 */
public class Door {

	/** The room that this door leads to. */
	private Room destination;
	/** Whether this door is locked. */
	private boolean locked;
	/** The name of the item required to unlock this door. Null if no key needed. */
    private String requiredKey;
    
    
	/**
	 * Constructor for the Door class.
	 * @param destination The room this door leads to
	 */
	public Door(Room destination) {
		this.destination = destination;
		this.locked = false;
		this.requiredKey = null;
	}

	/**
	 * A getter for the room this door leads to.
	 * @return The room this door leads to
	 */
	public Room getDestination() {
		return destination;
	}

	/**
	 * A getter for whether this door is locked.
	 * @return Whether this door is locked
	 */
	public boolean isLocked() {
		return locked;
	}

	/**
	 * A setter for whether this door is locked.
	 * @param locked Whether this door is locked.
	 */
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	/**
     * Returns the name of the item required to unlock this door.
     *
     * @return The key item name, or null if no key is required.
     */
    public String getRequiredKey() {
        return requiredKey;
    }
 
    /**
     * Sets the key required to unlock this door and locks it.
     *
     * @param keyName The name of the item that unlocks this door.
     */
    public void setRequiredKey(String keyName) {
        this.requiredKey = keyName;
        this.locked = true;
    }
}
