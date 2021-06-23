package analogsnigs.game.server;

import analogsnigs.game.utilities.Constants;
import org.java_websocket.WebSocket;
import analogsnigs.game.player.Character;

public class Player extends Character{

    public transient WebSocket socket;

    public Player(WebSocket socket) {
        this.socket = socket;
    }

    public boolean isCorrectSocket(WebSocket socket) {
        return this.socket.equals(socket);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void updatePlayer(int xPos, int yPos, int width, int height, int hue) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.hue = hue;
    }

    public String getPlayerInfo() {
        return(name + "," +
                xPos + "," +
                yPos + "," +
                width + "," +
                height + "," +
                hue);
    }

    public void sendData(String data) {
        socket.send(data);
    }

    public void teleport(int x, int y) {
        xPos = x;
        yPos = y;

        socket.send(Constants.SET_POSITION + getPlayerInfo());
    }
}
