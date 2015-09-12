package controller;


import model.Character;
import model.House;
import model.Tile;

public class CharacterController
{
  private final House house;
  Character player;
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
//    Character player = house.getPlayer();
    player = house.getPlayer();
    float playerSpeed = player.getSpeed();

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

  public void characterRun()
  {
    // TODO
  }

  /**
   * If 'P' is pressed.
   */
  public void trapInteraction()
  {
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
    direction = 90;
  }

  /**
   * If 'A' or left arrow is pressed.
   */
  public void moveLeft()
  {
    direction = 180;
  }

  /**
   * If 'S' or down arrow is pressed.
   */
  public void moveDown()
  {
    direction = 270;
  }

  /**
   * If 'D' or right arrow is pressed.
   */
  public void moveRight()
  {
    direction = 0;
  }

}
