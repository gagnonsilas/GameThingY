package analogsnigs.game.menu;

import analogsnigs.game.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.function.Consumer;

public class Button extends UIElement {
    Consumer<String> method;
    private final Array<UIElement> elements = new Array<>();

    private final float x;
    private final float y;

    public Button(float x, float y, int width, int height, String data, Consumer<String> method, String buttonText) {
        this.x = x;
        this.y = y;
        this.xPos = (int) ((Gdx.graphics.getWidth() * x) - width / 2);
        this.yPos = (int) ((Gdx.graphics.getHeight() * y) - height / 2);
        this.data = data;
        this.width = width;
        this.height = height;
        this.method = method;
        this.color = Color.WHITE;
        font = Game.FONT;
        font.getData().setScale(0.6f);
        font.setColor(Game.FONT_COLOR);
        layout = new GlyphLayout(font, buttonText);

        int textureXPos = 0;

        for (int i = (width / Game.WALL_SIZE) - 1; i > 0; i--) {
            textureXPos += i;
        }

        drawingLayer = 2;
        addToDrawable();
        addTextObject();
        textureRegion = new TextureRegion(Game.TEXTURE_SHEET, textureXPos * 16, 48, (width / Game.WALL_SIZE) * 16, 16);
    }

    public void addLinkedElement(UIElement linkedElement) {
        elements.add(linkedElement);
    }

    public void update() {
        for (UIElement element : elements) {
            element.update();
        }
        this.xPos = (int) ((Gdx.graphics.getWidth() * x) - width / 2);
        this.yPos = (int) ((Gdx.graphics.getHeight() * y) - height / 2);

    }

    public void isPressed() {
        int inX = Gdx.input.getX();
        int inY = Gdx.graphics.getHeight() - Gdx.input.getY();
        if (inX > xPos && xPos + width > inX && inY > yPos && yPos + height > inY) {
            StringBuilder string = new StringBuilder(data);

            for (UIElement element : elements) {
                string.append(element.data).append(",");
            }

            method.accept(elements.size > 0?string.substring(0, string.length() - 1):data);

            Game.INPUT.changeButtonState(Input.Buttons.LEFT, false);

        }
    }

    @Override
    public void remove() {
        super.remove();
        for (UIElement element : elements) {
            element.remove();
        }
        removeFromDrawable();
    }
}