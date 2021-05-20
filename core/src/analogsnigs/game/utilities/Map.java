package analogsnigs.game.utilities;

import analogsnigs.game.Game;
import analogsnigs.game.gameobjects.GameElement;
import analogsnigs.game.gameobjects.GameObject;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Map {

    int[][] map;

    Array<GameObject> objectMap = new Array<>();
    private static int[][][] tiles;

    public static void loadMapAutoTiler() {
        tiles = new int[][][] {
                // Bottom Left Corner
                {
                        {0, 3, 0},
                        {1, 0, 3},
                        {0, 1, 0},
                        {0, 2}
                },
                // Top Left Corner
                {
                        {0, 0, 0},
                        {1, 0, 3},
                        {0, 3, 0},
                        {0, 0}
                },
                // Left Wall
                {
                        {0, 0, 0},
                        {1, 0, 0},
                        {0, 0, 0},
                        {0, 1}
                },
                // Bottom Right Corner
                {
                        {0, 3, 0},
                        {3, 0, 1},
                        {0, 1, 0},
                        {2, 2}
                },
                // Top Right corner
                {
                        {0, 0, 0},
                        {3, 0, 1},
                        {0, 3, 0},
                        {2, 0}
                },
                // Right Wall
                {
                        {0, 0, 0},
                        {0, 0, 1},
                        {0, 0, 0},
                        {2, 1}
                },
                // Right Upwards Corner Wall
                {
                        {0, 2, 0},
                        {0, 0, 3},
                        {0, 3, 0},
                        {3, 0}
                },
                // Left Upwards Corner Wall
                {
                        {0, 2, 0},
                        {3, 0, 0},
                        {0, 3, 0},
                        {4, 0}
                },
                // Right Downwards Corner Wall
                {
                        {0, 3, 0},
                        {2, 0, 0},
                        {0, 0, 0},
                        {3, 1}
                },
                // Left Downwards Corner Wall
                {
                        {0, 3, 0},
                        {0, 0, 2},
                        {0, 0, 0},
                        {4, 1}
                },
                // Regular Wall
                {
                        {0, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0},
                        {1, 0}
                },
        };
    }

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
                if(map[i][j] == 0) {
                    continue;
                }
                if(map[i][j] == 1) {
                    objectMap.add(new GameElement(j * Game.WALL_SIZE,
                            (map.length - i )* Game.WALL_SIZE,
                            0,
                            new TextureRegion(Game.MAP_TEXTURES, 16, 16, 16, 16)));
                    continue;
                }
                
                // Loop through each tile type
                for (int[][] tile : tiles) {
                    if(tileTypeWorks(tile, i, j)) {
                        objectMap.add(new GameElement(j * Game.WALL_SIZE,
                                        (map.length - i )* Game.WALL_SIZE,
                                        (map[i][j] != 2 || !hasColliders?0:1),
                                        new TextureRegion(Game.MAP_TEXTURES, tile[3][0]*16, tile[3][1]*16, 16, 16)));
                        break;
                    }
                }


            }
        }
    }

    private boolean tileTypeWorks(int[][] testGrid, int i, int j) {
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                if(testGrid[k][l] != 0 && pos(k + i - 1, l + j - 1)  != testGrid[k][l] - 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public String getMapAsString() {
        StringBuilder trimmedMap = new StringBuilder();
        for (int[] row : trimMap()) {
            trimmedMap.append("\"");
            for (int column : row) {
                trimmedMap.append(column).append(",");
            }

            trimmedMap.deleteCharAt(trimmedMap.length() - 1);

            trimmedMap.append("\" + \n");

        }

        return trimmedMap.toString();
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
            if (rightBound + 1 - leftBound >= 0)
                System.arraycopy(map[i], leftBound, newMap[i - topBound], 0, rightBound + 1 - leftBound);
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
