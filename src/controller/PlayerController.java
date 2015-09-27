package controller;


import common.Speed;
import model.*;
import model.Move;

public class PlayerController extends AbstractCharacterController<Player>
{
  private int trapSetTimer = 0;
  private final int TRAP_SET_TIME = 60 * 1; // Takes 5 seconds to set/pick up traps
  private boolean pKeyPressed = false;

  Tile playerTile;

  private boolean DEBUG = false;

  public PlayerController (House house)
  {
    super(house, house.getPlayer());
  }

  @Override
  public void update (float deltaTime)
  {
    float playerSpeed;
    // we are inheriting mover from AbstractCharacterController
    // it was set in the Constructor
    mover = house.getPlayer();
    playerTile = house.getCharacterTile(mover);

    float direction = mover.getRotation();
    float stamina = mover.getStamina();
    float x = mover.getCurrentX();
    float y = mover.getCurrentY();

    if (stamina == 0)
    {
      isMoving = false;
      mover.setSpeed(Speed.IDLE);
    }

    if (!isMoving)
    {
      // Stamina regeneration
      if (stamina < 5.0)
      {
        stamina += 0.2 * deltaTime + 0.01; // Stamina regenerates faster if mover is not moving
        if (stamina > 5) stamina = 5;
        mover.setStamina(stamina);
      }

      // Trap interaction
      if (pKeyPressed) trapInteraction();
    }

    if (isMoving)
    {
      if (running)
      {
        playerSpeed = Speed.RUN;
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
        playerSpeed = Speed.WALK;
        if (stamina < 5.0)
        {
          stamina += 0.2 * deltaTime;
          if (stamina > 5) stamina = 5;
          mover.setStamina(stamina);
        }
      }

      mover.setSpeed(playerSpeed * deltaTime);
      mover.setRotation(direction);

      if (moveUp || moveDown) y = (float) (y + mover.getSpeed() * Math.sin(Math.toRadians(direction)));
      if (moveLeft || moveRight) x = (float) (x + mover.getSpeed() * Math.cos(Math.toRadians(direction)));

      checkCollision(new Move(x,y, (int) direction));
      isMoving = false;
    }
  }

  /**
   * If 'P' is pressed.
   */
  public void trapKeyPressed()
  {
    pKeyPressed = true;
    trapSetTimer++;
  }

  public void trapKeyReleased()
  {
    pKeyPressed = false;
    trapSetTimer = 0;
  }

  /**
   * If 'P' key is held down for long enough for player to pick up/set trap.
   */
  public void trapInteraction()
  {
    Tile tile = house.getCharacterTile(house.getPlayer());
    if (house.isTrap(tile))
    {
//      if (DEBUG) System.out.println("PICKING UP TRAP");
      if (trapSetTimer % TRAP_SET_TIME == 0) mover.pickupTrap(tile);
    }
    else
    {
//      if (DEBUG) System.out.println("SETTING TRAP");
      int numTraps = mover.trapsAvailable();
      if (trapSetTimer % TRAP_SET_TIME == 0)
      {
        if (numTraps > 0) mover.dropTrap(tile);
      }
    }
  }

  @Override
  public boolean checkCollision(Move moveToCheck)
  {
    //Area testArea = new Area(moveToCheck.col,
    //    moveToCheck.row,
    //    mover.getWidth(),
    //    mover.getHeight());

    //List<Tile> neighbors = house.getIntersectingNeighbors(playerTile, testArea);

    // Fire traps combust if player runs over them
    //for (Tile neighbor : neighbors)
    //{
    //  if (neighbor.getTrap() == Trap.FIRE)
    //  {
    //    if (running)
    //    {
    //      List<Combustible> explode = house.getCombustibleNeighbors(neighbor);
    //      for (Combustible item : explode)
    //      {
    //        CombustibleController.getInstance().addCombustible(item);
    //      }
    //      CombustibleController.getInstance().addCombustible(neighbor);
    //    }
    //  }
    //}

    if (house.getCharacterTile(mover) == house.getExit())
    {
      // We have a winner!!!!
      // not sure what flag to set here
    }

    if (super.checkCollision(moveToCheck))
    {
      mover.setSpeed(0);
    }
    else
    {
      mover.move(moveToCheck.col, moveToCheck.row);
    }

    return false;
  }
}
