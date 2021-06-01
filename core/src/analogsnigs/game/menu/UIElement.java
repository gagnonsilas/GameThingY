package analogsnigs.game.menu;

import analogsnigs.game.Game;
import analogsnigs.game.gameobjects.GameObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Array;

public class UIElement extends GameObject{

    public float x;
    public float y;

    public String data = "";
    public GlyphLayout layout;

    public BitmapFont font;

    public boolean isOffset = false;

    public int textPadding;

    public static Array<UIElement> textObjects = new Array<>();

    public boolean hasBackgroundPanel;

    public UIElement() {}

    public void reload() {
        textObjects.add(this);
    }

    public void update() {
        this.xPos = hasBackgroundPanel?(int) (((Game.WALL_SIZE * 8 * x) + (Gdx.graphics.getWidth() / 2) - (Game.WALL_SIZE * 8 / 2)) - width / 2):
                (int) ((Gdx.graphics.getWidth() * x) - width / 2);
        this.yPos = hasBackgroundPanel?(int) (((Game.WALL_SIZE * 8 * y) + (Gdx.graphics.getHeight() / 2) - (Game.WALL_SIZE * 8 / 2)) - height / 2)
                :(int) ((Gdx.graphics.getHeight() * y) - height / 2);
    }

    public static void resetTextElements() {
        textObjects = new Array<>();
    }

    public void remove() {
        textObjects.removeValue(this, true);
        removeFromDrawable();
    }
}
