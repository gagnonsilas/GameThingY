package analogsnigs.game.menu;

import analogsnigs.game.Game;
import analogsnigs.game.utilities.Constants;
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

    public Button(float x, float y, int xOffset, int yOffset, int width, int height, String data, Consumer<String> method, String buttonText, boolean hasBackgroundPanel) {
        this.hasBackgroundPanel = hasBackgroundPanel;
        this.x = x;
        this.y = y;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        super.update();
        this.data = data;
        this.width = width;
        this.height = height;
        this.method = method;
        this.color = Color.WHITE;
        font = Game.FONT;
        font.getData().setScale(1f);
        font.setColor(Game.FONT_COLOR);
        layout = new GlyphLayout(font, buttonText);

        int textureXPos = 0;

        for (int i = (width / Constants.WALL_SIZE) - 1; i > 0; i--) {
            textureXPos += i;
        }

        drawingLayer = 2;
        addToDrawable();
        this.reload();
        textureRegion = new TextureRegion(Game.TEXTURE_SHEET, textureXPos * 16, 48, (width / Constants.WALL_SIZE) * 16, 16);
    }


    public Button(float x, float y, int xOffset, int yOffset, int width, int height, String data, Consumer<String> method, String buttonText) {
        this.hasBackgroundPanel = false;
        this.x = x;
        this.y = y;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        super.update();
        this.data = data;
        this.width = width;
        this.height = height;
        this.method = method;
        this.color = Color.WHITE;
        font = Game.FONT;
        font.getData().setScale(1f);
        font.setColor(Game.FONT_COLOR);
        layout = new GlyphLayout(font, buttonText);

        int textureXPos = 0;

        for (int i = (width / Constants.WALL_SIZE) - 1; i > 0; i--) {
            textureXPos += i;
        }

        drawingLayer = 2;
        addToDrawable();
        this.reload();
        textureRegion = new TextureRegion(Game.TEXTURE_SHEET, textureXPos * 16, 48, (width / Constants.WALL_SIZE) * 16, 16);
    }


    public void addLinkedElement(UIElement linkedElement) {
        elements.add(linkedElement);
    }

    public void update() {
        for (UIElement element : elements) {
            element.update();
        }
        super.update();
    }

    public void isPressed() {
        int inX = Gdx.input.getX();
        int inY = Gdx.graphics.getHeight() - Gdx.input.getY();
        if (inX > xPos && xPos + width > inX && inY > yPos && yPos + height > inY) {
            StringBuilder string = new StringBuilder(data);

            for (UIElement element : elements) {
                if(string.length() > 0) {
                    string.append(",");
                }
                string.append(element.data);
            }
            if(string.length() > 0) {
                method.accept(string.toString());
            }

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

    public void reload() {
        for (UIElement element : elements) {
            element.reload();
        }
        textObjects.add(this);
        addToDrawable();
    }
}