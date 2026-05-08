package edu.kings;
import java.util.HashMap;

public class Container extends Item {
	/**
	 * Class Container - an item that can hold other items.
	 *
	 * This class is part of the "Maze of Fred" application.
	 * A Container is a special kind of Item that can store other items inside it,
	 * like a backpack or a chest.
	 *
	
	 */
	    /** The items stored inside this container, mapped by lowercase name. */
	    private HashMap<String, Item> contents;
	 
	    /**
	     * Constructor for Container.
	     *
	     * @param name        The container's name.
	     * @param description The container's description.
	     * @param points      The container's point value.
	     * @param weight      The container's weight.
	     */
	    public Container(String name, String description, int points, double weight) {
	        super(name, description, points, weight);
	        contents = new HashMap<>();
	    }
	 
	    /**
	     * Adds an item to this container.
	     *
	     * @param item The item to add.
	     */
	    public void addItem(Item item) {
	        contents.put(item.getName().toLowerCase(), item);
	    }
	 
	    /**
	     * Removes the named item from this container and returns it.
	     *
	     * @param name The name of the item to remove.
	     * @return The removed item, or null if no item with that name exists.
	     */
	    public Item removeItem(String name) {
	        return contents.remove(name.toLowerCase());
	    }
	 
	    /**
	     * Gets an item from this container by name.
	     *
	     * @param name The name of the item to find.
	     * @return The item, or null if it is not in this container.
	     */
	    public Item getItem(String name) {
	        return contents.get(name.toLowerCase());
	    }
	 
	    /**
	     * Returns whether this container is empty.
	     *
	     * @return true if the container has no items, false otherwise.
	     */
	    public boolean isEmpty() {
	        return contents.isEmpty();
	    }
	 
	    /**
	     * Returns the complete description of this container including
	     * its name, description, weight, and a list of items inside it.
	     *
	     * @return A string representing all details of this container.
	     */
	    public String toString() {
	        String result = getName() + ": " + getDescription() + " (Weight: " + getWeight() + " lbs)";
	        if (contents.isEmpty()) {
	            result += "\n  The " + getName() + " is empty.";
	        } else {
	            result += "\n  The " + getName() + " contains:";
	            for (Item item : contents.values()) {
	                result += "\n    - " + item.getName();
	            }
	        }
	        return result;
	    }
}
