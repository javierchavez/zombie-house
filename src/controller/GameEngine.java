package controller;

import model.Character;
import model.House;
import model.Zombie;
import view.*;

import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;


/**
 * Main Controller. Its job is to delegate actions to other controllers and
 * to send data to the renders as needed
 */
public class GameEngine implements KeyListener, MouseInputListener
{
  private House house;
  private Character player;
  private Zombie zombie;
  private Renderer houseRenderer;
  private CharacterController controller;
  private ZombieController zombieController;

  private Renderer playerRenderer;
  private Renderer zombieRenderer;
  private Point2D dragFrom;
  private Converter converter;

  private boolean moving = false;
  private boolean upPressed = false;
  private boolean leftPressed = false;
  private boolean downPressed = false;
  private boolean rightPressed = false;

  private boolean DEBUG = false;

  public GameEngine ()
  {
    player = new Character();
    house = new House(player);
    controller = new CharacterController(house);
    zombieController = new ZombieController(house);

    house.generateRandomHouse();
    playerRenderer = new CharacterRenderer(player, house.getWidth(), house.getHeight());
    zombieRenderer = new ZombieRenderer(house);
    converter = new Converter(house);
    houseRenderer = new HouseRenderer(house, converter);
  }

  public void update(float deltaTime)
  {
    controller.update(deltaTime);
    zombieController.update(deltaTime);

    // Ordinal direction
    if (upPressed && rightPressed) controller.moveUpRight();
    else if (upPressed && leftPressed) controller.moveUpLeft();
    else if (downPressed && rightPressed) controller.moveDownRight();
    else if (downPressed && leftPressed) controller.moveDownLeft();

    // Cardinal directions
    else if (upPressed) controller.moveUp();
    else if (rightPressed) controller.moveRight();
    else if (downPressed) controller.moveDown();
    else if (leftPressed) controller.moveLeft();

    if (!moving) controller.characterIdle();
  }

  public void render (Graphics graphics)
  {
    houseRenderer.render(graphics);
    playerRenderer.render(graphics);
    zombieRenderer.render(graphics);
  }

  @Override
  public void keyTyped (KeyEvent e) { }

  @Override
  public void keyPressed (KeyEvent e)
  {
    switch (e.getKeyCode())
    {
      // Player movement direction
      case KeyEvent.VK_UP:
        upPressed = true;
        moving = true;
        break;
      case KeyEvent.VK_W:
        upPressed = true;
        moving = true;
        break;
      case KeyEvent.VK_DOWN:
        downPressed = true;
        moving = true;
        break;
      case KeyEvent.VK_S:
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
          // Character can only run if they're actually moving
          controller.characterRun();
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
    switch (e.getKeyCode())
    {
      // Player movement/directions
      case KeyEvent.VK_UP:
        upPressed = false;
        moving = false;
        break;
      case KeyEvent.VK_W:
        upPressed = false;
        moving = false;
        break;
      case KeyEvent.VK_DOWN:
        downPressed = false;
        moving = false;
        break;
      case KeyEvent.VK_S:
        downPressed = false;
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

  @Override
  public void mouseClicked (MouseEvent e)
  {
    System.out.println(converter.pointToHouseTile(e.getPoint()));
  }

  @Override
  public void mousePressed (MouseEvent e)
  {
    dragFrom = e.getPoint();
  }

  @Override
  public void mouseReleased (MouseEvent e)
  {

  }

  @Override
  public void mouseEntered (MouseEvent e)
  {

  }

  @Override
  public void mouseExited (MouseEvent e)
  {

  }

  @Override
  public void mouseDragged (MouseEvent e)
  {
    double dx = -(e.getPoint().getX() - dragFrom.getX());
    double dy = -(e.getPoint().getY() - dragFrom.getY());
    dragFrom = e.getPoint();
  }

  @Override
  public void mouseMoved (MouseEvent e) { }
}
