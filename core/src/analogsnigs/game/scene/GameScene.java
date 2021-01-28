package analogsnigs.game.scene;

import analogsnigs.game.client.Client;
import analogsnigs.game.gameobjects.Character;
import analogsnigs.game.gameobjects.GameElement;
import analogsnigs.game.player.Player;

public class GameScene implements Scene{

    public Player player;

    private Client client;

    public GameScene() {
        new GameElement(200, 200, 1, 0, 1);
        player = new Player(new Character(0, 0, 45, 45, "Silas"));
        client = new Client();
    }

    @Override
    public void run() {
        player.update();
        client.sendCharacter();
    }

    @Override
    public void quit() {
        client.disconnect();
    }
}
