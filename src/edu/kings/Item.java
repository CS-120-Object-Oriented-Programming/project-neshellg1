package edu.kings;

public class Item {
	/** The name of the item. */
    private String name;
    /** The description of the item. */
    private String description;
    /** The point value of the item. */
    private int points;
    /** The weight of the item. */
    private double weight;

    /**
     * Constructor for Item.
     * @param name The item's name.
     * @param description The item's description.
     * @param points The item's point value.
     * @param weight The item's weight.
     */
    public Item(String name, String description, int points, double weight) {
        this.name = name;
        this.description = description;
        this.points = points;
        this.weight = weight;
    }

    /** @return the name */
    public String getName() { 
    	return name; 
    	}

    /** @return the description */
    public String getDescription() { 
    	return description;
    	}

    /** @param description the new description */
    public void setDescription(String description) { 
    	this.description = description; 
    	}

    /** @return the point value */
    public int getPoints() { 
    	return points; 
    	}

    /** @return the weight */
    public double getWeight() { 
    	return weight; 
    	}
    
    /**
     * Returns the complete description of the item.
     * @return A string with name, description, and weight.
     */
    public String toString() {
        return name + ": " + description+ " (Weight: " + weight + " lbs)";
    }
}
