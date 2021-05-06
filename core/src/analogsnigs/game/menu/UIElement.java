package analogsnigs.game.menu;

import analogsnigs.game.Game;
import analogsnigs.game.gameobjects.GameObject;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Array;

public class UIElement extends GameObject{

    public String data = "";
    public GlyphLayout layout;

    public BitmapFont font;

    public boolean isOffset = false;

    public int textPadding;

    public static Array<UIElement> textObjects = new Array<>();

    public UIElement() {

    }

    public String getData() {
        return data;
    }

    public void reload() {
        textObjects.add(this);
    }

    public void update() {

    }

    public static void resetTextElements() {
        textObjects = new Array<>();
    }

    public void remove() {
        textObjects.removeValue(this, true);
    }
}
