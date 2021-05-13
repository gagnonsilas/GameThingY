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
        this.width = (Game.WALL_SIZE / 16) * 6;
        this.height = (Game.WALL_SIZE / 16) * 6;
        this.drawingLayer = 1;
        this.color = Color.WHITE;
        this.textureRegion = new TextureRegion(Game.TEXTURE_SHEET, 4 * 16, 16, 10, 10);
        isOffset = false;
        addToDrawable();
    }

    public float[] checkJoystick() {

        int inX = Gdx.input.getX();
        int inY = Gdx.input.getY() - Gdx.graphics.getHeight();
        if(inX < Gdx.graphics.getWidth() / 2 && inY < Gdx.graphics.getWidth() / 2) {
            if (Gdx.input.justTouched()) {
                x = inX;
                y = inY;
            } else if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                int length = (int) Math.sqrt(((inX - x) * (inX - x)) +
                        ((inY - y) * (inY - y)));
                if (length < Game.WALL_SIZE) {
                    xPos = inX;
                    yPos = inY;
                } else {
                    xPos = ((inX - x) / length) * Game.WALL_SIZE;
                    yPos = ((inY - y) / length) * Game.WALL_SIZE;
                }
                System.out.println(xPos - x + " " + x + " " + length);
                System.out.println((inX - x) + ", " + (inY - y));
                return new float[]{(xPos - x) / (Game.WALL_SIZE * 1f), (y - yPos) / (Game.WALL_SIZE * 1f)};
            } else {
                xPos = x;
                yPos = y;
            }
        }

        return new float[]{0, 0};
    }
}
