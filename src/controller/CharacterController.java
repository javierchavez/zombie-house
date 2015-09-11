package controller;


import model.Character;
import model.House;

public class CharacterController
{
  private final House house;
  private boolean isMoving = false;
  private int direction;

  ///////////////////////////////////////////////////////////////////////////////////////////
  // Placeholder variables until I know how to get this information from the other classes //
  ///////////////////////////////////////////////////////////////////////////////////////////
  private boolean pickup = false;
  private int traps = 0;

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
    Character player = house.getPlayer();
    // change the player's x and y
    // the distance of the player is dependant on time...
    // the player's x and y are in Pixels

    if (isMoving)
    {
      // TODO: do stuff
      // playerSpeed * deltaTime determines how many tiles/second player will move across??
      player.setRotation(direction);
      isMoving = false;
    }
  }

  /**
   * To pick up a trap.
   */
  public void pickup()
  {
    if (pickup)
    {
      // pick up trap
      traps++;
    }
  }

  /**
   * To set a trap.
   */
  public void setTrap()
  {
    if (!pickup)
    {
      // set trap
      traps--;
    }
  }

  /**
   * If w or up arrow is pressed.
   */
  public void moveUp()
  {
    direction = 90;
  }

  /**
   * If a or left arrow is pressed.
   */
  public void moveLeft()
  {
    direction = 180;
  }

  /**
   * If s or down arrow is pressed.
   */
  public void moveDown()
  {
    direction = 270;
  }

  /**
   * If d or right arrow is pressed.
   */
  public void moveRight()
  {
    direction = 0;
  }

}
