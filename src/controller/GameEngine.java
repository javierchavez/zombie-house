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

  public GameEngine ()
  {
    player = new Character();
    player.move(House.WIDTH / 2, House.HEIGHT / 2);
    house = new House(player);

    houseRenderer = new HouseRenderer(house);
    playerRenderer = new CharacterRenderer(player);

    controller = new CharacterController(house);
  }

  public void update(float deltaTime)
  {
    controller.update(deltaTime);
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
    if (e.getID() == KeyEvent.VK_R)
    {
      controller.characterRun();
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

    switch (e.getID()) {
      // PLAYER DIRECTION //
      // Arrow keys
      case KeyEvent.VK_UP:
        controller.moveUp();
        break;
      case KeyEvent.VK_LEFT:
        controller.moveLeft();
        break;
      case KeyEvent.VK_DOWN:
        controller.moveDown();
        break;
      case KeyEvent.VK_RIGHT:
        controller.moveRight();
        break;

      // WASD controls
      case KeyEvent.VK_W:
        controller.moveUp();
        break;
      case KeyEvent.VK_A:
        controller.moveLeft();
        break;
      case KeyEvent.VK_S:
        controller.moveDown();
        break;
      case KeyEvent.VK_D:
        controller.moveRight();
        break;

      // PLAYER ACTIONS //
      case KeyEvent.VK_R:
        controller.characterWalk();
        break;
      case KeyEvent.VK_P:
        controller.trapInteraction();
        break;
    }
  }
}
