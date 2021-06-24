package analogsnigs.game.client;

import analogsnigs.game.gameobjects.GameElement;
import analogsnigs.game.menu.Button;
import analogsnigs.game.menu.MenuPanel;
import analogsnigs.game.menu.TextElement;
import analogsnigs.game.menu.TextInputField;
import analogsnigs.game.minigame.Game;
import analogsnigs.game.minigame.GamePlayObject;
import analogsnigs.game.player.Player;
import analogsnigs.game.scene.Scene;
import analogsnigs.game.utilities.Map;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketListener;
import com.github.czyzby.websocket.WebSockets;
import com.github.czyzby.websocket.data.WebSocketCloseCode;
import analogsnigs.game.player.Character;
import com.google.gson.Gson;
import analogsnigs.game.utilities.Constants;

import java.lang.reflect.Type;
import java.util.HashMap;

public class Client {
    WebSocket socket;
    Map map;
    MenuPanel panel;
    Scene game;
    private final Gson gson = new Gson();

    public Client(Scene game) {
//        socket = WebSockets.newSocket("wss://analog-snigs-games.herokuapp.com");
        socket = WebSockets.newSocket("ws://localhost:8753");
        this.game = game;
        this.map = new Map(new int[0][]);
        this.map.setMap(new int[0][], true);
        panel = new MenuPanel();
        socket.setSendGracefully(true);
        socket.addListener(new WebSocketListener() {
            @Override
            public boolean onOpen(WebSocket webSocket) {
                Packet packet = new Packet(Constants.ADD_PLAYER, Player.character);
                String output = new Gson().toJson(packet);
                socket.send(output);
                return false;
            }

            @Override
            public boolean onClose(WebSocket webSocket, WebSocketCloseCode code, String reason) {
                Character.deleteAll();
                return false;
            }

            @Override
            public boolean onMessage(WebSocket webSocket, String packet) {
                parsePacket(packet);
                return false;
            }

            @Override
            public boolean onMessage(WebSocket webSocket, byte[] packet) {
                return false;
            }

            @Override
            public boolean onError(WebSocket webSocket, Throwable error) {
                return false;
            }
        });

        socket.connect();
    }

    public void sendCharacter() {
        Packet packet = new Packet(Constants.SEND_PLAYER, Player.character);
        String output = new Gson().toJson(packet);
        socket.send(output);
    }

    public void parsePacket(String data) {
        Packet packet = new Gson().fromJson(data, Packet.class);
        switch (packet.id){
            case Constants.SEND_GAME:
                Game game = gson.fromJson(gson.toJson(packet.object), Game.class);
                loadMap(game.getMap());
                Player.character.move(game.spawnPoint[0], game.spawnPoint[1]);
                for (GamePlayObject object : game.objects) {
                    object.textureRegion = new TextureRegion(analogsnigs.game.Game.TEXTURE_SHEET, 32, object.textureID * 16);
                }
                for (analogsnigs.game.server.Player player : game.players.values()) {
                    addCharacter(player);
                }
                break;
            case Constants.ADD_PLAYER:
                Character player = gson.fromJson(gson.toJson(packet.object), Character.class);
                addCharacter(player);
            case Constants.SEND_PLAYER:
                updateCharacter(gson.fromJson(gson.toJson(packet.object), Character.class));
                break;
            case Constants.REMOVE_PLAYER:
                removeCharacter(gson.fromJson(gson.toJson(packet.object), Character.class));

            default:
        }
    }

    public void updateCharacter(Character player) {
        if(player.name.equals(Player.character.name)) {
            return;
        }
        Character character = Character.characters.get(player.name);

        character.moveToPoint(player.xPos, player.yPos);
        character.setHue(player.hue);
        character.width = player.width;
        character.height = player.height;
    }

    public void addCharacter(Character player) {
        if (!player.name.equals(Player.character.name)) {
            new Character(
                    player.xPos,
                    player.yPos,
                    player.width,
                    player.height,
                    player.name,
                    player.hue
            );
        }
    }

    public void removeCharacter(Character player) {
        if (!player.name.equals(Player.character.name)) {
            Character.characters.get(player.name).deleteCharacter();
        }
    }

    public void disconnect() {
        WebSockets.closeGracefully(socket);
    }

    public void loadMap(int[][] map) {
        this.map.setMap(map, true);
    }

    public void loadMenuPanel(String packet) {
        String[] elements = packet.split("~");

        Player.character.hideNameTag();

        panel.delete();

        panel = new MenuPanel(true);

        for (String element : elements) {
            if(element.contains("&")){

                String[] buttonProperties = element.split("&")[0].split(",");
                String[] elementProperties = element.split("&")[1].split(",");

                Button button = panel.addButton(new Button(
                        Float.parseFloat(buttonProperties[0]),
                        Float.parseFloat(buttonProperties[1]),
                        0,
                        0,
                        Integer.parseInt(buttonProperties[2]),
                        Integer.parseInt(buttonProperties[3]),
                        buttonProperties[4],
                        this::sendPanelData,
                        buttonProperties[5] ));

                if(elementProperties[4].equals("input")) {
                    button.addLinkedElement(new TextInputField(Float.parseFloat(elementProperties[0]),
                            Float.parseFloat(elementProperties[1]),
                            0,
                            0,
                            Integer.parseInt(elementProperties[2]),
                            Integer.parseInt(elementProperties[3]),
                            20
                    ));
                }



            }
            else {
                String[] properties = element.split(",");
                panel.addButton(new Button(
                        Float.parseFloat(properties[0]),
                        Float.parseFloat(properties[1]),
                        0,
                        0,
                        Integer.parseInt(properties[2]),
                        Integer.parseInt(properties[3]),
                        properties[4],
                        this::sendPanelData,
                        properties[5]));
            }



        }


    }

    public void updatePanel() {
        panel.update();
    }

    public void sendPanelData(String s) {
        socket.send("#3" + Player.character.name + "," + s);
        panel.delete();
        panel = new MenuPanel();
        Player.character.showNameTag();
    }

    public void interact(String s) {
        socket.send("#7" + Player.character.name);
    }

    public void connectionError(String reason) {

        Player.character.hideNameTag();

        panel = new MenuPanel(true);

        panel.addUIElements(new TextElement(0.5f, 0.7f, Constants.WALL_SIZE * 6, Constants.WALL_SIZE, reason, 0.6f));

        panel.addButton(new Button(0.5f, 0.4f, 0, 0, Constants.WALL_SIZE * 2, Constants.WALL_SIZE, "CANCEL", analogsnigs.game.Game::loadMenu, "CANCEL"));
    }

}
