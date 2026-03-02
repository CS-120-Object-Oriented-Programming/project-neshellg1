package edu.kings;

/**
 * The Player class represents a player's character in the game.
 * It keeps track of the room that the player's character is currently in
 * and provides methods to retrieve and update that information.
 *
 * @author Neshell
 */
public class Player {

    /** The room the player is currently in. */
    private Room currentRoom;

    /**
     * Constructor that initializes the player with a starting room.
     *
     * @param startingRoom the room the player's character begins in
     */
    public Player(Room startingRoom) {
        this.currentRoom = startingRoom;
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
     * Mutator (setter) - Updates the room the player is currently in.
     *
     * @param newRoom the new room to move the player to
     */
    public void setCurrentRoom(Room newRoom) {
        this.currentRoom = newRoom;
    }
}
