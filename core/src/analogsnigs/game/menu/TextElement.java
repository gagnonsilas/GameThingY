package analogsnigs.game.menu;

import analogsnigs.game.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

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

        // Split into rows of appropriate length
        int lettersPerRow = (width / 64) * 3;

//        System.out.println(lettersPerRow);

        for (int i = 0; i < text.length() / lettersPerRow; i++) {
            for (int j = 0; j < text.length(); j++) {
                if (text.substring((lettersPerRow * i) + j, (lettersPerRow * i) + j + 1).equals(" ")) {
                    text = text.substring(0, (lettersPerRow * i) + j) + "\n" + text.substring((lettersPerRow * i) + j);
                    break;
                }
            }
        }
        this.data = text;

        font = Game.FONT;
        font.getData().setScale(scale);

        layout = new GlyphLayout(font, data);

        drawingLayer = 2;
        reload();

    }

    public TextElement (int x, int y, int width, int height, String text, float scale, boolean isOffset, Color color) {
        this.xPos = x - (width);
        this.yPos = y - (height / 2);
        this.width = width;
        this.height = height;
        textPadding = (height - 30) / 4;
        this.color = Color.WHITE;
        this.data = text;

        font = Game.FONT;
        font.getData().setScale(scale);
        font.setColor(color);
        layout = new GlyphLayout(font, data);

        drawingLayer = 2;
        reload();

        this.isOffset = isOffset;

    }

    public void updatePos(int x, int y) {
        this.xPos = x - (width / 2);
        this.yPos = y - (height / 2);
    }
}
