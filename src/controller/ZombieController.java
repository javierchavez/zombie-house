package controller;


import model.*;
import model.Character;

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
  private int intelligence; // TODO: placeholder. Each zombie should be given an intelligence when generated

  // An incrementer to keep track of when 60 frames (1 second) have passed
  private int time = 0;
  private Random rand = new Random();

  // The values of these ints can be either -1, 0, or 1
  // Depending on what their value is, the zombie will know which direction to go
  int xDir;
  int yDir;

  private boolean DEBUG = true;

  // TODO: randomly decide if zombie will be random walk or line walk
  // NOTE: random walk and line walk have different intelligence
  public ZombieController (House house)
  {
    super(house);
  }

  @Override
  public void checkCollision (float deltaTime)
  {
    // Random and Line handle collision differently
    // Line changes direction immediately; Random keeps trying to go, but they should change direction in the next decision update
  }

  @Override
  public void update(float deltaTime)
  {
    zombies = house.getZombies();

    time++;
    for (int i = 0; i < zombies.size(); i++)
    {
      isMoving = true;
      float zombieSpeed;
      Zombie zombie;
      Tile zombieTile;

      mover = zombie = zombies.get(i);
      zombieTile = house.getZombieTile(zombie);

      float direction = mover.getRotation();

      if (DEBUG)
      {
        System.out.println("Zombie " + i + ": (" + zombie.getCurrentX() + ", " + zombie.getCurrentY() + ")");
      }
      x = zombie.getCurrentX();
      y = zombie.getCurrentY();

      zombieSpeed = Mover.STAGGER_SPEED;
      if (running) zombieSpeed = Mover.RUN_SPEED;

      if (isMoving)
      {
        playerDetected = zombie.sense(house, zombieTile);

        if (DEBUG) System.out.println("\tPlayer detected: " + playerDetected);

        if (playerDetected)
        {
          running = true;
          // TODO: zombie travels on path to player
          Tile nextTile = zombie.find(house);
          System.out.println("\tNext Tile: " + nextTile.getX() + "," + nextTile.getY());
        }
        else
        {
          zombieSpeed = Mover.STAGGER_SPEED;

          // TODO: remove this later
          intelligence = 0;

          // Random walk zombies
          if (intelligence == 0)
          {
            // TODO: make this time longer?
            if (time % 60 == 0) // Zombie changes position every 1 second
            {
              xDir = rand.nextInt(3) - 1;
              yDir = rand.nextInt(3) - 1;
            }
          }
          else if (intelligence == 1)
          {
            // TODO: Test collisions
            if (time % 60 == 0) // change this to: if (collision)
            {
              int changeDir = rand.nextInt(1);
              System.out.println(changeDir);
              if (DEBUG) System.out.println("collision");
              if (changeDir == 0) xDir = rand.nextInt(3) - 1;
              else yDir = rand.nextInt(3) - 1;
            }
          }

          if (xDir == 0 && yDir == 0 && intelligence == 0) resting();
          // Cardinal directions
          if (xDir < 0 && yDir == 0) moveLeft();
          if (xDir > 0 && yDir == 0) moveRight();
          if (yDir > 0 && xDir == 0) moveUp();
          if (yDir < 0 && xDir == 0) moveDown();
          // Ordinal directions
          if (xDir < 0 && yDir > 0) moveUpLeft();
        }

        if (idling) zombieSpeed = Mover.IDLE;

        zombie.setSpeed(zombieSpeed * deltaTime);

        // Update zombie's position
        if (moveUp || moveDown) y = (float) (y + zombie.getSpeed() *
                Math.sin(Math.toRadians(direction)));
        if (moveLeft || moveRight) x = (float) (x + zombie.getSpeed() *
                Math.cos(Math.toRadians(direction)));

        zombie.move(x, y);
        isMoving = false;
      }
    } // END FOR
  }

}
