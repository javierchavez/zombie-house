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
  private float x, y;

  // An incrementer to keep track of when 60 frames (1 second) have passed
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

  // The values of these ints can be either -1, 0, or 1
  // Depending on what their value is, the zombie will know which direction to go
  int xDir;
  int yDir;

  private boolean DEBUG = false;

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

    time++;
    for (int i = 0; i < zombies.size(); i++)
    {
      isMoving = true;
      float zombieSpeed;
      Zombie zombie = zombies.get(i);
      if (DEBUG)
      {
        System.out.println("Zombie " + i + ": (" + zombie.getCurrentX() + ", " + zombie.getCurrentY() + ")");
      }
      x = zombie.getCurrentX();
      y = zombie.getCurrentY();

      zombieSpeed = 0.5f;
      if (running) zombieSpeed = 2.0f;

      if (isMoving)
      {
        if (playerDetected)
        {
          running = true;
          // TODO: zombie travels on path to player
        }
        else
        {
          zombieSpeed = 0.5f;

          if (time % 60 == 0) // Zombie changes position every 1 second
          {
            xDir = rand.nextInt(3) - 1;
            yDir = rand.nextInt(3) - 1;
          }

          if (xDir == 0 && yDir == 0) resting();
          if (xDir < 0) moveLeft();
          if (xDir > 0) moveRight();
          if (yDir > 0) moveUp();
          if (yDir < 0) moveDown();
        }

        if (idling) zombieSpeed = 0;

        zombie.setSpeed(zombieSpeed * deltaTime);

        // Update zombie's position
        if (moveUp || moveDown) y = (float) (y + zombie.getSpeed() * Math.sin(direction * (Math.PI / 180)));
        if (moveLeft || moveRight) x = (float) (x + zombie.getSpeed() * Math.cos(direction * (Math.PI / 180)));

        zombie.move(x, y);
        isMoving = false;
      }
    } // END FOR
    if (DEBUG) System.out.println("End for");
  }

  /**
   * Zombie is resting.
   */
  public void resting()
  {
    if (DEBUG) System.out.println("\tResting...");
    idling = true;
  }

  /**
   * Tells the zombie to move up.
   */
  public void moveUp()
  {
    if (DEBUG) System.out.println("\tMoving up");
    moveUp = true;
    direction = NORTH;
    idling = false;
  }

  /**
   * Tells the zombie to move down.
   */
  public void moveDown()
  {
    if (DEBUG) System.out.println("\tMoving down");
    moveDown = true;
    direction = SOUTH;
    idling = false;
  }

  /**
   * Tells the zombie to move left.
   */
  public void moveLeft()
  {
    if (DEBUG) System.out.println("\tMoving left");
    moveLeft = true;
    direction = WEST;
    idling = false;
  }

  /**
   * Tells the zombie to move right.
   */
  public void moveRight()
  {
    if (DEBUG) System.out.println("\tMoving right");
    moveRight = true;
    direction = EAST;
    idling = false;
  }
}
