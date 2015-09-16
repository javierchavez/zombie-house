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
  private float x, y;

  private final int EAST = 0;
  private final int NORTH = 90;
  private final int WEST = 180;
  private final int SOUTH = 270;

  public ZombieController(House house)
  {
    this.house = house;
  }

  public void collisionDetection(float deltaTime)
  {

  }

  public void update(float deltaTime)
  {
    zombies = house.getZombies();
    // update one zombie at a time
    for (Zombie zombie : zombies)
    {
      x = zombie.getCurrentX();
      y = zombie.getCurrentY();

      if (isMoving)
      {
        // TODO: check collision
        // TODO: choose random direction

//      zombie.move(x, y); // update zombie's position
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

  public void moveRight()
  {
    direction = EAST;
  }

  public void moveLeft()
  {
    direction = WEST;
  }
}
