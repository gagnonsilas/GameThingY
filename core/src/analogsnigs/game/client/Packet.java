package analogsnigs.game.client;
import analogsnigs.game.menu.MenuPanel;
import analogsnigs.game.minigame.Game;
import analogsnigs.game.player.Character;

public class Packet {
    public int id;
    public Object object;

    public Packet(int id, Object object) {
        this.id = id;
        this.object = object;
    }
}
