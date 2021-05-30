package analogsnigs.game.utilities;

import analogsnigs.game.Game;
import analogsnigs.game.menu.UIElement;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Joystick extends UIElement {

    private int x;
    private int y;


    public Joystick(int xPos, int yPos) {
        this.x = xPos;
        this.y = yPos;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = (Game.WALL_SIZE / 16) * 10;
        this.height = (Game.WALL_SIZE / 16) * 10;
        this.drawingLayer = 2;
        this.color = Color.WHITE;
        this.textureRegion = new TextureRegion(Game.TEXTURE_SHEET, 16, 4 * 16, 10, 10);
        isOffset = true;
        addToDrawable();
    }

    public float[] checkJoystick() {

        int inX = Gdx.input.getX() - (width / 2);
        int inY = Gdx.graphics.getHeight() - Gdx.input.getY() - (height / 2);


        if(inX < Gdx.graphics.getWidth() / 2 && inY < Gdx.graphics.getHeight() / 2) {
            float xOffset;
            float yOffset;
            if (Gdx.input.justTouched()) {
                x = inX;
                y = inY;
            } else if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                float length = (float) Math.sqrt(((inX - x) * (inX - x)) +
                        ((inY - y) * (inY - y)));
                if (length < Game.WALL_SIZE) {
                    xPos = inX;
                    yPos = inY;
                    xOffset = (inX - x) / (Game.WALL_SIZE * 1f);
                    yOffset = (inY - y) / (Game.WALL_SIZE * 1f);
                } else {
                    xOffset = (inX - x) / (length);
                    yOffset = (inY - y) / (length);
                    xPos = (int) (x + (xOffset * Game.WALL_SIZE));
                    yPos = (int) (y + (yOffset * Game.WALL_SIZE));
                }
                return new float[]{xOffset, yOffset};
            } else {
                xPos = x;
                yPos = y;
            }
        }

        return new float[]{0, 0};
    }
}
