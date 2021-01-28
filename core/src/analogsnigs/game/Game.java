package analogsnigs.game;

import analogsnigs.game.gameobjects.GameObject;
import analogsnigs.game.scene.GameScene;
import analogsnigs.game.scene.Scene;
import analogsnigs.game.utilities.Collider;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;

	public static final int WALL_SIZE = 50;
	public static Scene currentScene;
	public static Texture TEXTURE_SHEET;

	
	@Override
	public void create () {
		TEXTURE_SHEET = new Texture("TextureSheet.png");

		batch = new SpriteBatch();

		currentScene = new GameScene();

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(27, 20, 36, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		currentScene.run();
		Collider.checkColliders();
		batch.begin();
		drawObjects(GameObject.backgroundObjects);
		drawObjects(GameObject.drawableObjects);
		drawObjects(GameObject.foregroundObjects);
		batch.end();
	}

	public void drawObjects(Array<GameObject> objects) {
		for (GameObject object : objects) {
			batch.draw(object.textureRegion, object.xPos, object.yPos, object.width, object.height);
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		TEXTURE_SHEET.dispose();
	}
}
