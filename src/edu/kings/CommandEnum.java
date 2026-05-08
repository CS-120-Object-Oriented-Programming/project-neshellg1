package edu.kings;

public enum CommandEnum {
	GO("go"),
	HELP("help"),
    QUIT("quit"),
    LOOK("look"),
    SCORE("score"),
    TURNS("turns"),
    BACK("back"),
    STATUS("status"),
    TAKE("take"),
    DROP("drop"),
    EXAMINE("examine"),
    INVENTORY("inventory"),
    /** Lock a door. */
    LOCK("lock"),
    /** Unlock a door. */
    UNLOCK("unlock"),
    /** Pack an item into a container. */
    PACK("pack"),
    /** Unpack an item from a container. */
    UNPACK("unpack"),
    UNKNOWN("unknown");
    

	 /** The text representation of this command. */
    private final String myText;

    CommandEnum(String theText) {
        this.myText = theText;
    }

    public String getText() {
        return myText;
    }
}
