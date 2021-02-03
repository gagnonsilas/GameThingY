package analogsnigs.game.scene;

public interface Scene {
    void run();

    void quit();

    int[] getOffset();
}
