package analogsnigs.game.gameobjects;

import analogsnigs.game.Game;
import analogsnigs.game.utilities.Collider;
import analogsnigs.game.utilities.Constants;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameElement extends GameObject{
    private float x;
    private float y;

    public GameElement(int xPos, int yPos, int size, int textureID, int drawingLayer) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = Constants.WALL_SIZE;
        this.height = Constants.WALL_SIZE;
        this.drawingLayer = drawingLayer;
        this.color = Color.WHITE;
        textureRegion = new TextureRegion(Game.TEXTURE_SHEET, textureID * 16, 32, 16, 16);
        addToDrawable();
    }
    public GameElement(int xPos, int yPos, int drawingLayer, TextureRegion textureRegion) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = Constants.WALL_SIZE;
        this.height = Constants.WALL_SIZE;
        this.drawingLayer = drawingLayer;
        this.color = Color.WHITE;
        if(drawingLayer == 1) {
            collider = new Collider(this, true);
        }
        this.textureRegion = textureRegion;
        addToDrawable();
    }
}
