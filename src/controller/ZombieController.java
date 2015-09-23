package controller;


import common.Direction;
import common.Speed;
import model.*;
import model.Move;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ZombieController extends AbstractCharacterController<Zombie>
{
  List<Zombie> zombies;
  private boolean isMoving = true;
  private boolean playerDetected = false; // How do I know when the player is detected
  private boolean running;
  private float x, y;

  // An incrementer to keep track of when 60 frames (1 second) have passed
  private int time = 0;
  private Tile zombieTile;
  private List<Tile> path;
  // The values of these ints can be either -1, 0, or 1
  // Depending on what their value is, the zombie will know which direction to go
  int xDir;
  int yDir;
  float[] directions = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
  Random random = new Random();

  private boolean DEBUG = false;

  public ZombieController (House house)
  {
    super(house);
  }

  private void zombieDirection()
  {
    if (xDir == 0 && yDir == 0) resting();
    if (xDir < 0 && yDir == 0) moveLeft();
    if (xDir > 0 && yDir == 0) moveRight();
    if (yDir > 0 && xDir == 0) moveUp();
    if (yDir < 0 && xDir == 0) moveDown();
  }

  private void nextPos(Zombie zombie, float direction)
  {
    if (moveUp || moveDown) y = (float) (y + (zombie.getSpeed() * Math
        .sin(Math.toRadians(direction))));
    if (moveLeft || moveRight) x = (float) (x + (zombie.getSpeed() * Math
        .cos(Math.toRadians(direction))));
  }

  @Override
  public void update(float deltaTime)
  {
    zombies = house.getZombies();

    time++;
    for (int i = 0; i < zombies.size(); i++)
    {
      Zombie zombie;
      float zombieSpeed;
      Tile playerTile = house.getCharacterTile(house.getPlayer()); // Get player's current tile

      isMoving = true;

      mover = zombie = zombies.get(i);

      zombieTile = house.getCharacterTile(zombie);
      if (zombieTile == null)
      {
        zombies.remove(i);
      }

      float direction = mover.getRotation();

      if (DEBUG)
      {
        System.out.println("Zombie " + i + ": (" + zombie.getCurrentX() + ", " + zombie.getCurrentY() + ")");
      }

      x = zombie.getCurrentX();
      y = zombie.getCurrentY();

      zombieSpeed = Speed.STAGGER;
      if (running) zombieSpeed = Speed.WALK;

      if (isMoving)
      {
        playerDetected = zombie.sense(zombieTile, playerTile);
        if (DEBUG) System.out.println("\tPlayer detected: " + playerDetected);

        if (playerDetected)
        {
          running = true;
          zombie.getStrategy().find(house,
              house.getCharacterTile(zombie),
              house.getCharacterTile(house.getPlayer()));

          path = zombie.getStrategy().getPath();
          if (path.size() > 0)
          {
            Tile t = path.get(0);
            xDir = t.getCol();
            yDir = t.getRow();
          }
          isMoving = false;
        }
        else // if player is not detected
        {
          zombieSpeed = Speed.STAGGER;

          if (mover.getStrategy() instanceof LineMoveStrategy) // Line walker
          {
            Move move = zombie.getStrategy().getNextMove(house, house.getCharacterTile(zombie));
            // check collision
          }
          else // Random walker
          {
            if (time % 60 == 0) // Random walk zombies change direction every 1 second
            {
              System.out.println("\tUPDATE POSITION");
              Move move = zombie.getStrategy().getNextMove(house, house.getCharacterTile(zombie));
              boolean collision = super.checkCollision(move);
              if (collision)
              {
                xDir = random.nextInt(3) - 1;
                yDir = random.nextInt(3) - 1;
              }
              else
              {
                xDir = (int) move.col;
                yDir = (int) move.row;
              }
            }
          }

          // Zombie movement
          zombieDirection();

//          if (xDir == 0 && yDir == 0) resting();
//          if (xDir < 0 && yDir == 0) moveLeft();
//          if (xDir > 0 && yDir == 0) moveRight();
//          if (yDir > 0 && xDir == 0) moveUp();
//          if (yDir < 0 && xDir == 0) moveDown();
        }

        // Update zombie's speed and next move
        if (idling) zombieSpeed = Speed.IDLE;
        zombie.setSpeed(zombieSpeed * deltaTime);
        nextPos(zombie, direction);

//        if (moveUp || moveDown) y = (float) (y + (zombie.getSpeed() * Math
//                .sin(Math.toRadians(direction))));
//        if (moveLeft || moveRight) x = (float) (x + (zombie.getSpeed() * Math
//                .cos(Math.toRadians(direction))));

        checkCollision(new Move(x, y, direction));
        isMoving = false;
      }
    } // END FOR
  }

  @Override
  public boolean checkCollision (Move moveToCheck)
  {
    // Line changes direction immediately; Random keeps trying to go, but they should change direction in the next decision update

    // Checks for player and zombie collision are done in the abstract controller
    boolean collision = super.checkCollision(moveToCheck); // Checks for basic collisions (wall, obstacle, etc...)
    if (DEBUG) System.out.println("\tcollision: " + collision);
    if (collision)
    {
      if (DEBUG) System.out.println("\tMove to check: " + moveToCheck.col + ", " + moveToCheck.row);
    }

    Area testArea = new Area(moveToCheck.col,
                             moveToCheck.row,
                             mover.getWidth(),
                             mover.getHeight());

    List<Tile> neighbors = house.getIntersectingNeighbors(zombieTile, testArea);

    // Extra checks for fire traps
    for (Tile neighbor : neighbors)
    {
      if (neighbor.getTrap() == Trap.FIRE)
      {
        zombies.remove(mover);
        List<Tile> explode = house.getCombustableNeighbors(neighbor);
        for (Tile tile : explode)
        {
          tile.setTrap(Trap.ACTIVATED);
        }
        neighbor.setTrap(Trap.ACTIVATED);
      }
    }

    if (mover.getStrategy() instanceof RandomMoveStrategy)
    {
      if (collision)
      {
        if (DEBUG) System.out.println("\trandom walker");
        mover.setSpeed(0);
      }
    }

    // If Line Walker zombie collides with obstacle, they change direction
    if (mover.getStrategy() instanceof LineMoveStrategy)
    {
      if (collision)
      {
        System.out.println("\tline walker");
        xDir = random.nextInt(3) - 1;
        yDir = random.nextInt(3) - 1;
        zombieDirection();
        nextPos(mover, moveToCheck.direction);
        Move newMove = new Move(x, y, moveToCheck.direction);
        System.out.println("\tnew move: " + newMove.col + ", " + newMove.row);
//        if (super.checkCollision(move)) mover.move(move.col, move.row);
//        checkCollision(newMove);
      }
    }

    if (!collision)
    {
      mover.move(moveToCheck.col, moveToCheck.row);
    }
    return false;
  }
}
