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

    public Button addButton(int x, int y, int width, int height, String text, Consumer<String> method) {
        Button button = new Button(x, y, width, height, text, method);
        buttons.add(button);
        return button;
    }
}
