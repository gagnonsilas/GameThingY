package analogsnigs.game.scene;

import analogsnigs.game.Game;
import analogsnigs.game.editor.GameEditor;
import analogsnigs.game.gameobjects.GameObject;
import analogsnigs.game.menu.Button;
import analogsnigs.game.menu.MenuPanel;
import analogsnigs.game.menu.TextInputField;
import analogsnigs.game.menu.UIElement;
import analogsnigs.game.utilities.Constants;

public class Menu implements Scene {

    MenuPanel panel;

    public Menu() {
       panel = new MenuPanel();
       UIElement nameInputField = new TextInputField(0.5f, 0.5f, 0, (int)(Constants.WALL_SIZE * 2.5), Constants.WALL_SIZE * 7, Constants.WALL_SIZE, 20);
       panel.addButton(new Button(0.5f, 0.5f, 0, (int)(Constants.WALL_SIZE * 1.25), Constants.WALL_SIZE * 2, Constants.WALL_SIZE, "", this::connectToServer, "PLAY"))
            .addLinkedElement(nameInputField);
       panel.addButton(new Button(0.5f, 0.5f, 0, 0, 4 * Constants.WALL_SIZE, Constants.WALL_SIZE, "", this::loadMapEditor, "CREATE MAP"))
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
