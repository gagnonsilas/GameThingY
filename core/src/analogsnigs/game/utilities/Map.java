package analogsnigs.game.utilities;

import analogsnigs.game.Game;
import analogsnigs.game.gameobjects.GameElement;
import analogsnigs.game.gameobjects.GameObject;
import com.badlogic.gdx.utils.Array;

public class Map {

    int[][] map;

    Array<GameObject> objectMap = new Array<>();

    public Map(int[][] map) {
        setMap(map, true);
    }

    public void setMap(int[][] map, boolean hasColliders) {
        this.map = map;
        loadMap(hasColliders);
    }

    public void loadMap(boolean hasColliders) {
        for (GameObject object : objectMap) {
            object.delete();
        }

        objectMap = new Array<>();

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {

                int textureX;

                if(map[i][j] == 0) {
                    continue;
                }
                if(map[i][j] == 1) {
                    textureX = 0;
                }
                else if(pos(i, j - 1) == 0) {
                    textureX = pos(i + 1, j) == 2? 3 : 5;
                }
                else if(pos(i, j + 1) == 0) {
                    textureX = pos(i + 1, j) == 2? 4 : 6;
                }
                else if(pos(i + 1,j) == 1 || pos(i + 1,j) == 0) {
                    textureX = 1;
                }
                else if (pos(i + 1, j) == 2
                        && pos(i - 1, j) != 0
                        && pos(i, j + 1) == 2
                        && pos(i, j - 1) != 0
                        && pos(i + 1, j + 1) == 0){
                    textureX = 7;

                }
                else if (pos(i + 1, j) == 2
                        && pos(i - 1, j) != 0
                        && pos(i, j - 1) == 2
                        && pos(i, j + 1) != 0
                        && pos(i + 1, j - 1) == 0){
                    textureX = 8;

                }
                else {
                    textureX = 2;
                }
                objectMap.add(new GameElement(j * Game.WALL_SIZE,
                        (map.length - i )* Game.WALL_SIZE,
                        textureX,
                        0,
                        map[i][j] != 2 || !hasColliders?0:1));

            }
        }
    }

    public void printMap() {
        for (int[] row : trimMap()) {
            System.out.print("\"");
            for (int column : row) {
                System.out.print(column + ",");
            }
            System.out.println("\b~\" +");
        }
    }

    public int[][] trimMap() {

        int topBound = 0;
        int bottomBound = 0;
        int leftBound = 0;
        int rightBound = 0;

        topBound:
        for (int i = 0; i < map.length; i++) {
            for (int column : map[i]) {
                if (column != 0) {
                    topBound = i;
                    break topBound;
                }
            }
        }

        bottomBound:
        for (int i = map.length - 1; i >= 0; i--) {
            for (int column : map[i]) {
                if (column != 0) {
                    bottomBound = i;
                    break bottomBound;
                }
            }
        }

        leftBound:
        for (int i = 0; i < map[0].length; i++) {
            for (int[] row : map) {
                if(row[i] != 0) {
                    leftBound = i;
                    break leftBound;
                }
            }
        }

        rightBound:
        for (int i = map[0].length - 1; i >= 0; i--) {
            for (int[] row : map) {
                if(row[i] != 0) {
                    rightBound = i;
                    break rightBound;
                }
            }
        }

        int[][] newMap = new int[bottomBound - topBound + 1][rightBound - leftBound + 1];

        for (int i = topBound; i <= bottomBound; i++) {
            for (int j = leftBound; j <= rightBound; j++) {
                newMap[i-topBound][j-leftBound] = map[i][j];
            }
        }

        return newMap;
    }

    public int pos(int i, int j) {
        if(i < 0 || j < 0 || i >= map.length || j >= map[i].length) {
            return 0;
        }
        else {
            return map[i][j];
        }
    }

    public void setPos(int x, int inY, int set) {
        int y = map.length - inY;
        if(x >= 0 && y >= 0 && x < map[0].length && y < map.length) {
            map[y][x] = set;
            loadMap(false);
        }
    }
}
