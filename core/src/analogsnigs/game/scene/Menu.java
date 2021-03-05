package analogsnigs.game.scene;

import analogsnigs.game.Game;
import analogsnigs.game.gameobjects.GameObject;
import analogsnigs.game.menu.MenuPanel;
import analogsnigs.game.menu.TextInputField;
import analogsnigs.game.menu.UIElement;

public class Menu implements Scene {

    MenuPanel panel;

    public Menu() {
       panel = new MenuPanel();
       panel.addButton(0.5f, 0.7f, 100, 50, "", this::connectToServer, "PLAY")
            .addLinkedElement(new TextInputField(0.5f, 0.9f, 350, 50, 10));
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


}
