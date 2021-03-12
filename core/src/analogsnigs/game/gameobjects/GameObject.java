package analogsnigs.game.gameobjects;

import analogsnigs.game.utilities.Collider;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.Comparator;


public class GameObject {

    public static Array<GameObject> drawableObjects = new Array<>();
    public static Array<GameObject> backgroundObjects = new Array<>();
    public static Array<GameObject> foregroundObjects = new Array<>();

    public TextureRegion textureRegion;
    public int xPos;
    public int yPos;
    public int width;
    public int height;
    public Collider collider;
    public int drawingLayer;

    public Color color = Color.WHITE;

    public void addToDrawable() {
        switch (drawingLayer) {
            case 1:
                drawableObjects.add(this);
                break;
            case 0:
                backgroundObjects.add(this);
                break;
            case 2:
                foregroundObjects.add(this);
        }

    }

    public void removeFromDrawable() {
        switch (drawingLayer) {
            case 1:
                drawableObjects.removeValue(this, true);
                break;
            case 0:
                backgroundObjects.removeValue(this, true);
                break;
            case 2:
                foregroundObjects.removeValue(this, true);
        }
    }

    public static void resetDrawableObjects() {
        drawableObjects = new Array<>();
        backgroundObjects = new Array<>();
        foregroundObjects = new Array<>();
    }

    public void delete() {
        Collider.delete(collider);
        removeFromDrawable();
    }

    public static void sort() {
        drawableObjects.sort((a, b) -> b.yPos - a.yPos);
    }

    public static void resetObjects() {
        Collider.resetColliders();
        resetDrawableObjects();
    }

}
