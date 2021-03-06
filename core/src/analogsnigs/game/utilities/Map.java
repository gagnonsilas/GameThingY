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

                int textureX = 0;

                if(map[i][j] == 2) {
                    continue;
                }
                if(map[i][j] == 0) {
                    textureX = 0;
                }
                else if(pos(i, j - 1) == 2) {
                    textureX = pos(i + 1, j) == 1? 3 : 5;
                }
                else if(pos(i, j + 1) == 2) {
                    textureX = pos(i + 1, j) == 1? 4 : 6;
                }
                else if(pos(i + 1,j) == 0 || pos(i + 1,j) == 2) {
                    textureX = 1;
                }
                else if (pos(i + 1, j) == 1
                        && pos(i - 1, j) != 2
                        && pos(i, j + 1) == 1
                        && pos(i, j - 1) != 2
                        && pos(i + 1, j + 1) == 2){
                    textureX = 7;

                }
                else if (pos(i + 1, j) == 1
                        && pos(i - 1, j) != 2
                        && pos(i, j - 1) == 1
                        && pos(i, j + 1) != 2
                        && pos(i + 1, j - 1) == 2){
                    textureX = 8;

                }
                else {
                    textureX = 2;
                }
                objectMap.add(new GameElement(j * Game.WALL_SIZE,
                        (map.length - i )* Game.WALL_SIZE,
                        textureX,
                        0,
                        map[i][j] == 0 || map[i][j] == 9?0:1));

            }
        }
    }

    public int pos(int i, int j) {
        if(i < 0 || j < 0 || i >= map.length || j >= map[i].length) {
            return 2;
        }
        else {
            return map[i][j];
        }
    }
}
