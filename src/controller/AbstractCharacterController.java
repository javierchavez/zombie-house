package controller;

/**
 * @author Javier Chavez
 * @author Alex Baker
 * @author Erin Sosebee
 * <p>
 * Date September 28, 2015
 * CS 351
 * Zombie House
 * <p>
 * This is the super class for setting character speed and rotation
 */

import common.Direction;
import common.Speed;
import model.*;
import model.Character;
import model.Sound.SoundType;

import java.awt.*;
//import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Random;

/**
 * This class does most of the work for setting the character speed and rotation
 *
 * @param <T> type of mover you are wanting to move... it must extend Mover
 *            in the model.
 */
public abstract class AbstractCharacterController<T extends Character> implements MoverController<T>
{

  protected static House house;
  private final boolean DEBUG = false;
  private final Random random = new Random();
  protected T mover;
  protected boolean isMoving = false, running = false, idling = true;
  protected boolean moveUp, moveDown, moveLeft, moveRight;

  public AbstractCharacterController (House house, T mover)
  {
    AbstractCharacterController.house = house;
    this.mover = mover;
  }


  public AbstractCharacterController (House house)
  {
    this(house, null);
  }

  @Override
  public boolean checkCollision (Move moveToCheck)
  {
    boolean tripTrap = false;
    Tile current = house.getCharacterTile(mover);
    Area testArea = new Area(moveToCheck.col, moveToCheck.row, mover.getWidth(),
                             mover.getHeight());
    List<Tile> neighbors = house.neighborsInDirection(current,
                                                      moveToCheck.direction);
    boolean canHear = false;
    Player player = null;
    for (Tile neighbor : neighbors)
    {
      boolean isZombie =  mover instanceof Zombie;
      if (neighbor.intersects(testArea.getBoundingRectangle()))
      {
        if (isZombie)
        {
          player = house.getPlayer();
          canHear = player.senseHear(house.getCharacterTile(mover));
        }
        if (canHear)
        {
          float theta = player.getCardinalDirectionBetween(mover);
          mover.setChannel(theta);
          mover.setSoundType(SoundType.WALK);
          mover.setVolume(1f);
        }
        else
        {
          mover.setVolume(0f);
        }
        if (!neighbor.isPassable())
        {
          if (neighbor instanceof Wall && isZombie)
          {
            if (canHear)
            {
              float theta = player.getCardinalDirectionBetween(mover);
              mover.setChannel(theta);
              mover.setSoundType(SoundType.HIT);
              mover.setVolume(1f);
            }
          }
          return true;
        }
        else if (house.isZombieTile(neighbor))
        {
          if (mover instanceof Zombie)
          {
            return true;
          }
        }
      }
    }

    if (current.getTrap() == Trap.FIRE)
    {
      if (mover instanceof Player)
      {
        if (running)
        {
          tripTrap = true;
        }
      }
      else if (mover instanceof Zombie)
      {
        tripTrap = true;
      }

      if (tripTrap)
      {
        List<Combustible> explode = house.getCombustibleNeighbors(current);
        for (Combustible item : explode)
        {
          // need to remove the trap from the tiles in the explosion and/or
          // set those off too. that means 'getCombustibleNeighbors' of the trap in
          // the explosion and set those on fire too.
          CombustibleController.getInstance().addCombustible(item);
        }
        CombustibleController.getInstance().addCombustible(current);
        current.setTrap(Trap.NONE);
      }
    }

    return false;
  }

  @Override
  public void run ()
  {
    isMoving = true;
    if (mover.getStamina() > 0)
    {
      running = true;
    }
  }

  @Override
  public void walk ()
  {
    isMoving = true;
    running = false;
    idling = false;
  }

  @Override
  public void idle ()
  {
    // if (DEBUG) System.out.println("Idling...");
    idling = true;
    isMoving = false;
    running = false;
    mover.setSpeed(Speed.IDLE);
  }

  @Override
  public void stopMoving () // TODO: get rid of this method later? probably don't need it
  {
    if (DEBUG)
    {
      System.out.println("I can't move like that!");
    }
    isMoving = false;
    idling = true;
    running = false;
    mover.setSpeed(Speed.IDLE);
  }

  @Override
  public void moveUp ()
  {
    if (DEBUG)
    {
      System.out.println("Moving up");
    }
    isMoving = true;
    idling = false;
    moveUp = true;
    //direction = Mover.NORTH; // Change mover's direction
    // mover.setRotation(Direction.NORTH);
    mover.setRotation(Direction.SOUTH);
  }

  @Override
  public void moveDown ()
  {
    if (DEBUG)
    {
      System.out.println("Moving down");
    }
    isMoving = true;
    idling = false;
    moveDown = true;
    //direction = Mover.SOUTH;
    // mover.setRotation(Direction.SOUTH);
    mover.setRotation(Direction.NORTH);
  }

  @Override
  public void moveLeft ()
  {
    if (DEBUG)
    {
      System.out.println("Moving left");
    }
    isMoving = true;
    idling = false;
    moveLeft = true;
    //direction = Mover.WEST;
    mover.setRotation(Direction.WEST);
  }

  @Override
  public void moveRight ()
  {
    if (DEBUG)
    {
      System.out.println("Moving right");
    }
    isMoving = true;
    idling = false;
    moveRight = true;
    //direction = Mover.EAST;
    mover.setRotation(Direction.EAST);
  }

  @Override
  public void moveUpRight ()
  {
    if (DEBUG)
    {
      System.out.println("Moving up right");
    }
    isMoving = true;
    idling = false;
    //direction = NORTHEAST;
    // mover.setRotation(Direction.NORTHEAST);
    mover.setRotation(Direction.SOUTHEAST);
  }

  @Override
  public void moveUpLeft ()
  {
    isMoving = true;
    idling = false;
    //direction = NORTHWEST;
    // mover.setRotation(Direction.NORTHWEST);
    mover.setRotation(Direction.SOUTHWEST);
  }

  @Override
  public void moveDownRight ()
  {
    isMoving = true;
    idling = false;
    //direction = SOUTHEAST;
    // mover.setRotation(Direction.SOUTHEAST);
    mover.setRotation(Direction.NORTHEAST);
  }

  @Override
  public void moveDownLeft ()
  {
    isMoving = true;
    idling = false;
    //direction = SOUTHWEST;
    // mover.setRotation(Direction.SOUTHWEST);
    mover.setRotation(Direction.NORTHWEST);
  }

  @Override
  public void resting ()
  {
    if (DEBUG)
    {
      System.out.println("\tResting...");
    }
    idling = random.nextBoolean();
  }

  @Override
  public void render (Graphics2D graphics)
  {

  }
}
