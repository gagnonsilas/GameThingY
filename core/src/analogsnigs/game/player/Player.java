package analogsnigs.game.player;

import analogsnigs.game.Game;
import analogsnigs.game.gameobjects.Character;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;


public class Player {

    public float speed = 400;

    public static Character character;

    public Player(Character character) {
        Player.character = character;
    }

    public void update() {

        float deltaTime = Gdx.graphics.getDeltaTime();

        if(Game.INPUT.isKeyPressed(Input.Keys.W)) {
            character.yPos += speed * deltaTime;

        }
        if(Game.INPUT.isKeyPressed(Input.Keys.S)) {
            character.yPos -= speed * deltaTime;
        }
        if(Game.INPUT.isKeyPressed(Input.Keys.D)) {
            character.xPos += speed * deltaTime;
        }
        if(Game.INPUT.isKeyPressed(Input.Keys.A)) {
            character.xPos -= speed * deltaTime;
        }
    }
}
