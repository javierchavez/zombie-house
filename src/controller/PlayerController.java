package controller;


import model.*;

public class PlayerController extends AbstractCharacterController<Player>
{
  private boolean DEBUG = false;

  public PlayerController (House house)
  {
    super(house, house.getPlayer());
  }


  @Override
  public void checkCollision (float deltaTime)
  {

  }

  // TODO: player stops moving when they run out of stamina
  @Override
  public void update (float deltaTime)
  {
    float playerSpeed;
    // we are inheriting mover from AbstractCharacterController
    // it was set in the Constructor
    mover = house.getPlayer();
    float direction = mover.getRotation();
    float stamina = mover.getStamina();
    float x = mover.getCurrentX();
    float y = mover.getCurrentY();

    if (stamina == 0)
    {
      isMoving = false;
      mover.setSpeed(Mover.IDLE);
    }
    // Stamina regenerates if mover is idle
    if (!isMoving)
    {
      if (stamina < 5.0)
      {
        stamina += 0.2 * deltaTime + 0.01; // Stamina regenerates faster if mover is not moving?
        if (stamina > 5) stamina = 5;
        mover.setStamina(stamina);
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
          mover.setStamina(stamina);
        }
      }
      else // if player is not running
      {
        playerSpeed = Mover.WALK_SPEED;
        if (stamina < 5.0)
        {
          stamina += 0.2 * deltaTime;
          if (stamina > 5) stamina = 5;
          mover.setStamina(stamina);
        }
      }

      // Distance of player is dependent on time
      mover.setSpeed(
              playerSpeed * deltaTime); // Determines how many tiles/second the
      // player will move across
      mover.setRotation(direction);

      // Update player's x and y

      if (moveUp || moveDown) y = (float) (y + mover.getSpeed() * Math.sin(
              Math.toRadians(direction)));
      if (moveLeft || moveRight) x = (float) (x + mover.getSpeed() * Math.cos(
              Math.toRadians(direction)));

      mover.move(x, y);
      isMoving = false;
    }
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
      mover.pickupTrap(tile);
    }
    else
    {
      int numTraps = mover.trapsAvailable();
      if (numTraps > 0) mover.dropTrap(tile);
    }
  }
}