package analogsnigs.game.server;

import analogsnigs.game.client.Packet;
import analogsnigs.game.minigame.Game;
import analogsnigs.game.player.Character;
import analogsnigs.game.utilities.Constants;
import com.google.gson.Gson;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class Server extends TimerTask {
    private static WebSocketServer wss;

    public static Game game;

    Gson gson = new Gson();

    public static void main(String[] args) {

        try {
            new Server(Integer.parseInt(args[0]));
        }
        catch(ArrayIndexOutOfBoundsException e) {
            new Server(8753);
        }
    }

    public Server(int port) {

        game = new Game(new int[][]{
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
        websocketHandler(port);
        new Timer().scheduleAtFixedRate(this, 0, 50);
        wss.run();
    }

    public void websocketHandler(int port) {

        wss = new WebSocketServer(new InetSocketAddress(port)) {

            @Override
            public void onOpen(WebSocket conn, ClientHandshake handshake) {}

            @Override
            public void onClose(WebSocket conn, int code, String reason, boolean remote) {
                for (Player player : game.players.values()) {
                    if(player.isCorrectSocket(conn)) {
                        log(player.name + " Disconnected");
    //                        players.remove(player.name);
                    }
                }
            }

            public void updatePlayer(Character player) {
                game.players.get(player.name).updatePlayer(
                        player.xPos,
                        player.yPos,
                        player.width,
                        player.height,
                        player.hue);
            }

            @Override
            public void onMessage(WebSocket conn, String message) {

                Packet packet = gson.fromJson(message, Packet.class);
                switch (packet.id) {
                    case Constants.ADD_PLAYER:
                        Character player = gson.fromJson(gson.toJson(packet.object), Character.class);
                        game.players.put(player.name, new Player(conn));
                        game.players.get(player.name).name = player.name;
                        log(player.name + " Joined the Game");
                        Packet gamePacket = new Packet(Constants.SEND_GAME, game);
                        conn.send(gson.toJson(gamePacket));
                        broadcast(message);
                    case Constants.SEND_PLAYER:
                        updatePlayer(gson.fromJson(gson.toJson(packet.object), Character.class));
                }

            }

            @Override
            public void onError(WebSocket conn, Exception ex) {
                ex.printStackTrace();
                if(conn != null) {
                    for (Player player : game.players.values()) {
                        if (player.isCorrectSocket(conn)) {

                            log(player.name + " Disconnected");
                            game.players.remove(player.name);
                        }
                    }
                }
            }

            @Override
            public void onStart() {
                log("server started");
                log("Waiting for connection ... ");
            }
        };
        wss.setTcpNoDelay(true);
    }

    public static void log(String log) {
        System.out.println("[" + Constants.DTF.format(LocalDateTime.now())+ "] " + log);
    }

    @Override
    public void run() {
        for (Player player : game.players.values()) {
            broadcast(gson.toJson(new Packet(Constants.SEND_PLAYER, player)));
        }

    }

    public static void broadcastGame() {
        broadcast(new Gson().toJson(new Packet(Constants.SEND_GAME, new Character())));
    }

    public static void broadcast(String data) {
        wss.broadcast(data);
    }
}
