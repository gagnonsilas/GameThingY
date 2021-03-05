package analogsnigs.game.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;

import java.util.function.Consumer;


public class MenuPanel {

    Array<UIElement> uiElements = new Array<>();
    Array<Button> buttons = new Array<>();

    public MenuPanel() {

    }

    public void update() {
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            for (Button button : buttons) {
                button.update();
            }
        }
    }

    public Button addButton(float x, float y, int width, int height, String text, Consumer<String> method, String buttonText) {
        Button button = new Button(x, y, width, height, text, method, buttonText);
        buttons.add(button);
        return button;
    }

    public void addUIElements(UIElement element) {
        uiElements.add(element);
    }
}
