package analogsnigs.game.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import analogsnigs.game.Game;
import com.github.czyzby.websocket.CommonWebSockets;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		CommonWebSockets.initiate();
		new Lwjgl3Application(new Game(), config);
	}
}
