package analogsnigs.game.scene;

import analogsnigs.game.Game;
import analogsnigs.game.client.Client;
import analogsnigs.game.gameobjects.Character;
import analogsnigs.game.gameobjects.GameObject;
import analogsnigs.game.menu.MenuPanel;
import analogsnigs.game.menu.UIElement;
import analogsnigs.game.player.Player;
import analogsnigs.game.utilities.Collider;
import com.badlogic.gdx.Gdx;

public class GameScene implements Scene{

    public Player player;

    private final Client client;

    private final MenuPanel panel;

    public GameScene(String name) {
        // Create characters client instance and menus
        player = new Player(new Character(100, 100, 45, 45, name, (int) Math.round(Math.random() * 360)));
        client = new Client(this);
        panel = new MenuPanel();
        panel.addButton(0.05f, 0.94f, Game.WALL_SIZE, Game.WALL_SIZE, ",", this::disconnect, "X");
        panel.addButton(0.15f, 0.94f, Game.WALL_SIZE, Game.WALL_SIZE, ",", client::interact, "I");
    }

    @Override
    public void run() {
        player.update();

        Character.moveCharacters();

        Collider.checkColliders();

        GameObject.sort();

        client.sendCharacter();

        client.updatePanel();

        panel.update();
    }

    @Override
    public void quit() {
        client.disconnect();
    }

    @Override
    public int[] getOffset() {
        return new int[]{
                (Gdx.graphics.getWidth() / 2) - Player.character.xPos - Player.character.width,
                (Gdx.graphics.getHeight() / 2) - Player.character.yPos - Player.character.height,
        };
    }

    public void disconnect(String data) {
        quit();
        GameObject.resetObjects();
        UIElement.resetTextElements();
        Game.loadMenu();
    }


}
