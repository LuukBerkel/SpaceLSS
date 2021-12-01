package Shared;

public class CommunicationLibrary {

    //region Communication
    //region Error handling
    public final static String COMMUNICATION_VERIFY_SERVER = "@Verify: I'm the server";

    public final static String COMMUNICATION_SESSION_ALREADY_CHOSEN_ERROR = "@Error: Already in use";
    public final static String COMMUNICATION_SESSION_COM_ERROR = "@Error: Com's are down";
    //endregion

    //region Game handling
    public final static String COMMUNICATION_SESSION_BOOT_USA = "@Answer: Boot USA";
    public final static String COMMUNICATION_SESSION_BOOT_USSR = "@Answer:  Boot USSR";

    public final static String COMMUNICATION_SESSION_REQUEST_USA = "@Request: USA";
    public final static String COMMUNICATION_SESSION_REQUEST_USSR = "@Request: USSR";

    public final static String COMMUNICATION_SESSION_REQUEST_POLE = "@Request: Pole";
    public final static String COMMUNICATION_SESSION_REQUEST_EQUATOR = "@Request: Equator";
    public final static String COMMUNICATION_SESSION_REQUEST_FIRST_PARALLEL = "@Request: First parallel";

    //endregion

    //endregion

    //region Internal
    public final static String GAME_INTERNAL_QUIT = "@Game: Quit";

    //region Keys
    public final static String KEYS_SUCCESSES_USA = "@Score: Success USA";
    public final static String KEYS_SUCCESSES_USSR = "@Score: Success USSR";
    public final static String KEYS_WASTED_USA = "@Score: Wasted USA";
    public final static String KEYS_WASTED_USSR = "@Score: Wasted USSR";
    public final static String KEYS_KILLED_USA = "@Score: Killed USA";
    public final static String KEYS_KILLED_USSR = "@Score: Killed USSR";
    //endregion
    //endregion



}
