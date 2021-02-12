package analogsnigs.game.scene;

import analogsnigs.game.Game;
import analogsnigs.game.client.Client;
import analogsnigs.game.gameobjects.Character;
import analogsnigs.game.gameobjects.GameObject;
import analogsnigs.game.menu.MenuPanel;
import analogsnigs.game.menu.UIElement;
import analogsnigs.game.player.Player;
import com.badlogic.gdx.Gdx;

public class GameScene implements Scene{

    public Player player;

    private final Client client;

    private final MenuPanel panel;

    public GameScene(String name) {
        player = new Player(new Character(100, 100, 45, 45, name, (int) Math.round(Math.random() * 360)));
        client = new Client();
        panel = new MenuPanel();
        panel.addButton(0.05f, 0.94f, 50, 50, ",", this::disconnect, "X");
    }

    @Override
    public void run() {
        player.update();
        Character.moveCharacters();
        client.sendCharacter();
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
        GameObject.resetDrawableObjects();
        UIElement.resetTextElements();
        Game.currentScene = new Menu();
    }
}
