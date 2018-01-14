package com.jorged.hooversim.exception;

public class WrongConfigurationException extends Exception {

    public final static String MSG_STARTING_COORDS = "Starting point outside of room";
    public final static String MSG_PATCHES_COORDS = "Patches outside of room";
    public final static String MSG_INSTRUCTIONS = "Instructions not recognized";

    public WrongConfigurationException(String message) {
        super(message);
    }

}
