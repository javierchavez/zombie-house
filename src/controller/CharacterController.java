package controller;


import model.Character;
import model.House;
import model.Tile;

public class CharacterController
{
  private final House house;
  Character player;
  private boolean isMoving = false, running = false;
  private int direction;
  private float x, y;

  private boolean moveUp = false, moveDown, moveLeft, moveRight;

  private boolean DEBUG = true;

  // TODO: update object's stamina when running/after running
  // TODO: collision detection

  public CharacterController (House house)
  {
    this.house = house;
  }

  /**
   *
   * collision detection
   */
  public void checkCollision(float deltaTime)
  {

  }


  public void update (float deltaTime)
  {
//    Character player = house.getPlayer();
    float playerSpeed;
    player = house.getPlayer();
    x = player.getCurrentX();
    y = player.getCurrentY();

    if (isMoving)
    {
      if (running) playerSpeed = 2.0f;
      else playerSpeed = 1.0f;

      // Distance of player is dependent on time
      player.setSpeed(playerSpeed * deltaTime); // Determines how many tiles/second the player will move across
      player.setRotation(direction);

      // Update player's x and y
      if (moveUp || moveDown) y = (float) (y + player.getSpeed() * Math.sin(direction));
      if (moveLeft || moveRight) x = (float) (x + player.getSpeed() * Math.cos(direction));

      player.move(x, y);
      isMoving = false;
    }
  }

  /**
   * If 'R' is pressed, player's speed is updated to 2.0, making them two times faster.
   */
  public void characterRun()
  {
    isMoving = true;
    player.setSpeed(2.0f); // Running speed
  }

  /**
   * When 'R' is released, player's speed drops back down to 1.0, the default speed.
   */
  public void characterWalk()
  {
    isMoving = true;
    player.setSpeed(1.0f); // Default speed
  }

  /**
   * When the character isn't moving at all
   */
  public void characterIdle()
  {
//    if (DEBUG) System.out.println("Idling...");
    isMoving = false;
    player.setSpeed(0.0f);
  }

  /**
   * If 'P' is pressed.
   */
  public void trapInteraction()
  {
    if (DEBUG) System.out.println("P pressed");
    Tile tile = house.getPlayerTile();
    if (house.isTrap(tile))
    {
      player.pickupTrap(tile);
    }
    else
    {
      int numTraps = player.trapsAvailable();
      if (numTraps > 0) player.dropTrap(tile);
    }
  }

  /**
   * If 'W' or up arrow is pressed.
   */
  public void moveUp()
  {
    if (DEBUG) System.out.println("Moving up");
    isMoving = true;
    moveUp = true;
    direction = 90; // Change player's direction
  }

  /**
   * If 'S' or down arrow is pressed.
   */
  public void moveDown()
  {
    if (DEBUG) System.out.println("Moving down");
    isMoving = true;
    moveDown = true;
    direction = 270;
  }

  /**
   * If 'A' or left arrow is pressed.
   */
  public void moveLeft()
  {
    if (DEBUG) System.out.println("Moving left");
    isMoving = true;
    moveLeft = true;
    direction = 180;
  }

  /**
   * If 'D' or right arrow is pressed.
   */
  public void moveRight()
  {
    if (DEBUG) System.out.println("Moving right");
    isMoving = true;
    moveRight = true;
    direction = 0;
  }
}
