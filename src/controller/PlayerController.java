package controller;


import common.Speed;
import model.*;
import model.Move;

import java.util.List;

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

//      if (DEBUG) System.out.println("trap timer: " + trapSetTimer);
      // Detecting if player is on trap tile
      //  TODO: just for testing. Get rid of this later
      if (DEBUG)
      {
        Tile tile = house.getCharacterTile(house.getPlayer());
        if (house.isTrap(tile)) System.out.println("On trap tile");
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

      // Distance of player is dependent on time
      mover.setSpeed(playerSpeed * deltaTime); // Determines how many tiles/second the player will move across
      mover.setRotation(direction);

      // Update player's x and y

      if (moveUp || moveDown) y = (float) (y + mover.getSpeed() * Math.sin(Math.toRadians(direction)));
      if (moveLeft || moveRight) x = (float) (x + mover.getSpeed() * Math.cos(Math.toRadians(direction)));

      checkCollision(new Move(x,y, (int) direction));
      // mover.move(x, y);
      isMoving = false;
      for (Zombie zombie : house.getZombies())
      {
        boolean canHear = mover.sense(house.getCharacterTile(zombie),
                                      house.getCharacterTile(mover));
        if (canHear)
        {
          float theta = mover.getDirectionCardinal(zombie);
          zombie.setChannel(theta);
        }
      }
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
    Area testArea = new Area(moveToCheck.col,
        moveToCheck.row,
        mover.getWidth(),
        mover.getHeight());

    List<Tile> neighbors = house.getIntersectingNeighbors(playerTile, testArea);

    // Fire traps combust if player runs over them
    for (Tile neighbor : neighbors)
    {
      if (neighbor.getTrap() == Trap.FIRE)
      {
        if (running)
        {
          List<Combustible> explode = house.getCombustableNeighbors(neighbor);
          for (Combustible item : explode)
          {
            item.setCombustedState(Combustible.CombustedState.IGNITED);
          }
          neighbor.setCombustedState(Combustible.CombustedState.IGNITED);
        }
      }
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
