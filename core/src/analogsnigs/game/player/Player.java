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

        double xVel = 0;
        double yVel = 0;

        if(Game.INPUT.isKeyPressed(Input.Keys.W)) {
            yVel += speed * deltaTime;
        }
        if(Game.INPUT.isKeyPressed(Input.Keys.S)) {
            yVel -= speed * deltaTime;
        }
        if(Game.INPUT.isKeyPressed(Input.Keys.D)) {
            xVel += speed * deltaTime;
        }
        if(Game.INPUT.isKeyPressed(Input.Keys.A)) {
            xVel -= speed * deltaTime;
        }



        character.move((int)(character.xPos + xVel), (int)(character.yPos + yVel));
    }
}
