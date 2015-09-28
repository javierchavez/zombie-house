package controller;


import common.Direction;
import common.Speed;
import model.*;
import model.Move;

import java.util.List;
import java.util.Random;

public class ZombieController extends AbstractCharacterController<Zombie>
{
  List<Zombie> zombies;
  private boolean isMoving = true;
  private boolean playerDetected = false;
  private boolean running = false;
//  private float x, y;

  // An incrementer to keep track of when 120 frames (2 seconds) have passed
  private int time = 0;
  private Tile zombieTile;
  private List<Tile> path;

  Random random = new Random();

  private boolean DEBUG = true;

  public ZombieController (House house)
  {
    super(house);
  }

  /**
   *
   */
  private void zombieDirection(float xDir, float yDir)
  {
    if (xDir == 0 && yDir == 0) resting();

    if (xDir < 0 && yDir == 0) moveLeft();
    if (xDir < 0 && yDir < 0) moveDownLeft();
    if (xDir < 0 && yDir > 0) moveDownRight();
    if (xDir > 0 && yDir == 0) moveRight();
    if (yDir > 0 && xDir == 0) moveUp();
    if (xDir > 0 && yDir > 0) moveUpRight();
    if (yDir < 0 && xDir == 0) moveDown();
    if (xDir < 0 && yDir > 0) moveUpLeft();
  }

  /*
  private void newXY(Zombie zombie, float direction)
  {
    if (moveUp || moveDown) y = (float) (y + (zombie.getSpeed() * Math
        .sin(Math.toRadians(direction))));
    if (moveLeft || moveRight) x = (float) (x + (zombie.getSpeed() * Math
        .cos(Math.toRadians(direction))));
  }*/

  @Override
  public void update(float deltaTime)
  {
    zombies = house.getZombies();

    time++;
    for (int i = 0; i < zombies.size(); i++)
    {
      Zombie zombie;
      float zombieSpeed;
      // The values of these floats can be either -1, 0, or 1
      float xDir, yDir; // Depending on what their value is, the zombie will know which direction to go
      float x, y;
      Tile playerTile = house.getCharacterTile(house.getPlayer()); // Get player's current tile

      isMoving = true;
      mover = zombie = zombies.get(i);
      if (zombie.getCombustedState() == Combustible.CombustedState.IGNITED)
      {
        zombies.remove(i);
        continue;
      }

      zombieTile = house.getCharacterTile(zombie);
      if (zombieTile == null)
      {
        zombies.remove(i);
        continue;
      }

      float direction = mover.getRotation();

//      if (DEBUG) System.out.println("Zombie " + i + ": (" + zombie.getCurrentX() + ", " + zombie.getCurrentY() + ")");

      zombieSpeed = Speed.STAGGER; // Default zombie speed
      if (idling) zombieSpeed = Speed.IDLE;
      if (running) zombieSpeed = 0.75f;

      if (isMoving)
      {
        playerDetected = zombie.sense(playerTile); // Detect player
        if (playerDetected)
        {
          running = true;
          zombieSpeed = 0.75f;

          // Get path to player
          mover.getStrategy().find(house,
              house.getCharacterTile(zombie),
              house.getCharacterTile(house.getPlayer()));

          path = mover.getStrategy().getPath();

          zombie.setSpeed(zombieSpeed * deltaTime);
          Tile currentTile = house.getCharacterTile(zombie);
          path.remove(currentTile);
          if (path.size() > 0)
          {
            Tile nextTile = path.get(0);
            xDir = (currentTile.getX() - nextTile.getX()) * -1;
            yDir = (currentTile.getY() - nextTile.getY()) * -1;

            zombieDirection(xDir, yDir);
            x = (float) (mover.getCurrentX() + mover.getSpeed() * Math.cos(Math.toRadians(mover.getRotation())));
            y = (float) (mover.getCurrentY() + mover.getSpeed() * Math.sin(Math.toRadians(mover.getRotation())));

            if (checkCollision(new Move(x, y, mover.getRotation())))
            {
              if (mover.getRotation() == Direction.EAST)
              {
                mover.setRotation(Direction.NORTH);
              }
              else if (mover.getRotation() == Direction.WEST)
              {
                mover.setRotation(Direction.NORTH);
              }
              else if (mover.getRotation() == Direction.SOUTH)
              {
                mover.setRotation(Direction.EAST);
              }
              else if (mover.getRotation() == Direction.NORTH)
              {
                mover.setRotation(Direction.WEST);
              }

//              mover.move((int) currentTile.getX(), (int) currentTile.getY());
            }
          }
          else stopMoving();
        }
        else // If player is not detected
        {
          running = false;
          zombieSpeed = Speed.STAGGER;
          float wanderXDir, wanderYDir;
          float wanderX, wanderY;

          zombie.setSpeed(zombieSpeed * deltaTime);
          wanderX = (float) (mover.getCurrentX() + zombie.getSpeed() * Math.cos(Math.toRadians(mover.getRotation())));
          wanderY = (float) (mover.getCurrentY() + zombie.getSpeed() * Math.sin(Math.toRadians(mover.getRotation())));

          if (zombie.getStrategy() instanceof RandomMoveStrategy)
          {
            if (time == 0 || time % 120 == 0)
            {
              Move move = zombie.getStrategy().getNextMove(house, house.getCharacterTile(zombie));
              wanderXDir = move.col;
              wanderYDir = move.row;
              zombieDirection(wanderXDir, wanderYDir);
            }
            checkCollision(new Move(wanderX, wanderY, mover.getRotation()));
          }
          else // Zombie is Line Mover
          {
            if (checkCollision(new Move(wanderX, wanderY, mover.getRotation())))
            {
              Move move = zombie.getStrategy().getNextMove(house, house.getCharacterTile(zombie));
              wanderXDir = move.col;
              wanderYDir = move.row;
              zombieDirection(wanderXDir, wanderYDir);
            }
          }

        }
        isMoving = false;
      }
    } // END FOR
  }

  @Override
  public boolean checkCollision (Move moveToCheck)
  {
    boolean collision = super.checkCollision(moveToCheck);
    if (collision)
    {
      mover.setSpeed(0);
      return true;
    }
    else
    {
      mover.move(moveToCheck.col, moveToCheck.row);
    }
    return false;
  }
}
