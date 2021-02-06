package analogsnigs.game.menu;

import analogsnigs.game.Game;
import analogsnigs.game.gameobjects.GameObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.function.Consumer;

public class Button extends UIElement {
    Consumer<String> method;
    private Array<UIElement> elements = new Array<>();

    public Button (int x, int y, int width, int height, String data, Consumer<String> method) {
        this.xPos = x;
        this.yPos = y;
        this.data = data;
        this.width = width;
        this.height = height;
        this.method = method;
        drawingLayer = 2;
        addToDrawable();
        textureRegion = new TextureRegion(Game.TEXTURE_SHEET, 0, 48, 16, 16);
    }

    public void addLinkedElement(UIElement linkedElement) {
        elements.add(linkedElement);
    }

    public void update() {
        for (UIElement element : elements) {
            element.update();
        }
        int inX = Gdx.input.getX();
        int inY = Gdx.graphics.getHeight() - Gdx.input.getY();
        if(inX > xPos && xPos + width > inX && inY > yPos && yPos + height > inY) {
            StringBuilder string = new StringBuilder(data);

            for (UIElement element : elements) {
                string.append(element.data).append(",");
            }

            method.accept(string.substring(0, string.length()-1));
        }
    }
}
