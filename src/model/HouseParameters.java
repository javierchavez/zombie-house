package model;

/**
 * @author Javier Chavez
 * @author Alex Baker
 * @author Erin Sosebee
 * <p>
 * Date September 28, 2015
 * CS 351
 * Zombie House
 * <p>
 * A list of parameters which help define the house
 */

import common.CharacterAttributes;
import common.Duration;
import common.Speed;

/**
 * Data class for defining house parameters and changing them
 * based on a given level
 */
public class HouseParameters
{
  public int rows;
  public int cols;

  public int minRooms;
  public int minHallways;
  public int minObstacles;

  public int minHallwayLength;

  public float zombieSpawn;
  public float trapSpawn;

  public int minZombieDistance;
  public int minTravelDistance;


  /**
   * Creates a HouseParameter object.
   * Parameters changed in this object do
   * not change other instances
   */
  public HouseParameters ()
  {
    setLevel(GameOptions.GAME_STATE.PLAY);
  }

  /**
   * Sets the parameters to values based on the given level
   *
   * @param level GAME_STATE level
   */
  public void setLevel (GameOptions.GAME_STATE level)
  {
    switch (level)
    {
      case LEVEL1:
        rows = 21;
        cols = 36;
        minRooms = 6;
        minHallways = 4;
        minObstacles = 5;
        minHallwayLength = 3;
        zombieSpawn = 0.01f;
        trapSpawn = 0.01f;
        minZombieDistance = 10;
        minTravelDistance = 10;
        Empty.minCost = 15;
        Empty.maxCost = 30;

        CharacterAttributes.SIGHT = 5f;
        CharacterAttributes.HEARING = 10f;
        CharacterAttributes.MAX_STAMINA = 5f;
        CharacterAttributes.STAMINA_REGEN = 0.2f;
        CharacterAttributes.SMELL = 7f;

        Duration.BURN_DURATION = 15f;
        Duration.PICKUP_TIME = 5f;
        Duration.SUPER_ZOMBIE_UPDATE = 2.5f;
        Duration.ZOMBIE_UPDATE = 2f;

        Speed.IDLE = 0f;
        Speed.WALK = 1f;
        Speed.RUN = 2f;
        Speed.FAST_WALK = 1.3f;
        Speed.STAGGER = 0.5f;
        Speed.STAGGER_RUN = 0.75f;
        break;
      case LEVEL2:
        rows = (int) (21 * 1.5);
        cols = (int) (36 * 1.5);
        minRooms = 8;
        minHallways = 4;
        minObstacles = 5;
        minHallwayLength = 3;
        zombieSpawn = 0.02f;
        trapSpawn = 0.01f;
        minZombieDistance = 9;
        minTravelDistance = 12;
        Empty.minCost = 15;
        Empty.maxCost = 30;

        CharacterAttributes.SIGHT = 5f;
        CharacterAttributes.HEARING = 10f;
        CharacterAttributes.MAX_STAMINA = 7f;
        CharacterAttributes.STAMINA_REGEN = 0.5f;
        CharacterAttributes.SMELL = 7f;

        Duration.BURN_DURATION = 7f;
        Duration.PICKUP_TIME = 2f;
        Duration.SUPER_ZOMBIE_UPDATE = 2f;
        Duration.ZOMBIE_UPDATE = 2f;

        Speed.IDLE = 0f;
        Speed.WALK = 1.5f;
        Speed.RUN = 3f;
        Speed.FAST_WALK = 2.25f;
        Speed.STAGGER = 1f;
        Speed.STAGGER_RUN = 1.25f;
        break;
      case LEVEL3:
        rows = 21 * 2;
        cols = 36 * 2;
        minRooms = 12;
        minHallways = 4;
        minObstacles = 5;
        minHallwayLength = 3;
        zombieSpawn = 0.03f;
        trapSpawn = 0.02f;
        minZombieDistance = 9;
        minTravelDistance = 15;
        Empty.minCost = 10;
        Empty.maxCost = 20;

        CharacterAttributes.SIGHT = 4f;
        CharacterAttributes.HEARING = 12f;
        CharacterAttributes.MAX_STAMINA = 7f;
        CharacterAttributes.STAMINA_REGEN = 0.5f;
        CharacterAttributes.SMELL = 7f;

        Duration.BURN_DURATION = 5f;
        Duration.PICKUP_TIME = 1f;
        Duration.SUPER_ZOMBIE_UPDATE = 2f;
        Duration.ZOMBIE_UPDATE = 2f;

        Speed.IDLE = 0f;
        Speed.WALK = 1.5f;
        Speed.RUN = 3f;
        Speed.FAST_WALK = 2.5f;
        Speed.STAGGER = 1f;
        Speed.STAGGER_RUN = 1.25f;
        break;
      case LEVEL4:
        rows = (int) (21 * 2.5);
        cols = (int) (36 * 2.5);
        minRooms = 15;
        minHallways = 4;
        minObstacles = 5;
        minHallwayLength = 3;
        zombieSpawn = 0.05f;
        trapSpawn = 0.03f;
        minZombieDistance = 8;
        minTravelDistance = 20;
        Empty.minCost = 8;
        Empty.maxCost = 25;

        CharacterAttributes.SIGHT = 3f;
        CharacterAttributes.HEARING = 15f;
        CharacterAttributes.MAX_STAMINA = 15f;
        CharacterAttributes.STAMINA_REGEN = 1f;
        CharacterAttributes.SMELL = 9f;

        Duration.BURN_DURATION = 5f;
        Duration.PICKUP_TIME = 1f;
        Duration.SUPER_ZOMBIE_UPDATE = 1.5f;
        Duration.ZOMBIE_UPDATE = 2f;

        Speed.IDLE = 0f;
        Speed.WALK = 2f;
        Speed.RUN = 5f;
        Speed.FAST_WALK = 3.5f;
        Speed.STAGGER = 1.5f;
        Speed.STAGGER_RUN = 2.15f;
        break;
      case LEVEL5:
        rows = 21 * 3;
        cols = 36 * 3;
        minRooms = 20;
        minHallways = 4;
        minObstacles = 5;
        minHallwayLength = 3;
        zombieSpawn = 0.08f;
        trapSpawn = 0.05f;
        minZombieDistance = 7;
        minTravelDistance = 30;
        Empty.minCost = 5;
        Empty.maxCost = 30;

        CharacterAttributes.SIGHT = 2f;
        CharacterAttributes.HEARING = 20f;
        CharacterAttributes.MAX_STAMINA = 5f;
        CharacterAttributes.STAMINA_REGEN = 2f;
        CharacterAttributes.SMELL = 9f;

        Duration.BURN_DURATION = 51f;
        Duration.PICKUP_TIME = 3f;
        Duration.SUPER_ZOMBIE_UPDATE = 1f;
        Duration.ZOMBIE_UPDATE = 1.5f;

        Speed.IDLE = 0f;
        Speed.WALK = 2f;
        Speed.RUN = 5f;
        Speed.FAST_WALK = 4f;
        Speed.STAGGER = 1.5f;
        Speed.STAGGER_RUN = 2.25f;
        break;
      default:
        rows = 21;
        cols = 36;
        minRooms = 8;
        minHallways = 4;
        minObstacles = 5;
        minHallwayLength = 3;
        zombieSpawn = 0.01f;
        trapSpawn = 0.01f;
        minZombieDistance = 10;
        minTravelDistance = 10;
        Empty.minCost = 15;
        Empty.maxCost = 30;

        CharacterAttributes.SIGHT = 5f;
        CharacterAttributes.HEARING = 10f;
        CharacterAttributes.MAX_STAMINA = 5f;
        CharacterAttributes.STAMINA_REGEN = 0.2f;
        CharacterAttributes.SMELL = 7f;

        Duration.BURN_DURATION = 15f;
        Duration.PICKUP_TIME = 5f;
        Duration.SUPER_ZOMBIE_UPDATE = 2.5f;
        Duration.ZOMBIE_UPDATE = 2f;

        Speed.IDLE = 0f;
        Speed.WALK = 1f;
        Speed.RUN = 2f;
        Speed.FAST_WALK = 1.3f;
        Speed.STAGGER = 0.5f;
        Speed.STAGGER_RUN = 0.75f;
        break;
    }
  }
}
