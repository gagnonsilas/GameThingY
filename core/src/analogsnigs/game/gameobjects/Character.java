package analogsnigs.game.gameobjects;

import analogsnigs.game.Game;
import analogsnigs.game.utilities.Collider;
import com.badlogic.gdx.Gdx;
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

    public Character(int xPos, int yPos, int width, int height, String name) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.collider = new Collider(this, false);
        this.name = name;
        addToDrawable(1);
        this.textureRegion = new TextureRegion(Game.TEXTURE_SHEET, 0, 16, 16, 16);
        characters.add(this);
    }

    public void move(int xPos, int yPos) {
        this.xPos = setX;
        this.yPos = setY;
        setX = xPos;
        setY = yPos;
        moveX = (setX - this.xPos) * 20;
        moveY = (setY - this.yPos) * 20;
    }

    public void update() {
        if(Math.abs(setX - xPos) > Math.abs(xPos + moveX * Gdx.graphics.getDeltaTime())) {
            moveX = 0;
            moveY = 0;
            xPos = setX;
            yPos = setY;
        }
        xPos += moveX * Gdx.graphics.getDeltaTime();
        yPos += moveY * Gdx.graphics.getDeltaTime();
    }

    public static void moveCharacters() {
        for (Character character : characters) {
            character.update();
        }
    }

    public void deleteCharacter() {
        characters.remove(this);
    }

    public static Character findCharacterByName(String name) {
        for (Character character : characters) {
            if(character.name.equals(name)) {
               return character;
            }
        }

        return new Character(0, 0, 45, 45, name);
    }
}
