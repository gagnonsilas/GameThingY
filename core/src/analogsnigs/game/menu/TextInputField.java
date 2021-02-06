package analogsnigs.game.menu;

import analogsnigs.game.Game;
import analogsnigs.game.gameobjects.GameObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextInputField extends UIElement {

    int inputLength;

    InputProcessor processor;

    public TextInputField(int x, int y, int width, int height, int inputLength) {
        this.xPos = x;
        this.yPos = y;
        this.width = width;
        this.height = height;
        this.inputLength = inputLength;
        textPadding = (height - 30) / 4;
        font = new BitmapFont(Game.fontFile);
        font.getData().setScale(1);
        font.setColor(Color.WHITE);

        drawingLayer = 2;
        textureRegion = new TextureRegion(Game.TEXTURE_SHEET, 48, 48, 32, 16);
        addToDrawable();
        addTextObject();
        processor = new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                if(character == '\b') {
                    if(data.length() >= 1) {
                        data = data.substring(0, data.length() - 1);
                    }
                }
                else if(data.length() < inputLength) {
                    data += character;
                }
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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
        };
    }

    @Override
    public void update() {
        int inX = Gdx.input.getX();
        int inY = Gdx.graphics.getHeight() - Gdx.input.getY();
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            if (inX > xPos && xPos + width > inX && inY > yPos && yPos + height > inY) {
                Gdx.input.setInputProcessor(processor);
            }
        }
    }




}
