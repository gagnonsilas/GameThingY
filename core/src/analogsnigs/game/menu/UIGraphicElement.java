package analogsnigs.game.menu;

import analogsnigs.game.utilities.Collider;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class UIGraphicElement extends UIElement{

    private final float x, y;

    public UIGraphicElement(float xPos, float yPos, int width, int height, int drawingLayer, TextureRegion textureRegion) {
        this.x = xPos;
        this.y = yPos;
        this.xPos = (int) ((Gdx.graphics.getWidth() * x) - width / 2);
        this.yPos = (int) ((Gdx.graphics.getHeight() * y) - height / 2);
        this.width = width;
        this.height = height;
        this.drawingLayer = drawingLayer;
        this.color = Color.WHITE;
        this.textureRegion = textureRegion;
        addToDrawable();
    }

    @Override
    public void update() {
        this.xPos = (int) ((Gdx.graphics.getWidth() * x) - width / 2);
        this.yPos = (int) ((Gdx.graphics.getHeight() * y) - height / 2);
    }

    @Override
    public void remove() {
        removeFromDrawable();
    }
}
