package analogsnigs.game.editor;

import analogsnigs.game.Game;
import analogsnigs.game.gameobjects.Barrier;
import analogsnigs.game.menu.Button;
import analogsnigs.game.player.Character;
import analogsnigs.game.gameobjects.GameObject;
import analogsnigs.game.menu.MenuPanel;
import analogsnigs.game.menu.UIElement;
import analogsnigs.game.player.Player;
import analogsnigs.game.scene.Scene;
import analogsnigs.game.utilities.Constants;
import analogsnigs.game.utilities.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class GameEditor implements Scene {

    int mapSize = Constants.WALL_SIZE;

    Map map = new Map(new int[mapSize][mapSize]);

    MenuPanel panel;
    MenuPanel mapEditor;
    MenuPanel eventEditor;
    MenuPanel objectEditor;
    MenuPanel panelEditor;

    MenuPanel editorPanel;

    Player player;

    public int brush = 1;

    public GameEditor(String name) {
        player = new Player(new Character(mapSize* Constants.WALL_SIZE / 2, mapSize* Constants.WALL_SIZE / 2, Constants.WALL_SIZE, Constants.WALL_SIZE, name, (int) Math.round(Math.random() * 360)));
        map.setMap(new int[mapSize][mapSize], true);
        panel = new MenuPanel();
        mapEditor = new MenuPanel();
        eventEditor = new MenuPanel();
        objectEditor = new MenuPanel();

        panel.addButton(new Button(0f, 1f, (int)(Constants.WALL_SIZE * 0.75), (int)(-Constants.WALL_SIZE * 0.75), Constants.WALL_SIZE, Constants.WALL_SIZE, ",", this::exit, "X"));
        panel.addButton(new Button(0f, 1f, (Constants.WALL_SIZE * 2), (int)(-Constants.WALL_SIZE * 0.75), Constants.WALL_SIZE, Constants.WALL_SIZE, " ", this::saveMap, "S"));
        panel.addButton(new Button(1f, 1f, (int)(-Constants.WALL_SIZE * 1.75), (int)(-Constants.WALL_SIZE * 0.75), Constants.WALL_SIZE * 3, Constants.WALL_SIZE, ",", this::setMapEditor, "Map"));
        panel.addButton(new Button(1f, 1f, (int)(-Constants.WALL_SIZE * 1.75), -Constants.WALL_SIZE * 2, Constants.WALL_SIZE * 3, Constants.WALL_SIZE, " ", this::setEventEditor, "Events"));
        panel.addButton(new Button(1f, 1f, (int)(-Constants.WALL_SIZE * 1.75), (int)(-Constants.WALL_SIZE * 3.25), Constants.WALL_SIZE * 3, Constants.WALL_SIZE, " ", this::setObjectEditor, "Objects"));
        panel.addButton(new Button(1f, 1f, (int)(-Constants.WALL_SIZE * 1.75), (int)(-Constants.WALL_SIZE * 3.25), Constants.WALL_SIZE * 3, Constants.WALL_SIZE, " ", this::setObjectEditor, "Objects"));

        mapEditor.addButton(new Button(0f, 0f, (int)(Constants.WALL_SIZE * 0.75), (int)(Constants.WALL_SIZE * 0.75), Constants.WALL_SIZE, Constants.WALL_SIZE, "0", this::setBrush, "E"));
        mapEditor.addButton(new Button(0f, 0f, Constants.WALL_SIZE * 2,    (int)(Constants.WALL_SIZE * 0.75), Constants.WALL_SIZE, Constants.WALL_SIZE, "1", this::setBrush, "F"));
        mapEditor.addButton(new Button(0f, 0f, (int)(Constants.WALL_SIZE * 3.25), (int)(Constants.WALL_SIZE * 0.75), Constants.WALL_SIZE, Constants.WALL_SIZE, "2", this::setBrush, "W"));

        eventEditor.addButton(new Button(0f, 0f, (int)(Constants.WALL_SIZE * 0.75), (int)(Constants.WALL_SIZE * 0.75), Constants.WALL_SIZE, Constants.WALL_SIZE, "0", this::setBrush, "+"));
        eventEditor.addButton(new Button(0f, 0f, Constants.WALL_SIZE * 2, (int)(Constants.WALL_SIZE * 0.75), Constants.WALL_SIZE, Constants.WALL_SIZE, "1", this::setBrush, ""));
        eventEditor.addButton(new Button(0f, 0f, (int)(Constants.WALL_SIZE * 3.25), (int)(Constants.WALL_SIZE * 0.75), Constants.WALL_SIZE, Constants.WALL_SIZE, "2", this::setBrush, ""));

        editorPanel = mapEditor;

        eventEditor.delete();
        objectEditor.delete();

        new Barrier(-Constants.WALL_SIZE, -Constants.WALL_SIZE, Constants.WALL_SIZE, mapSize * Constants.WALL_SIZE + 100);
        new Barrier(-Constants.WALL_SIZE, 0, mapSize * Constants.WALL_SIZE + 100, Constants.WALL_SIZE);
        new Barrier(mapSize * Constants.WALL_SIZE , -Constants.WALL_SIZE, Constants.WALL_SIZE, mapSize * Constants.WALL_SIZE + 100);
        new Barrier(-Constants.WALL_SIZE, mapSize * Constants.WALL_SIZE + Constants.WALL_SIZE, mapSize * Constants.WALL_SIZE + 100, Constants.WALL_SIZE);
    }


    @Override
    public void run() {
        player.update();
        Character.moveCharacters();
        GameObject.sort();
        panel.update();
        editorPanel.update();
        if(editorPanel == mapEditor) {
            draw();
        }
    }

    @Override
    public void quit() {
        GameObject.resetObjects();
        UIElement.resetTextElements();
        Game.loadMenu();
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

    public void saveMap(String m) {
        Gdx.app.getClipboard().setContents(map.getMapAsString());
    }

    public void setBrush(String m) {
        brush = Integer.parseInt(m);
    }

    public void draw() {
        if(Game.INPUT.isButtonPressed(Input.Buttons.LEFT)) {
            int[] offset  = getOffset();
            int xClick = (Gdx.input.getX() - offset[0]) / Constants.WALL_SIZE;
            int yClick = ((Gdx.graphics.getHeight() - Gdx.input.getY()) - offset[1]) / Constants.WALL_SIZE;

            map.setPos(xClick, yClick, brush);
        }
    }

    public void setMapEditor(String s) {
        editorPanel.delete();
        editorPanel = mapEditor;
        editorPanel.reload();
    }

    public void setEventEditor(String s) {
        editorPanel.delete();
        editorPanel = eventEditor;
        editorPanel.reload();
    }

    public void setObjectEditor(String s) {
        editorPanel.delete();
        editorPanel = objectEditor;
        editorPanel.reload();
    }
}
