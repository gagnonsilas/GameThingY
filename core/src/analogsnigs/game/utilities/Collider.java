package analogsnigs.game.utilities;
import analogsnigs.game.gameobjects.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Collider {

    private static List<Collider> colliders = new ArrayList<>();
    private boolean isStatic;
    private GameObject gameObject;

    public Collider(GameObject gameObject, boolean isStatic) {
        colliders.add(this);
        this.isStatic = isStatic;
        this.gameObject = gameObject;
    }

    public static void checkColliders() {
        for (Collider collider : colliders) {
            if(!collider.isStatic) {
                for (Collider collider1 : colliders) {
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
        int x1 = gameObject.xPos;
        int y1 = gameObject.yPos;
        int w1 = gameObject.width;
        int h1 = gameObject.height;
        int x2 = collider2.gameObject.xPos;
        int y2 = collider2.gameObject.yPos;
        int w2 = collider2.gameObject.width;
        int h2 = collider2.gameObject.height;

        if (x1 >= x2 + w2 || x2 >= x1 + w1) {
            return false;
        }
        if (y1 >= y2 + h2 || y2 >= y1 + h1) {
            return false;
        }
        return true;
    }

    public void unCollide(Collider collider2) {
        int x1 = gameObject.xPos;
        int y1 = gameObject.yPos;
        int w1 = gameObject.width;
        int h1 = gameObject.height;
        int x2 = collider2.gameObject.xPos;
        int y2 = collider2.gameObject.yPos;
        int w2 = collider2.gameObject.width;
        int h2 = collider2.gameObject.height;

        int xMove = (x1 >= x2) ? (x2 + w2) - x1 : x2 - (x1 + w1);
        int yMove = (y1 >= y2) ? (y2 + h2) - y1 : y2 - (y1 + h1);


        if(Math.abs(xMove) <= Math.abs(yMove)) {
            gameObject.xPos += xMove;
        }
        else {
            gameObject.yPos += yMove;
        }
    }

}
