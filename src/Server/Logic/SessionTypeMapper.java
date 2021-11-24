package Server.Logic;

public class SessionTypeMapper {
    public static SessionPlayerType mapPlayerType(String typeAsString) {
        if (typeAsString.equals("US")) return SessionPlayerType.USA;
        if (typeAsString.equals("USSR")) return SessionPlayerType.USSR;

        return null;
    }
}
