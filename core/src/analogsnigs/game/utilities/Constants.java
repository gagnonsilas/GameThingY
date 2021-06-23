package analogsnigs.game.utilities;

import java.time.format.DateTimeFormatter;

public class Constants {
    public static final int ERROR = -1;
    public static final int ADD_PLAYER = 0;
    public static final int SEND_PLAYER = 1;
    public static final int SEND_OBJECT = 2;
    public static final int SEND_CHAT = 3;
    public static final int SEND_GAME = 4;
    public static final int SEND_PANEL = 5;
    public static final int SET_POSITION = 6;
    public static final int INTERACT = 7;
    public static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    public static final int WALL_SIZE = 64;
}
