package analogsnigs.game.menu;

import analogsnigs.game.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextElement extends UIElement{

    private float x = 0;
    private float y = 0;

    public TextElement (float x, float y, int width, int height, String text, float scale) {
        this.x = x;
        this.y = y;
        this.xPos = (int) ((Gdx.graphics.getWidth() * x) - width / 2);
        this.yPos = (int) ((Gdx.graphics.getHeight() * y) - height / 2);
        this.width = width;
        this.height = height;
        textPadding = (height - 30) / 4;
        this.color = Color.WHITE;
        this.data = text;

        font = Game.FONT;
        font.getData().setScale(scale);

        layout = new GlyphLayout(font, data);

        drawingLayer = 2;
        addTextObject();

    }

    public TextElement (int x, int y, int width, int height, String text, float scale, boolean isOffset) {
        this.xPos = x - (width);
        this.yPos = y - (height / 2);
        this.width = width;
        this.height = height;
        textPadding = (height - 30) / 4;
        this.color = Color.WHITE;
        this.data = text;

        font = Game.FONT;
        font.getData().setScale(scale);

        layout = new GlyphLayout(font, data);

        drawingLayer = 2;
        addTextObject();

        this.isOffset = isOffset;

    }

    public void updatePos(int x, int y) {
        this.xPos = x - (width / 2);
        this.yPos = y - (height / 2);
    }
}
