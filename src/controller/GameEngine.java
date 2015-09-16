package controller;

import model.Character;
import model.House;
import view.CharacterRenderer;
import view.HouseRenderer;
import view.Renderer;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * Main Controller. Its job is to delegate actions to other controllers and
 * to send data to the renders as needed
 */
public class GameEngine implements KeyListener
{
  private House house;
  private Character player;
  private Renderer houseRenderer;
  private CharacterController controller;
  private Renderer playerRenderer;

  private boolean moving = false;
  private boolean upPressed = false;
  private boolean leftPressed = false;
  private boolean downPressed = false;
  private boolean rightPressed = false;

  private boolean DEBUG = true;

  public GameEngine ()
  {
    player = new Character();
    house = new House(player);

    // player.move(House.WIDTH / 2, House.HEIGHT / 2);

    houseRenderer = new HouseRenderer(house);
    playerRenderer = new CharacterRenderer(player);

    controller = new CharacterController(house);

    house.generateRandomHouse();
  }

  public void update(float deltaTime)
  {
    controller.update(deltaTime);
    if (upPressed) controller.moveUp();
    if (downPressed) controller.moveDown();
    if (leftPressed) controller.moveLeft();
    if (rightPressed) controller.moveRight();
    if (!moving) controller.characterIdle();
  }

  public void render (Graphics graphics)
  {
    houseRenderer.render(graphics);
    playerRenderer.render(graphics);
  }

  @Override
  public void keyTyped (KeyEvent e) { }

  @Override
  public void keyPressed (KeyEvent e)
  {
    switch (e.getKeyCode())
    {
      // Player directions
      case KeyEvent.VK_UP:
        upPressed = true;
        moving = true;
        break;
      case KeyEvent.VK_S:
        upPressed = true;
        moving = true;
        break;
      case KeyEvent.VK_DOWN:
        downPressed = true;
        moving = true;
        break;
      case KeyEvent.VK_LEFT:
        leftPressed = true;
        moving = true;
        break;
      case KeyEvent.VK_A:
        leftPressed = true;
        moving = true;
        break;
      case KeyEvent.VK_RIGHT:
        rightPressed = true;
        moving = true;
        break;
      case KeyEvent.VK_D:
        rightPressed = true;
        moving = true;
        break;

      // Running
      case KeyEvent.VK_R:
        if (moving)
        {
          if (DEBUG) System.out.println("Running");
          controller.characterRun(); // Character can only run if they're actually moving
        }
        break;
      default:
        moving = false;
        controller.characterIdle();
    }

  }

  @Override
  public void keyReleased (KeyEvent e)
  {
    // update player speed when r pressed

    // updated player direction when a,s,w,d up,down,left,right is pressed.
    // look at mover interface for values.. decide whether holding down a key
    // continues to rotate or if a sharp rotate. here are the key codes
    // http://docs.oracle.com/javase/7/docs/api/java/awt/event/KeyEvent.html

    // if p is pressed then call character controller. ( probably need to
    // create new methods in character controller) because the you need to
    // check if player has firetraps and and also if the current tile has a
    // trap to pickup in trap there is enum class of fire traps.

    switch (e.getKeyCode())
    {
      // Player movement/directions
      case KeyEvent.VK_UP:
        upPressed = false;
        moving = false;
        break;
      case KeyEvent.VK_S:
        upPressed = false;
        break;
      case KeyEvent.VK_DOWN:
        downPressed = false;
        moving = false;
        break;
      case KeyEvent.VK_LEFT:
        leftPressed = false;
        moving = false;
        break;
      case KeyEvent.VK_A:
        leftPressed = false;
        moving = false;
        break;
      case KeyEvent.VK_RIGHT:
        rightPressed = false;
        moving = false;
        break;
      case KeyEvent.VK_D:
        rightPressed = false;
        moving = false;
        break;

      // Player actions
      case KeyEvent.VK_R:
        if (moving)
        {
          if (DEBUG) System.out.println("Not running");
          controller.characterWalk();
        }
        break;
      case KeyEvent.VK_P:
        controller.trapInteraction();
        break;
      default:
        moving = false;
        controller.characterIdle();
    }
  }
}
