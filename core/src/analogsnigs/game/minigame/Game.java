package analogsnigs.game.minigame;
import analogsnigs.game.server.Player;
import analogsnigs.game.server.Server;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import analogsnigs.game.server.Panel;
import analogsnigs.game.utilities.Map;

public class Game {

    public final ConcurrentHashMap<String, Player> players = new ConcurrentHashMap<>();
    public final Map map;
    public int[] spawnPoint;

    public transient final HashMap<String, Panel> panels = new HashMap<>();
    public transient final HashMap<String, Event> events = new HashMap<>();
    public final ArrayList<GamePlayObject> objects = new ArrayList<>();


    public Game(int[][] mapData) {
        map = new Map(mapData);

        objects.add(new GamePlayObject(64, 416, 64, 1, "testEvent"));
        objects.add(new GamePlayObject(384, 416, 64, 0, "broadcastEvent"));
        objects.add(new GamePlayObject(512, 416, 64, 2, "event"));
        objects.add(new GamePlayObject(128, 416, 64, 3, "event"));
        objects.add(new GamePlayObject(640, 416, 64, 4, "event"));

        Panel testPanel = new Panel();
//        testPanel.addButton(new Button(0.7f, 0.15f, 0, 0, 3 * 64, 64, "test", client::sendPanelData));
//        testPanel.addButton(new Button(0.3f, 0.15f, 0, 0, 3 * 64, 64, "button 2", "Button 2"));
//        testPanel.addButton(new Button(0.5f, 0.5f, 0, 0, 3 * 64, 64, "button", "INPUT"));

        panels.put("panel", testPanel);

        events.put("testEvent", new Event());
        events.put("broadcastEvent", new Event());
        events.get("testEvent").addCall(panels.get("panel")::sendToPlayer);
        events.get("broadcastEvent").addCall(panels.get("panel")::broadcastPanel);

        spawnPoint = new int[] {200, 200};

        for (Player player : players.values()) {
            player.teleport(spawnPoint[0], spawnPoint[1]);
        }

    }

    public int[][] getMap() {
        return(map.map);
    }

    public void runEvent(String eventName, Player player) {
        events.get(eventName).triggerEvent(player);
    }

    public void interact(Player player) {
        for (GamePlayObject object : objects) {
            object.interact(player);
        }
    }

    public void panelInput(Player player, String data) {
        if(data.equals("test")) {
            player.teleport(275, 500);
        }
        if(data.equals("map2")) {
            Server.game = new Game(
                    new int[][]{{0,0,0,0,2,2,2,2,2},
                            {0,0,0,0,2,1,1,1,2},
                            {0,0,0,0,2,1,1,1,2},
                            {2,2,2,2,2,1,1,1,2},
                            {2,1,1,1,1,1,1,1,2},
                            {2,1,1,1,1,1,1,1,2},
                            {2,1,1,1,1,1,1,1,2},
                            {2,2,2,2,2,2,2,2,2}}
            );
            Server.broadcastGame();
        }
        else if(data.equals("map1")) {
            Server.game = new Game(new int[][]{
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,2,2},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,1,1,1,1,1,1,1,2},
                    {2,2,2,2,2,2,2,2,2,2,2,0,0,0,0,0,2,1,1,1,1,1,1,1,2},
                    {2,1,1,1,1,1,1,1,1,1,2,0,0,0,0,0,2,1,1,1,1,1,1,1,2},
                    {2,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,1,1,1,1,1,1,1,2},
                    {2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2},
                    {2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2},
                    {2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2},
                    {2,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,1,1,1,1,1,1,1,2},
                    {2,1,1,1,1,1,1,1,1,1,2,0,0,0,0,0,2,1,1,1,1,1,1,1,2},
                    {2,1,1,1,1,1,1,1,1,1,2,0,0,0,0,0,2,1,1,1,1,1,1,1,2},
                    {2,1,1,1,1,1,1,1,1,1,2,0,0,0,0,0,2,1,1,1,1,1,1,1,2},
                    {2,2,2,2,1,1,2,2,2,2,2,0,0,0,0,0,2,1,1,1,1,1,1,1,2},
                    {0,0,0,2,1,1,2,0,0,0,0,0,0,0,0,0,2,1,1,1,1,1,1,1,2},
                    {0,0,0,2,1,1,2,0,0,0,0,0,0,0,0,0,2,1,1,1,1,1,1,1,2},
                    {0,0,0,2,1,1,2,0,0,0,0,0,0,0,0,0,2,1,1,1,1,1,1,1,2},
                    {0,0,0,2,1,1,2,0,0,0,0,0,0,0,0,0,2,1,1,1,1,1,1,1,2},
                    {0,0,0,2,1,1,2,0,0,0,0,0,0,0,0,0,2,1,1,1,1,1,1,1,2},
                    {0,0,0,2,1,1,2,0,0,0,0,0,0,0,0,0,2,1,1,1,1,1,1,1,2},
                    {0,0,0,2,1,1,2,0,0,0,0,0,0,0,0,0,2,1,1,1,1,1,1,1,2},
                    {0,0,0,2,1,1,2,0,0,0,0,0,0,0,0,0,2,1,1,1,1,1,1,1,2},
                    {0,0,0,2,1,1,2,0,0,0,0,0,0,0,0,0,2,1,1,1,1,1,1,1,2},
                    {2,2,2,2,1,1,2,2,2,2,2,2,2,2,2,2,2,1,1,1,1,1,1,1,2},
                    {2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2},
                    {2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2},
                    {2,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
                    {2,1,1,1,1,1,1,1,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {2,1,1,1,1,1,1,1,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {2,2,2,2,2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}}
            );
            Server.broadcastGame();
        }
    }
}
