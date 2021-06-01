package analogsnigs.game.scene;

import analogsnigs.game.Game;
import analogsnigs.game.editor.GameEditor;
import analogsnigs.game.gameobjects.GameObject;
import analogsnigs.game.menu.MenuPanel;
import analogsnigs.game.menu.TextInputField;
import analogsnigs.game.menu.UIElement;

public class Menu implements Scene {

    MenuPanel panel;

    public Menu() {
       panel = new MenuPanel();
       UIElement nameInputField = new TextInputField(0.5f, 0.5f, 0, (int)(Game.WALL_SIZE * 2.5), Game.WALL_SIZE * 7, Game.WALL_SIZE, 10);
       panel.addButton(0.5f, 0.5f, 0, (int)(Game.WALL_SIZE * 1.25), Game.WALL_SIZE * 2, Game.WALL_SIZE, "", this::connectToServer, "PLAY")
            .addLinkedElement(nameInputField);
       panel.addButton(0.5f, 0.5f, 0, 0, 4 * Game.WALL_SIZE, Game.WALL_SIZE, "", this::loadMapEditor, "CREATE MAP")
            .addLinkedElement(nameInputField);
    }

    @Override
    public void run() {
        panel.update();
    }

    @Override
    public void quit() {

    }

    @Override
    public int[] getOffset() {
        return new int[]{0,0};
    }

    public void connectToServer(String userName) {
        GameObject.resetDrawableObjects();
        UIElement.resetTextElements();
        Game.currentScene = new GameScene(userName);
    }

    public void loadMapEditor(String name) {
        GameObject.resetDrawableObjects();
        UIElement.resetTextElements();
        Game.currentScene = new GameEditor(name);
    }

    public void reload() {
        panel.reload();
    }



}
