package analogsnigs.game.gameobjects;

import analogsnigs.game.utilities.Collider;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;



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

    public void addToDrawable(int drawingLayer) {
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

    public void removeFromDrawable(int drawingLayer) {
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

}