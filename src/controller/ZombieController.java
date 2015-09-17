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
  int xDir = 1, yDir = 0;

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

  private boolean DEBUG = true;

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
    for (Zombie zombie : zombies)
    {
      float zombieSpeed;
      x = zombie.getCurrentX();
      y = zombie.getCurrentY();
      rotation = zombie.getRotation();

      if (isMoving)
      {
        // If player detected, zombie moves faster and follows a path
        if (playerDetected)
        {
          running = true;
          zombieSpeed = 2.0f; // TODO: should zombie be able to run as fast as player?
          // zombie find strategy
        }
        else
        {
          running = false;
          zombieSpeed = 0.5f;

          time++;
          if (time % 60 == 0) // Zombie changes direction every second
          {
            // Doesn't handle diagonal movement for now
            int axis = rand.nextInt(1); // Determines whether x axis or y axis is updated
            if (axis == 0)
            {
              xDir = rand.nextInt(3) - 1; // X axis change
              switch(xDir)
              {
                case -1:
                  idling = false;
                  moveLeft();
                  break;
                case 0:
                  notMoving();
                  break;
                case 1:
                  idling = false;
                  moveRight();
                  break;
              }
            }
            else
            {
              yDir = rand.nextInt(3) - 1; // Y axis change
              switch(yDir)
              {
                case -1:
                  idling = false;
                  moveUp();
                  break;
                case 0:
                  notMoving();
                  break;
                case 1:
                  idling = false;
                  moveDown();
                  break;
              }
            }
          }

          if (idling) zombieSpeed = 0.0f;
        }

        zombie.setSpeed(zombieSpeed * deltaTime);

        // Update zombie's position
        if (moveUp || moveDown) y = (float) (y + zombie.getSpeed() * Math.sin(direction * (Math.PI / 180)));
        if (moveLeft || moveRight) x = (float) (x + zombie.getSpeed() * Math.cos(direction * (Math.PI / 180)));

        zombie.move(x, y); // update zombie's position
        isMoving = false;
      }
    } // ENDFOR
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
