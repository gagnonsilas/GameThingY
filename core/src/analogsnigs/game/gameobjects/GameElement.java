package analogsnigs.game.gameobjects;

import analogsnigs.game.Game;
import analogsnigs.game.utilities.Collider;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameElement extends GameObject{

    public GameElement(int xPos, int yPos, int textureX, int textureY, int drawingLayer) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = Game.WALL_SIZE;
        this.height = Game.WALL_SIZE;
        addToDrawable(drawingLayer);
        collider = new Collider(this, drawingLayer == 1);
        textureRegion = new TextureRegion(Game.TEXTURE_SHEET, textureX * 16, textureY * 16, 16, 16);
    }
}
