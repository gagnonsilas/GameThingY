package analogsnigs.game.player;

import analogsnigs.game.Game;
import analogsnigs.game.gameobjects.GameObject;
import analogsnigs.game.menu.TextElement;
import analogsnigs.game.utilities.Collider;
import analogsnigs.game.utilities.Constants;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Character extends GameObject {

    public transient static HashMap<String, Character> characters = new HashMap<>();
    public transient TextElement nameTag;


    public String name;
    private int setX;
    private int setY;
    private int moveX;
    private int moveY;

    public int hue;


    public Character(int xPos, int yPos, int width, int height, String name, int hue) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.collider = new Collider(this, false, 0, 0, width, height / 2);
        this.name = name;
        this.drawingLayer = 1;
        this.textureRegion = new TextureRegion(Game.TEXTURE_SHEET, 0, 16, 16, 16);
        setHue(hue);
        characters.put(name, this);
        addToDrawable();
        nameTag = new TextElement(xPos + width / 2, yPos + height + 30, Constants.WALL_SIZE * 2, Constants.WALL_SIZE / 2, name, 0.8f, true, new Color(0f, 0f, 0f, 0.8f));

    }

    public Character() {
    }

    public void moveToPoint(int xPos, int yPos) {
        this.xPos = setX;
        this.yPos = setY;
        setX = xPos;
        setY = yPos;
        moveX = 1;
        moveY = 1;
    }

    public void update() {
        nameTag.updatePos(xPos + (width / 2), yPos + height + 15);
        moveX = moveX == 1 && !(Math.abs(setX - xPos) < 1)?1:0;
        moveY = moveY == 1 && !(Math.abs(setY - yPos) < 1)?1:0;

        xPos += (setX - xPos) / 4 * moveX;
        yPos += (setY - yPos) / 4 * moveY;
    }

    public static void moveCharacters() {
        for (Character character : characters.values()) {
            character.update();
        }
    }

    public void deleteCharacter() {
        nameTag.remove();
        removeFromDrawable();
        characters.remove(name);
    }

    public void setHue(int hue) {
        this.hue = hue;
        this.color = new Color(0, 0, 0, 1).fromHsv( hue, 0.5f, 1f);
    }

    public static void deleteAll() {
        characters = new HashMap<>();
    }

    public void hideNameTag() {
        nameTag.remove();
    }

    public void showNameTag() {
        nameTag.reload();
    }

    @Override
    public void move(int x, int y) {
        xPos = x;
        yPos = y;
        nameTag.updatePos(xPos + (width / 2), yPos + height + 15);
    }
}
