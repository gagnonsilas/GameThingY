package analogsnigs.game.player;

import analogsnigs.game.Game;
import analogsnigs.game.gameobjects.Character;
import analogsnigs.game.utilities.Joystick;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;


public class Player {

    private Joystick joystick;

    public float speed = 400;

    public static Character character;

    public Player(Character character) {
        Player.character = character;
        joystick = new Joystick(Game.WALL_SIZE * 2, Game.WALL_SIZE * 2);
    }

    public void update() {


        float deltaTime = Gdx.graphics.getDeltaTime();

        double xVel = 0;
        double yVel = 0;

        float[] joystickOut = joystick.checkJoystick();

        xVel = joystickOut[0] * speed * deltaTime;
        yVel = joystickOut[1] * speed * deltaTime;

//        System.out.println(joystickOut[0]);
//        System.out.println(joystickOut[1]);


//        if(Game.INPUT.isKeyPressed(Input.Keys.W)) {
//            yVel += speed * deltaTime;
//        }
//        if(Game.INPUT.isKeyPressed(Input.Keys.S)) {
//            yVel -= speed * deltaTime;
//        }
//        if(Game.INPUT.isKeyPressed(Input.Keys.D)) {
//            xVel += speed * deltaTime;
//        }
//        if(Game.INPUT.isKeyPressed(Input.Keys.A)) {
//            xVel -= speed * deltaTime;
//        }



        character.move((int)(character.xPos + xVel), (int)(character.yPos + yVel));
    }
}
