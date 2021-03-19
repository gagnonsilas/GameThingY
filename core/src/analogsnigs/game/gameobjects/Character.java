package analogsnigs.game.gameobjects;

import analogsnigs.game.Game;
import analogsnigs.game.menu.TextElement;
import analogsnigs.game.utilities.Collider;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;

public class Character extends GameObject{

    public String name;

    public static List<Character> characters = new ArrayList<>();

    private int setX;
    private int setY;
    private int moveX;
    private int moveY;

    public int hue;

    public TextElement nameTag;

    public Character(int xPos, int yPos, int width, int height, String name, int hue) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.collider = new Collider(this, false);
        this.name = name;
        this.drawingLayer = 1;
        this.textureRegion = new TextureRegion(Game.TEXTURE_SHEET, 0, 16, 16, 16);
        setHue(hue);
        characters.add(this);
        addToDrawable();
        nameTag = new TextElement(xPos + width / 2, yPos + height + 30, 50, 20, name, 0.5f, true);

    }

    public void move(int xPos, int yPos) {
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
        for (Character character : characters) {
            character.update();
        }
    }

    public void deleteCharacter() {
        nameTag.remove();

        removeFromDrawable();
        characters.remove(this);
    }

    public static Character findCharacterByName(String name) {
        for (Character character : characters) {
            if(character.name.equals(name)) {
               return character;
            }
        }

        return new Character(0, 0, 45, 45, name, 0);
    }

    public void setHue(int hue) {
        this.hue = hue;
        this.color = new Color(0, 0, 0, 1).fromHsv( hue, 0.5f, 1f);
    }

    public static void deleteAll() {
        characters = new ArrayList<>();
    }

}
