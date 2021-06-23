package analogsnigs.game;

import analogsnigs.game.gameobjects.GameObject;
import analogsnigs.game.menu.UIElement;
import analogsnigs.game.scene.Menu;
import analogsnigs.game.scene.Scene;
import analogsnigs.game.utilities.GameInput;
import analogsnigs.game.utilities.Map;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;

	public static boolean isMobile = false;
	public static Scene currentScene;
	public static Texture TEXTURE_SHEET;
	public static Texture MAP_TEXTURES;
	public static Texture MENU_PANEL;
	private Viewport viewport;
	private Camera camera;

	public static FileHandle fontFile;
	public static BitmapFont FONT;

	public static Color FONT_COLOR = new Color(0.2f, 0.2f, 0.2f, 1);

	public static GameInput INPUT = new GameInput();

	private static Menu MAIN_MENU;

	@Override
	public void create () {

		Gdx.graphics.setWindowedMode(1024, 576);

		Gdx.input.setCatchKey(Input.Keys.SPACE, true);

		// Load all of the assets
		TEXTURE_SHEET = new Texture("TextureSheet.png");

		MAP_TEXTURES = new Texture("MapTextures.png");

		MENU_PANEL = new Texture("MenuPanel.png");

		Map.loadMapAutoTiler();

		fontFile = Gdx.files.internal("font/PixelFont.fnt");

		Gdx.input.setInputProcessor(INPUT);

		batch = new SpriteBatch();

		camera = new OrthographicCamera();

		viewport = new ScreenViewport(camera);

		FONT = new BitmapFont(fontFile);

		MAIN_MENU = new Menu();

		currentScene = MAIN_MENU;

	}

	@Override
	public void render () {
		// Update camera scaling
		this.batch.setTransformMatrix(this.camera.view);
		this.batch.setProjectionMatrix(this.camera.projection);

		// Fill Background color
		Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Run the update on the scene
		currentScene.run();

		// Get camera offset for drawing objects at
		int[] offset = currentScene.getOffset();


		// Start drawing batch
		batch.begin();

		drawObjects(GameObject.backgroundObjects, offset);
		drawObjects(GameObject.drawableObjects, offset);
		drawObjects(GameObject.foregroundObjects, new int[]{0,0});

		// Render text elements after all objects.
		for (UIElement element : UIElement.textObjects) {
			element.font.draw(batch, element.layout,
					element.xPos + (element.width - element.layout.width) / 2 + (element.isOffset? offset[0] : 0),
					element.yPos + (element.height - element.layout.height) / 2 + 6 + (element.isOffset? offset[1] : 0));
		}

		batch.end();
	}

	public void drawObjects(Array<GameObject> objects, int[] offset) {
		// Loop through every object in layer and render to screen
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

	public static void loadMenu() {
		currentScene = MAIN_MENU;
		MAIN_MENU.reload();
	}

	public static void loadMenu(String s) {
		GameObject.resetObjects();
		UIElement.resetTextElements();
		currentScene = MAIN_MENU;
		MAIN_MENU.reload();
	}

}
