package analogsnigs.game.menu;

import analogsnigs.game.Game;
import analogsnigs.game.gameobjects.GameObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextInputField extends UIElement {

    int inputLength;

    InputProcessor processor;

    private final float x;
    private final float y;

    public TextInputField(float x, float y, int width, int height, int inputLength) {
        this.x = x;
        this.y = y;
        this.xPos = (int) ((Gdx.graphics.getWidth() * x) - width / 2);
        this.yPos = (int) ((Gdx.graphics.getHeight() * y) - height / 2);
        this.width = width;
        this.height = height;
        this.inputLength = inputLength;
        textPadding = (height - 30) / 4;
        this.color = Color.WHITE;
        font = Game.FONT;
        font.getData().setScale(1);

        layout = new GlyphLayout(font, data);

        drawingLayer = 2;
        textureRegion = new TextureRegion(Game.TEXTURE_SHEET, 0, 64, 16, 16);
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
                layout.setText(font, data);
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
        this.xPos = (int) ((Gdx.graphics.getWidth() * x) - width / 2);
        this.yPos = (int) ((Gdx.graphics.getHeight() * y) - height / 2);
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            if (inX > xPos && xPos + width > inX && inY > yPos && yPos + height > inY) {
                Gdx.input.setInputProcessor(processor);
            }
        }
    }




}
