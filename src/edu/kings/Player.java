package edu.kings;
import java.util.HashMap;
/**
 * The Player class represents a player's character in the game.
 * It keeps track of the room that the player's character is currently in
 * and provides methods to retrieve and update that information.
 *
 */
public class Player {
	/** The maximum weight the player can carry. */
    private static final double MAX_WEIGHT = 20.0;
    
    /** The room the player is currently in. */
    private Room currentRoom;
    /** The player's current score. */
    private int score;

    /** The player's inventory, mapped by lowercase item name. */
    private HashMap<String, Item> inventory;	
    
    /**
     * Constructor that initializes the player with a starting room.
     *
     * @param startingRoom the room the player's character begins in
     */
    public Player(Room startingRoom) {
        this.currentRoom = startingRoom;
        this.score = 0;
        this.inventory = new HashMap<>();
    }

    /**
     * Accessor (getter) - Returns the room the player is currently in.
     *
     * @return the current room
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }
    /**
     * Accessor (getter) - Returns the player's current score.
     *
     * @return the current score
     */
    public int getScore() {
        return score;
    }

    /**
     * Mutator (setter) - Updates the room the player is currently in.
     *
     * @param newRoom the new room to move the player to
     */
    public void setCurrentRoom(Room newRoom) {
        this.currentRoom = newRoom;
    }
    /**
     * Mutator (setter) - Adds points to the player's score.
     *
     * @param points the number of points to add
     */
    public void addScore(int points) {
        this.score += points;
    }
    /**
     * Attempts to add an item to the player's inventory.
     * Returns false if the item alone exceeds the max weight,
     * or if adding it would exceed the current carry limit.
     *
     * @param item The item to add.
     * @return true if the item was successfully added, false otherwise.
     */
    public boolean addItem(Item item) {
        if (item.getWeight() > MAX_WEIGHT) {
            return false;
        }
        double currentWeight = 0;
        for (Item i : inventory.values()) {
            currentWeight += i.getWeight();
        }
        if (currentWeight + item.getWeight() > MAX_WEIGHT) {
            return false;
        }
        inventory.put(item.getName().toLowerCase(), item);
        return true;
    }
 
    /**
     * Gets an item from the player's inventory by name.
     *
     * @param name The name of the item.
     * @return The item, or null if not in inventory.
     */
    public Item getItem(String name) {
        return inventory.get(name.toLowerCase());
    }
 
    /**
     * Removes an item from the player's inventory by name.
     *
     * @param name The name of the item to remove.
     * @return The removed item, or null if not found.
     */
    public Item removeItem(String name) {
        return inventory.remove(name.toLowerCase());
    }
    /**
     * Returns a string listing all items in the player's inventory.
     *
     * @return A string representing the player's inventory.
     */
    public String getInventoryString() {
        if (inventory.isEmpty()) {
            return "You are not carrying anything.";
        }
        String result = "You are carrying:";
        for (Item item : inventory.values()) {
            result += "\n  " + item.toString();
        }
        return result;
    }
}
