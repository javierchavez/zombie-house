package controller;


import model.House;
import model.Zombie;

import java.util.List;

public class ZombieController
{
  private final House house;

  List<Zombie> zombies;
  private int direction;
  private boolean isMoving = true;
  private boolean playerDetected = false, running = false;
  private boolean moveUp, moveDown, moveLeft, moveRight;
  private float x, y, rotation;

  private final int EAST = 0;
  private final int NORTH = 270;
  private final int WEST = 180;
  private final int SOUTH = 90;

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
    // update one zombie at a time
    for (Zombie zombie : zombies)
    {
      float zombieSpeed = zombie.getSpeed();
      x = zombie.getCurrentX();
      y = zombie.getCurrentY();
      rotation = zombie.getRotation();

      if (!running) zombie.setSpeed(0.5f);

      if (isMoving)
      {
        // TODO: check collision
        // TODO: choose random direction
//        zombie.setRotation(direction);

        // If player detected, zombie moves faster
        if (playerDetected)
        {
          running = true;
          zombie.setSpeed(2.0f); // TODO: should zombie be able to run as fast as player?
          // zombie find strategy
        }

        // Update zombie's position
        if (moveUp || moveDown) y = (float) (y + zombie.getSpeed() * Math.sin(direction * (Math.PI / 180)));
        if (moveLeft || moveRight) x = (float) (x + zombie.getSpeed() * Math.cos(direction * (Math.PI / 180)));

//        zombie.move(x, y); // update zombie's position
        isMoving = false;
      }
    }
  }

  public void moveUp()
  {
    direction = NORTH;
  }

  public void moveDown()
  {
    direction = SOUTH;
  }

  public void moveLeft()
  {
    direction = WEST;
  }

  public void moveRight()
  {
    direction = EAST;
  }
}
