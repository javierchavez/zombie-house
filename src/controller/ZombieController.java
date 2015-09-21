package controller;


import model.*;
import model.Move;

import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Random;

public class ZombieController extends AbstractCharacterController<Zombie>
{

//  get the next direction from the model, move, and then check if zombie can smell player
//  if so run the algorithm (call find method) from the model and get the next location and move.... then repeat

  List<Zombie> zombies;
  private boolean isMoving = true;
  private boolean playerDetected = false; // How do I know when the player is detected
  private boolean running;
  private float x, y;

  // Intelligence 0 = random walk, 1 = line walk
  private int intelligence; // TODO: placeholder. Each zombie should be given an intelligence when generated i think?

  // An incrementer to keep track of when 60 frames (1 second) have passed
  private int time = 0;
  private Random rand = new Random();
  private Tile zombieTile;
  // The values of these ints can be either -1, 0, or 1
  // Depending on what their value is, the zombie will know which direction to go
  int xDir;
  int yDir;

  private boolean DEBUG = false;

  // TODO: randomly decide if zombie will be random walk or line walk
  // NOTE: random walk and line walk have different intelligence
  public ZombieController (House house)
  {
    super(house);
  }

//  @Override
//  public void checkCollision (float deltaTime)
//  {
//    // Random and Line handle collision differently
//    // Line changes direction immediately; Random keeps trying to go, but they should change direction in the next decision update
//  }

  @Override
  public void update(float deltaTime)
  {
    zombies = house.getZombies();

    time++;
    for (int i = 0; i < zombies.size(); i++)
    {
      Tile playerTile = house.getPlayerTile(); // Get player's current position

      Zombie zombie;
      float zombieSpeed;

      isMoving = true;

      mover = zombie = zombies.get(i);
      zombieTile = house.getZombieTile(zombie);
      if (zombieTile == null)
      {
        zombies.remove(i);
      }

      float direction = mover.getRotation();

      if (DEBUG)
      {
//        System.out.println(rand.nextInt(1));
        System.out.println("Zombie " + i + ": (" + zombie.getCurrentX() + ", " + zombie.getCurrentY() + ")");
        System.out.println("Zombie " + i + " intelligence : " + zombie.getIntelligence());
      }
      x = zombie.getCurrentX();
      y = zombie.getCurrentY();

      zombieSpeed = Mover.STAGGER_SPEED;
      if (running) zombieSpeed = Mover.WALK_SPEED;

      if (isMoving)
      {
        playerDetected = zombie.sense(zombieTile, playerTile);

        if (DEBUG) System.out.println("\tPlayer detected: " + playerDetected);

        if (playerDetected)
        {
          running = true;
          zombie.getStrategy().find(house,
                                    house.getZombieTile(zombie),
                                    house.getPlayerTile());
          List a = zombie.getStrategy().getPath();
          if (a.size() > 0)
          {
            Tile t = (Tile) a.get(0);
            xDir = t.getCol();
            yDir = t.getRow();
          }
//          Tile nextTile = zombie.find(house);
//          System.out.println("\tNext Tile: " + nextTile.getX() + "," + nextTile.getY());
        }
        else
        {
          zombieSpeed = Mover.STAGGER_SPEED;

          if (time % 60 == 0) // change this to: if (collision)
          {
            Move move = zombie.getStrategy().getNextMove(house, house.getZombieTile(
                                                                 zombie));
            xDir = (int) move.col;
            yDir = (int) move.row;
          }
          if (xDir == 0 && yDir == 0) resting();

          // Cardinal directions
          if (xDir < 0 && yDir == 0) moveLeft();
          if (xDir > 0 && yDir == 0) moveRight();
          if (yDir > 0 && xDir == 0) moveUp();
          if (yDir < 0 && xDir == 0) moveDown();

          // Ordinal directions
//          if (xDir < 0 && yDir > 0) moveUpLeft();
//          if (xDir > 0 && yDir > 0) moveUpRight();
//          if (xDir < 0 && yDir < 0) moveDownLeft();
//          if (xDir > 0 && yDir < 0) moveDownRight();
        }

        if (idling) zombieSpeed = Mover.IDLE;

        zombie.setSpeed(zombieSpeed * deltaTime);

        // Update zombie's position
        if (moveUp || moveDown) y = (float) (y + zombie.getSpeed() * Math.sin(Math.toRadians(direction)));
        if (moveLeft || moveRight) x = (float) (x + zombie.getSpeed() * Math.cos(Math.toRadians(direction)));

//        zombie.move(x, y);
        checkCollision(new Move(x,y,direction));
        isMoving = false;
      }
    } // END FOR
  }

  @Override
  public void checkCollision (Move moveToCheck)
  {
    List<Tile> neighbors = house.neighborsInDirection(zombieTile,
                                                      mover.getRotation());


    Rectangle2D.Float test = new Rectangle2D.Float(moveToCheck.col,
                                                   moveToCheck.row,
                                                   mover.getWidth(),
                                                   mover.getHeight());

    if (mover.getStrategy() instanceof LineMoveStrategy)
    {
      //
    }

    for (Tile neighbor : neighbors)
    {
      if (neighbor instanceof Wall)
      {
        if (test.intersects(neighbor.getBoundingRectangle()))
        {
          mover.setSpeed(0);
          return;
        }
      }
//      else if (test.intersects(house.getPlayer().getBoundingRectangle()))
//      {
//        house.getPlayer().setState(Player.PlayerState.DEAD);
//      }
      else if (neighbor.getTrap() == Trap.FIRE)
      {
        zombies.remove(mover);
        List<Tile> explode = house.neighbors(zombieTile);
        for (Tile tile : explode)
        {
          tile.setTrap(Trap.ACTIVATED);


        }
        zombieTile.setTrap(Trap.ACTIVATED);

      }
    }
    mover.move(moveToCheck.col, moveToCheck.row);

  }
}
