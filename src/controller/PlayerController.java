package controller;


import model.*;
import model.Move;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class PlayerController extends AbstractCharacterController<Player>
{
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
    float direction = mover.getRotation();
    float stamina = mover.getStamina();
    float x = mover.getCurrentX();
    float y = mover.getCurrentY();

    if (stamina == 0)
    {
      isMoving = false;
      mover.setSpeed(Mover.IDLE);
    }

    if (!isMoving)
    {
      if (stamina < 5.0)
      {
        stamina += 0.2 * deltaTime + 0.01; // Stamina regenerates faster if mover is not moving
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
      mover.setSpeed(playerSpeed * deltaTime); // Determines how many tiles/second the player will move across
      mover.setRotation(direction);

      // Update player's x and y

      if (moveUp || moveDown) y = (float) (y + mover.getSpeed() * Math.sin(Math.toRadians(direction)));
      if (moveLeft || moveRight) x = (float) (x + mover.getSpeed() * Math.cos(Math.toRadians(direction)));

      checkCollision(new Move(x,y, (int) direction));
      // mover.move(x, y);
      isMoving = false;
    }
  }
  @Override
  public void checkCollision(Move moveToCheck)
  {

    List<Tile> neighbors = house.neighborsInDirection(house.getPlayerTile(),
                                                      mover.getRotation());

    List<Tile> aneighbors = house.neighbors(house.getPlayerTile());

    Rectangle2D.Float test = new Rectangle2D.Float(moveToCheck.col,
                                                   moveToCheck.row,
                                                   mover.getWidth(),
                                                   mover.getHeight());
    for (Tile neighbor : neighbors)
    {
      if (neighbor instanceof Wall)
      {
        if (test.intersects(neighbor.getBoundingRectangle()))
        {
          mover.setSpeed(0);
          return;
        }
      }
    }
    mover.move(moveToCheck.col, moveToCheck.row);
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
