package analogsnigs.game.utilities;
import analogsnigs.game.gameobjects.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Collider {

    private transient static List<Collider> colliders = new ArrayList<>();
    private final boolean isStatic;
    private transient final GameObject gameObject;

    private final int[] boundingBox;

    public Collider(GameObject gameObject, boolean isStatic) {
        colliders.add(this);
        this.isStatic = isStatic;
        this.gameObject = gameObject;
        boundingBox = new int[]{0, 0, gameObject.width, gameObject.height};
    }

    public Collider(GameObject gameObject, boolean isStatic, int xOffset, int yOffset, int width, int height) {
        colliders.add(this);
        this.isStatic = isStatic;
        this.gameObject = gameObject;
        boundingBox = new int[]{xOffset, yOffset, width, height};
    }

    public static void checkColliders() {
        for (Collider collider : new ArrayList<>(colliders)) {
            if(!collider.isStatic ) {
                for (Collider collider1 : new ArrayList<>(colliders)) {
                    if(collider1.isStatic) {
                        if (collider.isOverlapping(collider1)) {
                            collider.unCollide(collider1);
                        }
                    }
                }
            }
        }
    }

    public boolean isOverlapping(Collider collider2) {
        int x1 = gameObject.xPos + boundingBox[0];
        int y1 = gameObject.yPos + boundingBox[1];
        int w1 = boundingBox[2];
        int h1 = boundingBox[3];
        int x2 = collider2.gameObject.xPos + collider2.boundingBox[0];
        int y2 = collider2.gameObject.yPos + collider2.boundingBox[1];
        int w2 = collider2.boundingBox[2];
        int h2 = collider2.boundingBox[3];

        if (x1 >= x2 + w2 || x2 >= x1 + w1) {
            return false;
        }
        return y1 < y2 + h2 && y2 < y1 + h1;
    }

    public void unCollide(Collider collider2) {
        int x1 = gameObject.xPos + boundingBox[0];
        int y1 = gameObject.yPos + boundingBox[1];
        int w1 = boundingBox[2];
        int h1 = boundingBox[3];
        int x2 = collider2.gameObject.xPos + collider2.boundingBox[0];
        int y2 = collider2.gameObject.yPos + collider2.boundingBox[1];
        int w2 = collider2.boundingBox[2];
        int h2 = collider2.boundingBox[3];

        int xMove = (x1 >= x2) ? (x2 + w2) - x1 : x2 - (x1 + w1);
        int yMove = (y1 >= y2) ? (y2 + h2) - y1 : y2 - (y1 + h1);


        if(Math.abs(xMove) <= Math.abs(yMove)) {
            gameObject.move(gameObject.xPos + xMove, gameObject.yPos);
        }
        else {
            gameObject.move(gameObject.xPos, gameObject.yPos + yMove);
        }


    }

    public static void delete(Collider c) {
        colliders.remove(c);
    }

    public static void resetColliders() {
        colliders = new ArrayList<>();
    }

}
