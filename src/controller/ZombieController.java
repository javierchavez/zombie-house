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

  private int distToPlayer; // Euclidean distance from zombie to player

  // An incrementer to keep track of when 60 frames (1 second) have passed
  private int time = 0;
  private Random rand = new Random();

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
    Character player = house.getPlayer();


    time++;
    for (int i = 0; i < zombies.size(); i++)
    {
      isMoving = true;
      float zombieSpeed;
      Zombie zombie;

      // Doing this for brevity
      // you really only want mover
      // if you look at the class this class is extending
      // (AbstractCharacterController) all of the move methods manipulate mover
      // which is why you would use that
      mover = zombie = zombies.get(i);

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
        // zombie.distToPlayer
        // if distToPlayer = zombie.getSmell then playerDetected = true else playerDetected = false
        if (playerDetected)
        {
          running = true;
          // TODO: zombie travels on path to player
        }
        else
        {
          zombieSpeed = Mover.STAGGER_SPEED;

          if (time % 60 == 0) // Zombie changes position every 1 second
          {
            xDir = rand.nextInt(3) - 1;
            yDir = rand.nextInt(3) - 1;
          }

          if (xDir == 0 && yDir == 0) resting();
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
    if (DEBUG) System.out.println("End for");
  }

}
