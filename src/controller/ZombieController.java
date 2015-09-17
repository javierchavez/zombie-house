package controller;


import model.House;
import model.Zombie;

import java.util.List;
import java.util.Random;

public class ZombieController
{
  private final House house;

  List<Zombie> zombies;
  private int direction;
  private boolean isMoving = true;
  private boolean playerDetected = false; // How do I know when the player is detected
  private boolean running;
  private boolean idling = false;
  private boolean moveUp, moveDown, moveLeft, moveRight;
  private float x, y, rotation;

  private int time = 0;
  private Random rand = new Random();

  // Cardinal directions
  private final int EAST = 0;
  private final int NORTH = 270;
  private final int WEST = 180;
  private final int SOUTH = 90;

  // Ordinal directions
  private final int NORTHEAST = 315;
  private final int NORTHWEST = 225;
  private final int SOUTHEAST = 45;
  private final int SOUTHWEST = 135;

  int xDir;
  int yDir;

  private boolean DEBUG = true;

  // TODO: do we want to handle the super zombie stuff in here or make a super zombie controller?
  public ZombieController (House house)
  {
    this.house = house;
  }

  public void collisionDetection(float deltaTime)
  {
    // TODO:
  }

  public void update(float deltaTime)
  {
    zombies = house.getZombies();
    isMoving = true;
    for (int i = 0; i < zombies.size(); i++)
    {
      float zombieSpeed;
      Zombie zombie = zombies.get(i);
//      System.out.println("Zombie " + i + ": (" + zombie.getCurrentX() + ", " + zombie.getCurrentY() + ")");
      x = zombie.getCurrentX();
      y = zombie.getCurrentY();
//      System.out.println(isMoving);

      if (isMoving)
      {
        // If player detected, zombie moves faster + follows path to player
        if (playerDetected)
        {
          running = true;
          zombieSpeed = 2.0f; // Should zombie be able to run as fast as player?
          // zombie find strategy
        }
        else
        {
          running = false;
          zombieSpeed = 0.5f;

          time++;
          if (time % 60 == 0) // Zombie changes direction every second
          {
            xDir = rand.nextInt(3) - 1;
            System.out.println(xDir);
            yDir = rand.nextInt(3) - 1;
          }

          // Deciding the direction to move
          if (xDir < 0) moveLeft();
          if (xDir > 0) moveRight();
          // if xDir == 0: no change to x trajectory
          if (yDir < 0) moveUp();
          if (yDir > 0) moveDown();
          // if yDir == 0: no change to y trajectory
          if (xDir == 0 && yDir == 0) notMoving(); // If both are zero, then stop moving
          if (idling) zombieSpeed = 0;
        }

        zombie.setSpeed(zombieSpeed * deltaTime);

        // Update zombie's position
        if (moveUp || moveDown) y = (float) (y + zombie.getSpeed() * Math.sin(direction * (Math.PI / 180)));
        if (moveLeft || moveRight) x = (float) (x + zombie.getSpeed() * Math.cos(direction * (Math.PI / 180)));

        zombie.move(x, y); // update zombie's position
        isMoving = false;
      }
    } // END FOR
  }

  /**
   * Zombie is resting.
   */
  public void notMoving()
  {
    idling = true;
  }

  /**
   * Tells the zombie to move up.
   */
  public void moveUp()
  {
    moveUp = true;
    direction = NORTH;
  }

  /**
   * Tells the zombie to move down.
   */
  public void moveDown()
  {
    moveDown = true;
    direction = SOUTH;
  }

  /**
   * Tells the zombie to move left.
   */
  public void moveLeft()
  {
    moveLeft = true;
    direction = WEST;
  }

  /**
   * Tells the zombie to move right.
   */
  public void moveRight()
  {
    moveRight = true;
    direction = EAST;
  }
}
