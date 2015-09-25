package model;


/**
 *
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


  public void setLevel(GameOptions.GAME_STATE level)
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
        break;
      default:
        rows = 21;
        cols = 36;
        minRooms = 8;
        minHallways = 4;
        minObstacles = 5;
        minHallwayLength = 3;
        zombieSpawn = 0.05f;
        trapSpawn = 0.05f;
        minZombieDistance = 10;
        minTravelDistance = 10;
        break;
    }
  }
}
