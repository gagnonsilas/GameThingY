package analogsnigs.game.scene;

import analogsnigs.game.Game;
import analogsnigs.game.client.Client;
import analogsnigs.game.gameobjects.Character;
import analogsnigs.game.gameobjects.GameElement;
import analogsnigs.game.player.Player;
import com.badlogic.gdx.Gdx;

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
        Character.moveCharacters();
        client.sendCharacter();
    }

    @Override
    public void quit() {
        System.out.println("quit");
        client.disconnect();
    }

    @Override
    public int[] getOffset() {
        System.out.println(Gdx.graphics.getWidth() + ", " + Gdx.graphics.getHeight());
        return new int[]{
                (Gdx.graphics.getWidth() / 2) - Player.character.xPos - Player.character.width,
                (Gdx.graphics.getHeight() / 2) - Player.character.yPos - Player.character.height,
        };
    }
}
