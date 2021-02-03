package analogsnigs.game.utilities;

import analogsnigs.game.Game;
import analogsnigs.game.gameobjects.GameElement;
import analogsnigs.game.gameobjects.GameObject;
import com.badlogic.gdx.utils.Array;

public class Map {

    int[][] map;

    Array<GameObject> objectMap = new Array<>();

    public Map(int[][] map) {
        setMap(map);
    }

    public void setMap(int[][] map) {
        this.map = map;
        loadMap();
    }

    public void loadMap() {
        for (GameObject object : objectMap) {
            object.delete();
        }

        objectMap = new Array<>();

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                objectMap.add(new GameElement(j * Game.WALL_SIZE,
                        (map.length - i )* Game.WALL_SIZE,
                        map[i][j],
                        0,
                        map[i][j] == 0 || map[i][j] == 9?0:1));

            }
        }
    }
}
