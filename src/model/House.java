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

  private Tile[][] board = new Tile[rows][cols];

  private List<Zombie> zombies = new ArrayList<>();
  private Character player;

  // min defaults from requirements
  private int minRooms = 6;
  private int minHallways = 4;

  // how many rooms and hallways are in the generated board
  private int numRooms = 0;
  private int numHallways = 0;


  public House (Character player)
  {
    this.player = player;
  }


  /**
   * Sets the size of the board
   * NOTE: This will re-initialize the board
   *
   * @param rows number of rows for the new board
   * @param cols number of columns for the new board
   */
  public void setSize (int rows, int cols)
  {
    this.rows = rows;
    this.cols = cols;
    board = new Tile[rows][cols];
  }

  /**
   * Gets the width of the house
   *
   * @return Number of columns in the board (int)
   */
  public int getWidth ()
  {
    return cols;
  }

  /**
   * Gets the height of the board
   *
   * @return Number of rows in the board (int)
   */
  public int getHeight ()
  {
    return rows;
  }

  /**
   * Gets all of the obstacles in the house
   *
   * @return A List<Tile> of Obstacles which are inside the house
   */
  public List<Tile> getObstacles ()
  {
    List<Tile> obstacles = new ArrayList<>();
    Tile tile;
    for (int row = 0; row < rows; row++)
    {
      for (int col = 0; col < cols; col++)
      {
        tile = board[row][col];
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
   * @return A List<Zombie> which are inside the house
   */
  public List<Zombie> getZombies ()
  {
    return zombies;
  }

  /**
   * Gets the player inside the house
   *
   * @return The player object inside the house
   */
  public Character getPlayer ()
  {
    return player;
  }

  /**
   * Gets the neighboring tiles around the current tile
   *
   * @param current The tile to get neighbors around
   * @return A List<Tile> which are touching the current tile
   */
  public List<Tile> neighbors (Tile current)
  {
    return null;
  }

  /**
   * Place a trap on the tile
   *
   * @param tile Tile to set the trap on
   * @param trap Type of trap to place on the tile (NONE, FIRE)
   */
  public void placeTrap (Tile tile, Trap trap)
  {
    tile.setTrap(trap);
  }
}
