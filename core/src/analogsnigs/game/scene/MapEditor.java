package analogsnigs.game.scene;

import analogsnigs.game.Game;
import analogsnigs.game.gameobjects.Barrier;
import analogsnigs.game.gameobjects.Character;
import analogsnigs.game.gameobjects.GameObject;
import analogsnigs.game.menu.MenuPanel;
import analogsnigs.game.menu.UIElement;
import analogsnigs.game.player.Player;
import analogsnigs.game.utilities.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.sun.org.apache.xpath.internal.axes.WalkerFactory;

public class MapEditor implements Scene {

    int mapSize = 50;

    Map map = new Map(new int[mapSize][mapSize]);

    MenuPanel panel;

    Player player;

    public int brush = 1;

    public MapEditor() {
        player = new Player(new Character(mapSize*Game.WALL_SIZE / 2, mapSize*Game.WALL_SIZE / 2, 45, 45, "Bob", (int) Math.round(Math.random() * 360)));
        panel = new MenuPanel();
        panel.addButton(0.05f, 0.94f, 50, 50, ",", this::exit, "X");
        panel.addButton(0.15f, 0.94f, 50, 50, " ", this::printMap, "S");
        panel.addButton(0.25f, 0.94f, 50, 50, "0 ", this::setBrush, "E");
        panel.addButton(0.35f, 0.94f, 50, 50, "1 ", this::setBrush, "F");
        panel.addButton(0.45f, 0.94f, 50, 50, "2 ", this::setBrush, "W");

        new Barrier(-50, -50, 50, mapSize * Game.WALL_SIZE + 100);
        new Barrier(-50, 0, mapSize * Game.WALL_SIZE + 100, 50);
        new Barrier(mapSize * Game.WALL_SIZE , -50, 50, mapSize * Game.WALL_SIZE + 100);
        new Barrier(-50, mapSize * Game.WALL_SIZE + 50, mapSize * Game.WALL_SIZE + 100, 50);
    }


    @Override
    public void run() {
        player.update();
        Character.moveCharacters();
        GameObject.sort();
        panel.update();
        draw();
    }

    @Override
    public void quit() {
        GameObject.resetObjects();
        UIElement.resetTextElements();
        Game.currentScene = new Menu();
    }

    @Override
    public int[] getOffset() {
        return new int[]{
                (Gdx.graphics.getWidth() / 2) - Player.character.xPos - Player.character.width,
                (Gdx.graphics.getHeight() / 2) - Player.character.yPos - Player.character.height,
        };
    }

    public void exit(String m) {
        quit();
    }

    public void printMap(String m) {
        map.printMap();
    }

    public void setBrush(String m) {
        brush = Integer.parseInt(m);
    }

    public void draw() {
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            int[] offset  = getOffset();
            int xClick = (Gdx.input.getX() - offset[0]) / 50;
            int yClick = ((Gdx.graphics.getHeight() - Gdx.input.getY()) - offset[1]) / 50;

            map.setPos(xClick, yClick, brush);
        }
    }
}
