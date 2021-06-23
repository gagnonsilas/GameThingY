package analogsnigs.game.minigame;


import analogsnigs.game.Game;
import analogsnigs.game.gameobjects.GameObject;
import analogsnigs.game.player.Character;
import analogsnigs.game.server.Player;
import analogsnigs.game.server.Server;
import com.badlogic.gdx.graphics.Color;

public class GamePlayObject extends GameObject {

    public int size;
    public int textureID;

    private final String event;

    public GamePlayObject(int xPos, int yPos, int size, int textureID, String event) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.size = size;
        this.width = size;
        this.height = size;

        this.textureID = textureID;
        this.event = event;

        this.color = Color.WHITE;

        drawingLayer = 2;

    }

    public boolean isColliding(Character player) {
        return(player.xPos <= xPos + size && xPos <= player.xPos + player.width && player.yPos <= yPos + size && yPos <= player.yPos + player.height);
    }

    public void interact(Player player) {
        if(isColliding(player)) {
            Server.game.runEvent(event, player);
        }
    }
}
