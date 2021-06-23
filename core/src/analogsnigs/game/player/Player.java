package analogsnigs.game.player;

import analogsnigs.game.Game;
import analogsnigs.game.utilities.Constants;
import analogsnigs.game.utilities.Joystick;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;


public class Player {

    private transient Joystick joystick;

    public float speed = 400;

    public static Character character;

    public Player(Character character) {
        Player.character = character;
        if(Game.isMobile) {
            joystick = new Joystick(Constants.WALL_SIZE * 2, Constants.WALL_SIZE * 2);
        }
    }

    public void update() {


        float deltaTime = Gdx.graphics.getDeltaTime();

        double xVel = 0;
        double yVel = 0;

        if(Game.isMobile) {
            float[] joystickOut = joystick.checkJoystick();

            xVel = joystickOut[0] * speed * deltaTime;
            yVel = joystickOut[1] * speed * deltaTime;
        }
        else{
            if (Game.INPUT.isKeyPressed(Input.Keys.W)) {
                yVel += speed * deltaTime;
            }
            if (Game.INPUT.isKeyPressed(Input.Keys.S)) {
                yVel -= speed * deltaTime;
            }
            if (Game.INPUT.isKeyPressed(Input.Keys.D)) {
                xVel += speed * deltaTime;
            }
            if (Game.INPUT.isKeyPressed(Input.Keys.A)) {
                xVel -= speed * deltaTime;
            }

        }

        character.move((int)(character.xPos + xVel), (int)(character.yPos + yVel));
    }
}
