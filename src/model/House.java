package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//import com.sun.tools.javac.util.ArrayUtils;
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

  private HouseGenerator generator;
  private List<Room> rooms;


  public House(Character player)
  {
    this.player = player;
    // initialization of matrix
    this.setSize(rows, cols);

    // random location with no obstacles
    // player will need to be placed after the house is generated
    // leaving this here for now so as not to break things.
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
    House.rows = rows;
    House.cols = cols;

    house = new Tile[rows][cols];

    // Initialize the house to Empty tiles with random costs
    for (int row = 0; row < rows; row++)
    {
      for (int col = 0; col < cols; col++)
      {
        house[row][col] = new Empty(col, row);
      }
    }
  }

  /**
   * Generates a random house based on class parameters
   */
  public void generateRandomHouse()
  {
    generator = new HouseGenerator();
    generator.generateHouse();
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
    List<Tile> neighbors = new ArrayList<>(4);
    int row = current.getY();
    int col = current.getX();
    Tile neighbor;

    if ((neighbor = getTile(row-1, col)) != null) neighbors.add(neighbor);
    if ((neighbor = getTile(row, col+1)) != null) neighbors.add(neighbor);
    if ((neighbor = getTile(row+1, col)) != null) neighbors.add(neighbor);
    if ((neighbor = getTile(row, col-1)) != null) neighbors.add(neighbor);
    return neighbors;
  }

  /**
   * Get a tile
   *
   * @param row row
   * @param col column
   * @return Tile retrieved from matrix
   */
  public Tile getTile(int row, int col)
  {
    // for some reason the exception was holding the java unit tests, so I
    // had to do something like this.
    return validate(row, col) ? house[row][col] : null;
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

  /**
   * Get the tile the player is standing on
   *
   * @return Tile containing the player
   */
  public Tile getPlayerTile()
  {
    return house[(int) player.getCurrentY()][(int) player.getCurrentX()];
  }

  /**
   * Check a given tile if it contains a trap
   *
   * @param tile Must be a Tile retrieved from model.
   * @return True if trap present
   */
  public boolean isTrap(Tile tile)
  {
    return tile.getTrap() == Trap.FIRE;
  }

  @Override
  public String toString()
  {
    String board = "";
    board += "x = Wall\n";
    board += "* = Floor \n\n";

    // draw top boarder
    for (int i = 0; i < cols+2; i++)
    {
      board += "-";
    }
    board += "\n";

    for (int i = 0; i < rows; i++)
    {
      board += "|";
      for (int j = 0; j < cols; j++)
      {
        if (house[i][j] instanceof Floor)
        {
          board += "*";
        }
        else if (house[i][j] instanceof Wall)
        {
          board += "x";
        }
        else
        {
          board += " ";
        }
      }
      board += "|\n";
    }

    // draw bottom boarder
    for (int i = 0; i < cols+2; i++)
    {
      board += "-";
    }
    board += "\n\n\n";
    return board;
  }

  private boolean validate(int row, int col)
  {
    return (col >= 0 && col < this.getWidth())
            && (row >= 0 && row < this.getHeight());
  }


  private class HouseGenerator
  {
    Random rand;
    public HouseGenerator()
    {
      rooms = new ArrayList<>();
      rand = new Random();
    }

    public void generateHouse()
    {
      addRooms();
    }

    public void addRooms()
    {
      int row;
      int col;
      int width;
      int height;
      int minWidth = (int) (cols * .1);
      int maxWidth = (int) (cols * .15);
      int minHeight = (int) (rows * .15);
      int maxHeight = (int) (rows * .25);

      while (rooms.size() < minRooms)
      {
        row = rand.nextInt(rows);
        col = rand.nextInt(cols);
        width = minWidth + rand.nextInt(maxWidth-minWidth);
        height = minHeight + rand.nextInt(maxHeight-minHeight);
        if (validRoom(row, col, width, height))
        {
          rooms.add(new Room(row, col, width, height));
        }
      }
    }

    private boolean validRoom(int row, int col, int width, int height)
    {
      if ((row < 0) || ((row+height) >= rows))
      {
        return false;
      }
      else if ((col < 0) || ((col+width) >= cols))
      {
        return false;
      }
      else
      {
        for (int i = (row-1); i <= (row+height+1); i++)
        {
          if ((i > 0) && (i < rows))
          {
            for (int j = (col-1); j <= (col+width+1); j++)
            {
              if ((j > 0) && (j < cols))
              {
                if (!(house[i][j] instanceof Empty))
                {
                  return false;
                }
              }
            }
          }
        }
      }
      return true;
    }
  }

  private class Room
  {
    int row;
    int col;
    int width;
    int height;

    public Room (int row, int col, int width, int height)
    {
      this.row = row;
      this.col = col;
      this.width = width;
      this.height = height;

      for (int i = row; i <= (row+height); i++)
      {
        for (int j = col; j <= (col+width); j++)
        {
          house[i][j] = new Floor(j, i, 10);
        }
      }
    }

    public int getRow()
    {
      return row;
    }

    public int getCol()
    {
      return col;
    }

    public int getWidth()
    {
      return width;
    }

    public int getHeight()
    {
      return height;
    }

    public Tile[][] getRoom()
    {
      Tile[][] room = new Tile[height][width];
      for (int i = 0; i <= height; i++)
      {
        for (int j = 0; j <= width; j++)
        {
          room[i][j] = house[row+i][col+j];
        }
      }
      return room;
    }
  }

  public static void main(String[] args)
  {
    House house = new House(new Character());
    house.generateRandomHouse();
    System.out.println(house);
  }
}
