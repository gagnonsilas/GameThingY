package analogsnigs.game.menu;

import analogsnigs.game.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.function.Consumer;


public class MenuPanel {

    Array<UIElement> uiElements = new Array<>();
    Array<Button> buttons = new Array<>();
    boolean hasBackgroundPanel;

    public MenuPanel(boolean hasBackgroundPanel) {
        this.hasBackgroundPanel = hasBackgroundPanel;
        if(hasBackgroundPanel) {
            uiElements.add(new UIGraphicElement(
                    0.5f,
                    0.5f,
                    8 * Game.WALL_SIZE,
                    8 * Game.WALL_SIZE,
                    2, new TextureRegion(Game.MENU_PANEL, 0, 0, 16*8, 16*8)));
        }
    }

    public MenuPanel() {

    }

    public void update() {
        for (Button button : buttons) {
            button.update();
        }
        for (UIElement uiElement : uiElements) {
            uiElement.update();
        }
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            for (Button button : buttons) {
                button.isPressed();
            }
        }
    }

    public Button addButton(float x, float y, int width, int height, String text, Consumer<String> method, String buttonText) {
        Button button = new Button(x, y, width, height, text, method, buttonText, hasBackgroundPanel);
        buttons.add(button);
        return button;
    }

    public void addUIElements(UIElement element) {
        uiElements.add(element);
    }

    public void delete() {
        for (UIElement uiElement : uiElements) {
            uiElement.remove();
        }
        for (int i = 0; i < buttons.size; i++) {
            buttons.get(i).remove();
        }

    }

    public void reload() {
        for (UIElement uiElement : uiElements) {
            uiElement.reload();
        }

        for (Button button : buttons) {
            button.reload();
        }
    }
}
