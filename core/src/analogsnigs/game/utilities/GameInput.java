package analogsnigs.game.utilities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class GameInput implements InputProcessor {

    boolean[] pressedKeys = new boolean[Input.Keys.MAX_KEYCODE + 1];
    boolean[] pressedButtons = new boolean[5];



    @Override
    public boolean keyDown(int keycode) {
        pressedKeys[keycode] = true;
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        pressedKeys[keycode] = false;
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        pressedButtons[button] = true;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        pressedButtons[button] = false;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public void changeButtonState(int button, boolean state) {
        pressedButtons[button] = state;
    }

    public void changeKeyState(int keycode, boolean state) {
        pressedKeys[keycode] = state;
    }

    public boolean isKeyPressed(int keycode) {
        return pressedKeys[keycode];
    }

    public boolean isButtonPressed(int button) {
        return pressedButtons[button];
    }
}
