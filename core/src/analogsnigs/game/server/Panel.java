package analogsnigs.game.server;

import analogsnigs.game.client.Packet;
import analogsnigs.game.menu.MenuPanel;
import analogsnigs.game.utilities.Constants;
import com.google.gson.Gson;

public class Panel extends MenuPanel {

    public String getPanel() {
        Packet packet = new Packet(Constants.SEND_PANEL, this);
        return new Gson().toJson(packet);
    }

    public void broadcastPanel(Player player) {
        Server.broadcast(getPanel());
    }

    public void sendToPlayer(Player player) {
        player.sendData(getPanel());
    }
}
