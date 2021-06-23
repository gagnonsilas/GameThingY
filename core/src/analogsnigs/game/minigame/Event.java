package analogsnigs.game.minigame;
import analogsnigs.game.server.Player;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Event {

    ArrayList<Consumer<Player>> calls = new ArrayList<>();

    public void addCall(Consumer<Player> call) {
        calls.add(call);
    }

    public void triggerEvent(Player player) {
        for (Consumer<Player> call : calls) {
            call.accept(player);
        }
    }


}
