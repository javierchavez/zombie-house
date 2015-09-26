package controller;


import common.Speed;
import model.*;
import model.Move;

import java.util.List;
import java.util.Random;

public class ZombieController extends AbstractCharacterController<Zombie>
{
  List<Zombie> zombies;
  private boolean isMoving = true;
  private boolean playerDetected = false; // How do I know when the player is detected
  private boolean running = false;
  private float x, y;

  // An incrementer to keep track of when 120 frames (2 seconds) have passed
  private int time = 0;
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
  private void newXY(Zombie zombie, float direction)
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
    for (int i = 0; i < zombies.size(); i++)
    {
      Zombie zombie;
      float zombieSpeed;
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

      if (DEBUG) System.out.println("Zombie " + i + ": (" + zombie.getCurrentX() + ", " + zombie.getCurrentY() + ")");

      x = zombie.getCurrentX();
      y = zombie.getCurrentY();

      zombieSpeed = Speed.STAGGER;
      if (running) zombieSpeed = Speed.WALK;

      if (isMoving)
      {
        playerDetected = zombie.sense(playerTile);
        if (playerDetected && time % 10 == 0)
        {
          mover.getStrategy().find(house,
              house.getCharacterTile(mover),
              house.getCharacterTile(house.getPlayer()));

          path = mover.getStrategy().getPath();

          new Thread(() -> {
            while(path.size() > 0)
            {
              Tile t = path.remove(0);
              // Figure out which way to make the zombie face
              mover.move(t.getCol(), t.getRow());

              try
              {
                Thread.sleep(200);
              }
              catch (InterruptedException e)
              {
                e.printStackTrace();
              }
            }
          }).start();

          playerDetected = false;
        }
        else // if player is not detected
        {
          zombieSpeed = Speed.STAGGER;

          if (mover.getStrategy() instanceof LineMoveStrategy) // Line walker
          {
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
//            newXY(zombie, direction); // Get the zombie's next position

            checkCollision(new Move(x, y, direction));
          }
          else // Random walker
          {
            if (time % 120 == 0) // Random walk zombies change direction every 2 seconds
            {
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
          newXY(zombie, direction); // Get the zombie's new x and y
          checkCollision(new Move(x, y, direction));
        }

        // Update zombie's speed and next move
//        if (idling)
//        {
//          System.out.println("\tis idling");
//          zombieSpeed = Speed.IDLE;
//        }
//        zombie.setSpeed(zombieSpeed * deltaTime);
//        newXY(zombie, direction);
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
        newXY(mover, moveToCheck.direction);
        Move newMove = new Move(x, y, moveToCheck.direction);
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
