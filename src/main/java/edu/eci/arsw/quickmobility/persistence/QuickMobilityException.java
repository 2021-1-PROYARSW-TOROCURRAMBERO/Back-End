package edu.eci.arsw.quickmobility.persistence;

public class QuickMobilityException extends Exception {
    public final static String USERNAME_NOT_FOUND = "The user that you want to enter is not available";

    public final static String CAR_NOT_FOUND = "The car selected is not available";

    public final static String INVALID_CAR = "The car selected is invalid";

    public final static String INVALID_RATING = "The value of rating is invalid";

    public final static String UNIVERSITY_NOT_FOUND = "The university selected is not available";

    public final static String INVALID_UNIVERSITY = "The university is invalid";

    public final static String PASANGER_NOT_FOUND = "The passanger selected is not available";

    public final static String DRIVER_NOT_FOUND = "The driver selected is not available";

    public QuickMobilityException(String message){
        super(message);
    }
}