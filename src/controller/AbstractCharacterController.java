package controller;


import model.*;
import model.Character;

import java.awt.geom.Rectangle2D;
import java.util.List;

/**
 * This class does most of the work for setting the character speed and rotation
 * @param <T> type of mover you are wanting to move... it must extend Mover
 *           in the model.
 */
public abstract class AbstractCharacterController<T extends Character> implements
        MoverController<T>
{

  protected static House house;
  protected T mover;
  protected boolean isMoving = false, running = false, idling = true;
  protected boolean moveUp, moveDown, moveLeft, moveRight;
  private boolean DEBUG = false;

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
  public void run ()
  {
    isMoving = true;
    if (mover.getStamina() > 0) running = true;
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
    mover.setSpeed(Mover.IDLE);
  }

  @Override
  public void stopMoving () // TODO: get rid of this method later? probably don't need it
  {
    System.out.println("I can't move like that!");
    isMoving = false;
    running = false;
    mover.setSpeed(Mover.IDLE);
  }

  @Override
  public void moveUp ()
  {
    if (DEBUG) System.out.println("Moving up");
    isMoving = true;
    idling = false;
    moveUp = true;
    //direction = Mover.NORTH; // Change mover's direction
    mover.setRotation(Mover.NORTH);
  }

  @Override
  public void moveDown ()
  {
    if (DEBUG) System.out.println("Moving down");
    isMoving = true;
    idling = false;
    moveDown = true;
    //direction = Mover.SOUTH;
    mover.setRotation(Mover.SOUTH);
  }

  @Override
  public void moveLeft ()
  {
    if (DEBUG) System.out.println("Moving left");
    isMoving = true;
    idling = false;
    moveLeft = true;
    //direction = Mover.WEST;
    mover.setRotation(Mover.WEST);
  }

  @Override
  public void moveRight ()
  {
    if (DEBUG) System.out.println("Moving right");
    isMoving = true;
    idling = false;
    moveRight = true;
    //direction = Mover.EAST;
    mover.setRotation(Mover.EAST);
  }

  @Override
  public void moveUpRight ()
  {
    if (DEBUG) System.out.println("Moving up right");
    isMoving = true;
    idling = false;
    //direction = NORTHEAST;
    mover.setRotation(Mover.NORTHEAST);
  }

  @Override
  public void moveUpLeft ()
  {
    isMoving = true;
    idling = false;
    //direction = NORTHWEST;
    mover.setRotation(Mover.NORTHWEST);
  }

  @Override
  public void moveDownRight ()
  {
    isMoving = true;
    idling = false;
    //direction = SOUTHEAST;
    mover.setRotation(Mover.SOUTHEAST);
  }

  @Override
  public void moveDownLeft ()
  {
    isMoving = true;
    idling = false;
    //direction = SOUTHWEST;
    mover.setRotation(Mover.SOUTHWEST);
  }

  @Override
  public void resting ()
  {
    if (DEBUG) System.out.println("\tResting...");
    idling = true;
  }
}
