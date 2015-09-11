package model;

import java.util.ArrayList;
import java.util.List;
import model.Tile.Trap;

public class House
{
  // Dependency in GameEngine line 27
  public static final int WIDTH = 80;
  public static final int HEIGHT = 80;

  // defaults specified in requirements
  private static int rows = 21;
  private static int cols = 36;

  private Tile[][] house = new Tile[rows][cols];

  private List<Zombie> zombies = new ArrayList<>();
  private Character player;

  // min defaults from requirements
  private int minRooms = 6;
  private int minHallways = 4;

  // how many rooms and hallways are in the generated house
  private int numRooms = 0;
  private int numHallways = 0;

  public House(Character player)
  {
    this.player = player;

    // random location with no obstacles
    this.player.move(0, 1);
  }

  /**
   * Sets the size of the house
   * NOTE: This will re-initialize the house
   *
   * @param rows number of rows for the new house
   * @param cols number of columns for the new house
   */
  public void setSize(int rows, int cols)
  {
    this.rows = rows;
    this.cols = cols;
    house = new Tile[rows][cols];
  }

  /**
   * Gets the width of the house
   *
   * @return columns
   */
  public int getWidth()
  {
    return cols;
  }

  /**
   * Gets the height of the house (rows)
   *
   * @return rows
   */
  public int getHeight()
  {
    return rows;
  }

  /**
   * Get the current house layout
   *
   * @return house[][]
   */
  public Tile[][] getHouse()
  {
    return house;
  }

  /**
   * Gets the number of rooms in the current house
   *
   * @return numRooms
   */
  public int getNumRooms()
  {
    return numRooms;
  }

  /**
   * gets the number of hallways in the current house
   *
   * @return numHallways
   */
  public int getNumHallways()
  {
    return numHallways;
  }

  /**
   * Sets the minimum number of rooms in the generated house
   *
   * @param minRooms minimum number of rooms
   */
  public void setMinRooms(int minRooms)
  {
    this.minRooms = minRooms;
  }

  /**
   * Sets the minimum number of hallways in the generated house
   *
   * @param minHallways minimum number of hallways
   */
  public void setMinHallways(int minHallways)
  {
    this.minHallways = minHallways;
  }

  /**
   * Gets all of the obstacles in the house
   *
   * @return List<Tile>
   */
  public List<Tile> getObstacles()
  {
    List<Tile> obstacles = new ArrayList<>();
    Tile tile;
    for (int row = 0; row < rows; row++)
    {
      for (int col = 0; col < cols; col++)
      {
        tile = house[row][col];
        if (tile instanceof Obstacle)
        {
          obstacles.add(tile);
        }
      }
    }
    return obstacles;
  }

  /**
   * Gets all of the zombies inside the house
   *
   * @return List<Zombie>
   */
  public List<Zombie> getZombies()
  {
    return zombies;
  }

  /**
   * Gets the player inside the house
   *
   * @return player
   */
  public Character getPlayer()
  {
    return player;
  }

  /**
   * Gets the neighboring tiles around the current tile
   * i.e. tiles which are touching the current tile
   *
   * @param current The tile to get neighbors around
   * @return List<Tile>
   */
  public List<Tile> neighbors(Tile current)
  {
    return null;
  }

  /**
   * Place a trap on the tile
   *
   * @param tile Tile to set the trap on
   * @param trap Type of trap to place on the tile (NONE, FIRE)
   */
  public void placeTrap(Tile tile, Trap trap)
  {
    tile.setTrap(trap);
  }

  public Tile getPlayerTile()
  {
    return house[(int) player.getCurrentX()][(int) player.getCurrentX()];
  }

  public boolean isTrap(Tile tile)
  {
    return tile.getTrap() == Trap.FIRE;
  }
}
