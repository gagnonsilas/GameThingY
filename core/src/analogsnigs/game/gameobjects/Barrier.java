package analogsnigs.game.gameobjects;

import analogsnigs.game.utilities.Collider;
import com.badlogic.gdx.graphics.Color;

public class Barrier extends GameObject{
    public Barrier(int xPos, int yPos, int width, int height) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.color = Color.BLUE;
        collider = new Collider(this, true);
    }
}
