package controller;


import common.Speed;
import model.*;
import model.Move;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class ZombieController extends AbstractCharacterController<Zombie>
{
  List<Zombie> zombies;
//  List<Combustible> combustibleList = new ArrayList<>();
  ConcurrentHashMap<Combustible, Integer> combustibleMap = new ConcurrentHashMap<>();
  private boolean isMoving = true;
  private boolean playerDetected = false; // How do I know when the player is detected
  private boolean running;
  private float x, y;

  // An incrementer to keep track of when 120 frames (2 seconds) have passed
  private int time = 0;
  private int secIncrement = 0;
  private Tile zombieTile;
  private List<Tile> path;
  // The values of these ints can be either -1, 0, or 1
  // Depending on what their value is, the zombie will know which direction to go
  int xDir;
  int yDir;
  Random random = new Random();

  private boolean DEBUG = false;

  public ZombieController (House house)
  {
    super(house);
  }

  /**
   *
   */
  private void zombieDirection()
  {
    if (xDir == 0 && yDir == 0) resting();
    if (xDir < 0 && yDir == 0) moveLeft();
    if (xDir > 0 && yDir == 0) moveRight();
    if (yDir > 0 && xDir == 0) moveUp();
    if (yDir < 0 && xDir == 0) moveDown();
  }

  /**
   *
   * @param zombie
   * @param direction
   */
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
    // handleFires();
    time++;
    secIncrement++;
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

//      if (DEBUG)
//      {
//        System.out.println("Zombie " + i + ": (" + zombie.getCurrentX() + ", " + zombie.getCurrentY() + ")");
//      }

      x = zombie.getCurrentX();
      y = zombie.getCurrentY();

      zombieSpeed = Speed.STAGGER;
      if (running) zombieSpeed = Speed.WALK;

      if (isMoving)
      {
        playerDetected = zombie.sense(playerTile);
        if (DEBUG) System.out.println("\tPlayer detected: " + playerDetected);
        if (playerDetected)
        {
          zombieSpeed = Speed.WALK;

          mover.getStrategy().find(house,
              house.getCharacterTile(mover),
              house.getCharacterTile(house.getPlayer()));

          path = mover.getStrategy().getPath();
          new Thread(() -> {
            while(path.size() > 0)
            {
              Tile t = path.remove(0);
//              x = t.getCol();
//              y = t.getY();

//              checkCollision(new Move(x, y, direction));
              mover.move(t.getCol(), t.getRow());

              try
              {
                Thread.sleep(300);
              }
              catch (InterruptedException e)
              {
                e.printStackTrace();
              }
            }
          }).start();

//          if (path.size() > 0)
//          {
//            // This is all super buggy and game-breaking, so it's all commented out:
//            Tile t = path.remove(0);
//            mover.move(t.getCol(), t.getRow());
////            xDir = t.getCol();
////            yDir = t.getRow();
//            // Figure out which direction to make zombie face later
//
//
//            /**
//            new Thread(() -> {
//              while (playerDetected)
//              {
//                Tile t = path.remove(0);
//                mover.move(t.getX(), t.getY());
//
//                try
//                {
//                  Thread.sleep(140);
//                }
//                catch (InterruptedException e)
//                {
//                  e.printStackTrace();
//                }
//              }
//            }).start(); **/
//          }
        }
        else // if player is not detected
        {
          zombieSpeed = Speed.STAGGER;

          if (mover.getStrategy() instanceof LineMoveStrategy) // Line walker
          {
            if (DEBUG) System.out.println("\n\n\tLine Mover");
//            Move move = zombie.getStrategy().getNextMove(house, house.getCharacterTile(zombie));
//            boolean collision = super.checkCollision(move);
//            if (collision)
//            {
//              xDir = random.nextInt(3) - 1;
//              yDir = random.nextInt(3) - 1;
//            }
//
//            zombieDirection(); // Zombie's new direction
//            zombie.setSpeed(zombieSpeed * deltaTime);
//            nextPos(zombie, direction); // Get the zombie's next position

            checkCollision(new Move(x, y, direction));
          }
          else // Random walker
          {
//            if (DEBUG) System.out.println("\n\n\tRandom Mover");
            if (time % 120 == 0) // Random walk zombies change direction every 2 seconds
            {
//              if (DEBUG) System.out.println("\tRANDOM UPDATE POSITION");
              Move move = zombie.getStrategy().getNextMove(house, house.getCharacterTile(zombie));
              xDir = (int) move.col;
              yDir = (int) move.row;
//              boolean collision = super.checkCollision(move);
//              if (collision)
//              {
//                xDir = random.nextInt(3) - 1;
//                yDir = random.nextInt(3) - 1;
//              }
//              else
//              {
//                xDir = (int) move.col;
//                yDir = (int) move.row;
//              }
            }
          }

          // Update zombie's position/status
          zombie.setSpeed(zombieSpeed * deltaTime);
          zombieDirection(); // Direction zombie should face
          nextPos(zombie, direction); // Get the zombie's new x and y
          checkCollision(new Move(x, y, direction));
        }

        // Update zombie's speed and next move
//        if (idling)
//        {
//          System.out.println("\tis idling");
//          zombieSpeed = Speed.IDLE;
//        }
//        zombie.setSpeed(zombieSpeed * deltaTime);
//        nextPos(zombie, direction);
//
//        checkCollision(new Move(x, y, direction));
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
//    if (DEBUG) System.out.println("\tcollision: " + collision);
    if (collision)
    {
      if (DEBUG) System.out.println("\tMove to check: " + moveToCheck.col + ", " + moveToCheck.row);
    }

    //Area testArea = new Area(moveToCheck.col,
    //                         moveToCheck.row,
    //                         mover.getWidth(),
    //                         mover.getHeight());

    //List<Tile> neighbors = house.getIntersectingNeighbors(zombieTile, testArea);

    // Extra checks for fire traps
    //for (Tile neighbor : neighbors)
    //{
    //  if (neighbor.getTrap() == Trap.FIRE)
    //  {
    //    zombies.remove(mover);
    //    List<Combustible> explode = house.getCombustibleNeighbors(neighbor);
    //    for (Combustible item : explode)
    //    {
    //      CombustibleController.getInstance().addCombustible(item);
    //    }
    //    CombustibleController.getInstance().addCombustible(neighbor);
    //  }
    //}

//    if (mover.getStrategy() instanceof RandomMoveStrategy)
//    {
//      if (collision)
//      {
//        if (DEBUG) System.out.println("\trandom walker");
//        mover.setSpeed(0);
//      }
//    }

    // If Line Walker zombie collides with obstacle, they change direction
    if (mover.getStrategy() instanceof LineMoveStrategy)
    {
      if (collision)
      {
//        System.out.println("\tline walker");
        xDir = random.nextInt(3) - 1;
        yDir = random.nextInt(3) - 1;
        zombieDirection();
        nextPos(mover, moveToCheck.direction);
        Move newMove = new Move(x, y, moveToCheck.direction);
//        System.out.println("\tnew move: " + newMove.col + ", " + newMove.row);
//        if (super.checkCollision(move)) mover.move(move.col, move.row);
        checkCollision(newMove);
      }
    }

    if (!collision)
    {
      mover.move(moveToCheck.col, moveToCheck.row);
    }
    return false;
  }
}
