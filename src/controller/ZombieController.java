package controller;


import common.Direction;
import common.Speed;
import model.*;
import model.Move;

import java.awt.geom.Rectangle2D;
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
  // The values of these ints can be either -1, 0, or 1
  // Depending on what their value is, the zombie will know which direction to go
  int xDir;
  int yDir;
  float[] directions = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
  Random random = new Random();

  private boolean DEBUG = true;

  public ZombieController (House house)
  {
    super(house);
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

          List a = zombie.getStrategy().getPath();
          if (a.size() > 0)
          {
            Tile t = (Tile) a.get(0);
//            if (DEBUG) System.out.println("new tile: (" + t.getX() + ", " + t.getY() + ")");
            xDir = t.getCol();
            yDir = t.getRow();
//            if (DEBUG) System.out.println("new tile (col,row): (" + t.getCol() + ", " + t.getRow() + ")");

          }
          isMoving = false;
        }
        else
        {
          zombieSpeed = Speed.STAGGER;

          if (time == 0 || time % 90 == 0) // change this to: if (collision)
          {
            Move move = zombie.getStrategy().getNextMove(house, house.getCharacterTile(zombie));
            xDir = (int) move.col;
            yDir = (int) move.row;
          }

          // Zombie movement
          if (xDir == 0 && yDir == 0) resting();
          if (xDir < 0 && yDir == 0) moveLeft();
          if (xDir > 0 && yDir == 0) moveRight();
          if (yDir > 0 && xDir == 0) moveUp();
          if (yDir < 0 && xDir == 0) moveDown();
        }
        if (idling) zombieSpeed = Speed.IDLE;

        zombie.setSpeed(zombieSpeed * deltaTime);

        // Update zombie's position
        if (moveUp || moveDown) y = (float) (y + (zombie.getSpeed() * Math
                .sin(Math.toRadians(direction))));
        if (moveLeft || moveRight) x = (float) (x + (zombie.getSpeed() * Math
                .cos(Math.toRadians(direction))));

        checkCollision(new Move(x, y, direction));
        isMoving = false;
      }
    } // END FOR
  }

  @Override
  public boolean checkCollision (Move moveToCheck)
  {
    // @esosebee I changed this method around to work with the new abstract controller
    // let me know if you have any questions
    // checks for a player and zombie collision are done in the abstract controller

    // Random and Line handle collision differently
    // Line changes direction immediately; Random keeps trying to go, but they should change direction in the next decision update

    // super checks for basic collisions (Wall, Obstacle, etc...)
    boolean collision = super.checkCollision(moveToCheck);
    if (DEBUG) System.out.println("\tMove to check: " + moveToCheck.col + ", " + moveToCheck.row);

    Area testArea = new Area(moveToCheck.col,
                             moveToCheck.row,
                             mover.getWidth(),
                             mover.getHeight());
    List<Tile> neighbors = house.getIntersectingNeighbors(zombieTile, testArea);

    // extra checks for fire traps
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
      //
    }

    // If Line Walker zombie collides with obstacle, they change direction
    if (mover.getStrategy() instanceof LineMoveStrategy)
    {
      if (collision)
      {
        moveToCheck.direction = -moveToCheck.direction;
      }
    }

    if (!collision)
    {
      mover.move(moveToCheck.col, moveToCheck.row);
    }
    return false;
  }
}
