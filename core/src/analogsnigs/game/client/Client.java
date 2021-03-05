package analogsnigs.game.client;

import analogsnigs.game.player.Player;
import analogsnigs.game.utilities.Map;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketListener;
import com.github.czyzby.websocket.WebSockets;
import com.github.czyzby.websocket.data.WebSocketCloseCode;
import analogsnigs.game.gameobjects.Character;

public class Client {
    WebSocket socket;

    Map map;

    public Client() {
//        socket = WebSockets.newSocket("wss://analog-snigs-games.herokuapp.com");
        socket = WebSockets.newSocket("ws://localhost:8753");
        socket.setSendGracefully(true);
        socket.addListener(new WebSocketListener() {
            @Override
            public boolean onOpen(WebSocket webSocket) {
                webSocket.send("#0" + Player.character.name);
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
        String output = "#1" + Player.character.name + "," +
                Player.character.xPos + "," +
                Player.character.yPos + "," +
                Player.character.width + "," +
                Player.character.height + "," +
                Player.character.hue + ",";
        socket.send(output);
    }

    public void parsePacket(String packet) {
        if(packet.contains("#1")) {
            characterUpdate(packet.substring(2));
        }
        else if(packet.contains("#2")) {
            addCharacter(packet.substring(2));
        }
        else if(packet.contains("#3")) {
            System.out.println(packet.substring(2));
        }
        else if(packet.contains("#4")) {
            loadMap(stringTo2DArray(packet.substring(2)));
        }
        else if(packet.contains("#5")) {
            loadMenuPanel(packet.substring(2));
        }


    }

    public int[][] stringTo2DArray(String input) {

        String[] inputSplit = input.split("~");

        String[][] inputSplit2 = new String[inputSplit.length][];

        for (int i = 0; i < inputSplit.length; i++) {
            inputSplit2[i] = inputSplit[i].split(",");
        }

        int[][] outputArray = new int[inputSplit2.length][inputSplit2[0].length];

        try{
            for (int i = 0; i < inputSplit2.length; i++) {
                for (int j = 0; j < inputSplit2[i].length; j++) {
                    outputArray[i][j] = Integer.parseInt(inputSplit2[i][j]);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return outputArray;
    }

    public void characterUpdate(String packet) {
        String[] characters = packet.split("~");
        for (String character : characters) {
            String[] characterData = character.split(",");
            if(characterData[0].equals(Player.character.name)) {
                continue;
            }
            Character.findCharacterByName(characterData[0]).move(Integer.parseInt(characterData[1]),
                    Integer.parseInt(characterData[2]));
            Character.findCharacterByName(characterData[0]).setHue(Integer.parseInt(characterData[5]));
        }
    }

    public void addCharacter(String packet) {

        String[] characters = packet.split("~");
        for (String character : characters) {
            String[] characterData = character.split(",");
            if(characterData[1].equals("exit")) {
                Character.findCharacterByName(characterData[0]).deleteCharacter();
            }
            else if(!characterData[0].equals(Player.character.name)){
                System.out.println(characterData[0] + " Joined The Game");
                new Character(Integer.parseInt(characterData[2]),
                        Integer.parseInt(characterData[3]),
                        Integer.parseInt(characterData[4]),
                        Integer.parseInt(characterData[5]),
                        characterData[0],
                        Integer.parseInt(characterData[6]));
            }
        }
    }

    public void disconnect() {
        WebSockets.closeGracefully(socket);
    }

    public void loadMap(int[][] map) {
        this.map = new Map(map);
    }

    public void loadMenuPanel(String packet) {
        String[] elements = packet.split("~");

    }
}
