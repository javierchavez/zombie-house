package controller;


import model.Character;
import model.House;
import model.Mover;
import model.Tile;

public class CharacterController
{
  private final House house;
  private Character player;
  private boolean isMoving = false, running = false, idling = true;
  private float direction;
  private boolean moveUp, moveDown, moveLeft, moveRight;

  private final int NORTHEAST = 315;
  private final int NORTHWEST = 225;
  private final int SOUTHEAST = 45;
  private final int SOUTHWEST = 135;

  private boolean DEBUG = false;

  public CharacterController (House house)
  {
    this.house = house;
  }

  /**
   * collision detection
   */
  public void checkCollision (float deltaTime)
  {
    // TODO:
  }


  // TODO: player stops moving when they run out of stamina
  public void update (float deltaTime)
  {
    float playerSpeed;
    player = house.getPlayer();
    float stamina = player.getStamina();
    float x = player.getCurrentX();
    float y = player.getCurrentY();

    if (stamina == 0)
    {
      isMoving = false;
      player.setSpeed(Mover.IDLE);
    }
    // Stamina regenerates if player is idle
    if (!isMoving)
    {
      if (stamina < 5.0)
      {
        stamina += 0.2 * deltaTime + 0.01; // Stamina regenerates faster if player is not moving?
        if (stamina > 5) stamina = 5;
        player.setStamina(stamina);
      }
    }

    if (isMoving)
    {
      if (running)
      {
        playerSpeed = Mover.RUN_SPEED;
        if (stamina > 0)
        {
          stamina -= deltaTime;
          if (stamina < 0) stamina = 0;
          if (stamina == 0)
          {
            running = false;
          }
          player.setStamina(stamina);
        }
      }
      else // if player is not running
      {
        playerSpeed = Mover.WALK_SPEED;
        if (stamina < 5.0)
        {
          stamina += 0.2 * deltaTime;
          if (stamina > 5) stamina = 5;
          player.setStamina(stamina);
        }
      }

      // Distance of player is dependent on time
      player.setSpeed(
              playerSpeed * deltaTime); // Determines how many tiles/second the
      // player will move across
      player.setRotation(direction);

      // Update player's x and y

      if (moveUp || moveDown) y = (float) (y + player.getSpeed() * Math.sin(
              Math.toRadians(direction)));
      if (moveLeft || moveRight) x = (float) (x + player.getSpeed() * Math.cos(
              Math.toRadians(direction)));

      player.move(x, y);
      isMoving = false;
    }
  }

  /**
   * If 'R' is pressed, player's speed is updated to 2.0, making them two times faster.
   */
  public void characterRun ()
  {
    isMoving = true;
    if (player.getStamina() > 0) running = true;
  }

  /**
   * When 'R' is released, player's speed drops back down to 1.0, the default speed.
   */
  public void characterWalk ()
  {
    isMoving = true;
    running = false;
    idling = false;
  }

  /**
   * When the character isn't moving at all
   */
  public void characterIdle ()
  {
    //    if (DEBUG) System.out.println("Idling...");
    idling = true;
    isMoving = false;
    running = false;
    player.setSpeed(Mover.IDLE);
  }

  /**
   * If two incompatible directions are pressed;
   */
  public void stopMoving() // TODO: get rid of this method later? probably don't need it
  {
    System.out.println("I can't move like that!");
    isMoving = false;
    running = false;
    player.setSpeed(0);
  }

  /**
   * If 'P' is pressed.
   */
  public void trapInteraction ()
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
  public void moveUp ()
  {
    if (DEBUG) System.out.println("Moving up");
    isMoving = true;
    idling = false;
    moveUp = true;
    direction = Mover.NORTH; // Change player's direction
  }

  /**
   * If 'S' or down arrow is pressed.
   */
  public void moveDown ()
  {
    if (DEBUG) System.out.println("Moving down");
    isMoving = true;
    idling = false;
    moveDown = true;
    direction = Mover.SOUTH;
  }

  /**
   * If 'A' or left arrow is pressed.
   */
  public void moveLeft ()
  {
    if (DEBUG) System.out.println("Moving left");
    isMoving = true;
    idling = false;
    moveLeft = true;
    direction = Mover.WEST;
  }

  /**
   * If 'D' or right arrow is pressed.
   */
  public void moveRight ()
  {
    if (DEBUG) System.out.println("Moving right");
    isMoving = true;
    idling = false;
    moveRight = true;
    direction = Mover.EAST;
  }

  public void moveUpRight()
  {
    if (DEBUG) System.out.println("Moving up right");
    isMoving = true;
    idling = false;
    direction = NORTHEAST;
  }

  public void moveUpLeft()
  {
    isMoving = true;
    idling = false;
    direction = NORTHWEST;
  }

  public void moveDownRight()
  {
    isMoving = true;
    idling = false;
    direction = SOUTHEAST;
  }

  public void moveDownLeft()
  {
    isMoving = true;
    idling = false;
    direction = SOUTHWEST;
  }
}
