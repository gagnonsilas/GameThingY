package analogsnigs.game;

import analogsnigs.game.gameobjects.GameObject;
import analogsnigs.game.menu.UIElement;
import analogsnigs.game.scene.Menu;
import analogsnigs.game.scene.Scene;
import analogsnigs.game.utilities.Collider;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.File;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;

	public static final int WALL_SIZE = 50;
	public static Scene currentScene;
	public static Texture TEXTURE_SHEET;
	private Viewport viewport;
	private Camera camera;

	public static FileHandle fontFile;



	
	@Override
	public void create () {
		TEXTURE_SHEET = new Texture("TextureSheet.png");

		fontFile = Gdx.files.internal("font/Pixeled-30.fnt");

		batch = new SpriteBatch();

		camera = new OrthographicCamera();

		viewport = new ScreenViewport(camera);

		currentScene = new Menu();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.094f, 0.063f, 0.122f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		currentScene.run();
		Collider.checkColliders();

		int[] offset = currentScene.getOffset();

		this.batch.setTransformMatrix(this.camera.view);
		this.batch.setProjectionMatrix(this.camera.projection);

		batch.begin();
		drawObjects(GameObject.backgroundObjects, offset);
		drawObjects(GameObject.drawableObjects, offset);
		drawObjects(GameObject.foregroundObjects, new int[]{0,0});
		for (UIElement element : UIElement.textObjects) {
			element.font.draw(batch, element.layout,
					element.xPos + (element.width - element.layout.width) / 2,
					element.yPos + (element.height + element.layout.height) / 2);
		}
		batch.end();
	}

	public void drawObjects(Array<GameObject> objects, int[] offset) {
		for (GameObject object : objects) {
			batch.setColor(object.color);
			batch.draw(object.textureRegion, object.xPos + offset[0], object.yPos + offset[1], object.width, object.height);
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		TEXTURE_SHEET.dispose();
		currentScene.quit();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		camera.update();
	}

}
