package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class House
{
  // defaults specified in requirements
  private static int rows = 21;
  private static int cols = 36;

  private Tile[][] house = new Tile[rows][cols];

  private List<Zombie> zombies = new ArrayList<>();
  private Character player;
  private SuperZombie superZombie;

  // min defaults from requirements
  private int minRooms = 6;
  private int minHallways = 4;
  private int minObstacles = 5;

  // how many rooms and hallways are in the generated house
  private int numHallways = 0;

  private HouseGenerator generator;
  private List<Room> rooms;
  private Tile exit;

  private float zombieSpawn = 0.01f;
  private float trapSpawn = 0.01f;

  // The minimum euclidean distance between the player and exit (inclusive)
  private int minTravelDistance = 15;
  private int maxTries = 500;


  public House(Character player)
  {
    this.player = player;
    // initialization of house
    this.setSize(rows, cols);
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
        if (row == 0 || row == rows-1 || col == 0 || col == cols-1)
        {
          // Tile is on the boarder so set high cost so hallways aren't drawn there
          house[row][col] = new Empty(col, row, 999999999);
        }
        else
        {
          // Create an empty tile with a random cost
          house[row][col] = new Empty(col, row);
        }
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
    placePlayer();
    generateZombies();
    addSuperZombie();
    generateTraps();

  }

  /**
   * Places the player in a random room on a random tile
   */
  public void placePlayer()
  {
    Random rand = new Random();
    Room room;
    Tile tile;
    int row;
    int col;
    int tries = 0;

    do
    {
      room = rooms.get(rand.nextInt(rooms.size()));
      row = room.getRow() + rand.nextInt(room.getHeight());
      col = room.getCol() + rand.nextInt(room.getWidth());
      tile = house[row][col];
      tries++;
    } while ((tries<maxTries)&&(!(tile instanceof Floor)||(getDistance(tile,exit)<minTravelDistance)));
    player.move(col, row);
  }

  /**
   * Generates Zombies in the house based on zombieSpawn
   * Zombie distance from character is at least > zombie smell distance
   * based on euclidean distance from player to zombie
   */
  public void generateZombies()
  {
    Random rand = new Random();
    Zombie zombie;
    Tile tile;

    // zombies only spawn in rooms and not in hallways
    for (Room room : rooms)
    {
      for (int row = room.getRow(); row < room.getRow()+room.getHeight(); row++)
      {
        for (int col = room.getCol(); col < room.getCol()+room.getWidth(); col++)
        {
          tile = house[row][col];
          if (tile instanceof Floor && tile != getPlayerTile())
          {
            if (rand.nextFloat() < zombieSpawn)
            {
              // can add code here to change zombie type
              zombie = new Zombie();
              if (getDistance(tile, getPlayerTile()) > zombie.getSmell())
              {
                zombie.move(col, row);
                zombies.add(zombie);
              }
            }
          }
        }
      }
    }
  }

  public void addSuperZombie()
  {
    Random rand = new Random();
    int row;
    int col;
    int tries = 0;
    float distance;
    Room room;
    Tile tile;
    superZombie = new SuperZombie();

    do
    {
      room = rooms.get(rand.nextInt(rooms.size()));
      row = room.getRow() + rand.nextInt(room.getHeight());
      col = room.getCol() + rand.nextInt(room.getWidth());
      tile = house[row][col];
      distance = getDistance(tile, getPlayerTile());
      tries++;
    } while ((tries<maxTries)&&(!(tile instanceof Floor)||isZombieTile(tile)||(distance<(2*superZombie.getSmell()))));
    superZombie.move(tile.getX(), tile.getY());
  }

  /**
   * Generates Traps in the house based on trapSpawn
   * Traps are not placed on the same tile as a character or zombie
   */
  public void generateTraps()
  {
    Random rand = new Random();
    Tile tile;

    for (int row = 0; row < rows; row++)
    {
      for (int col = 0; col < cols; col++)
      {
        tile = house[row][col];
        if (tile instanceof Floor && tile != getPlayerTile() && !isZombieTile(tile))
        {
          if (rand.nextFloat() < trapSpawn)
          {
            placeTrap(tile, Trap.FIRE);
          }
          else
          {
            placeTrap(tile, Trap.NONE);
          }
        }
      }
    }
  }

  /**
   * Get the Euclidean distance between two tiles
   *
   * @param start Starting tile
   * @param end Ending tile
   * @return Euclidean distance between start and end tiles
   */
  public float getDistance(Tile start, Tile end)
  {
    return (float) Math.sqrt(Math.pow((end.getX()-start.getX()),2) + Math.pow((end.getY()-start.getY()),2));
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
   * Get the Exit tile of the house
   *
   * @return Tile where whe exit is
   */
  public Tile getExit()
  {
    return exit;
  }

  /**
   * Gets the number of rooms in the current house
   *
   * @return numRooms
   */
  public int getNumRooms()
  {
    return rooms.size();
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

  public int getNumObstacles()
  {
    return getObstacles().size();
  }

  public void setMinObstacles(int minObstacles)
  {
    this.minObstacles = minObstacles;
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
   * @return List<Obstacle>
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

  public SuperZombie getSuperZombie()
  {
    return superZombie;
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
   * Gets all of the neighboring tiles around the current tile (including corner tiles)
   * i.e. tiles which are touching the current tile
   *
   * @param current The tile to get neighbors around
   * @return List<Tile>
   */
  public List<Tile> getAllNeighbors(Tile current)
  {
    List<Tile> neighbors = new ArrayList<>(8);
    int row = current.getY();
    int col = current.getX();
    Tile neighbor;

    if ((neighbor = getTile(row-1, col)) != null) neighbors.add(neighbor);
    if ((neighbor = getTile(row, col+1)) != null) neighbors.add(neighbor);
    if ((neighbor = getTile(row+1, col)) != null) neighbors.add(neighbor);
    if ((neighbor = getTile(row, col-1)) != null) neighbors.add(neighbor);

    if ((neighbor = getTile(row-1, col-1)) != null) neighbors.add(neighbor);
    if ((neighbor = getTile(row-1, col+1)) != null) neighbors.add(neighbor);
    if ((neighbor = getTile(row+1, col-1)) != null) neighbors.add(neighbor);
    if ((neighbor = getTile(row+1, col+1)) != null) neighbors.add(neighbor);
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
   * Get the tile the zombie is on
   *
   * @return Tile containing the zombie
   */
  public Tile getZombieTile(Zombie zombie)
  {
    return house[(int) zombie.getCurrentY()][(int) zombie.getCurrentX()];
  }

  public Tile getSuperZombieTile()
  {
    return house[(int) superZombie.getCurrentY()][(int) superZombie.getCurrentX()];
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
    board += "P = Player\n";
    board += "Z = Zombie\n";
    board += "S = Super Zombie\n";
    board += "T = Fire Trap\n";
    board += "O = Obstacle\n";
    board += "X = Wall\n";
    board += "e = Exit\n";
    board += "* = Floor \n\n";

    // draw top boarder
    for (int col = 0; col < cols+2; col++)
    {
      board += "-";
    }
    board += "\n";

    Tile current;
    for (int row = 0; row < rows; row++)
    {
      board += "|";
      for (int col = 0; col < cols; col++)
      {
        current = house[row][col];
        if (current == getPlayerTile())
        {
          board += "P";
        }
        else if (current == getSuperZombieTile())
        {
          board += "S";
        }
        else if (isZombieTile(current))
        {
          board += "Z";
        }
        else if (current.getTrap() == Trap.FIRE)
        {
          board += "T";
        }
        else if (current instanceof Floor)
        {
          board += "*";
        }
        else if (current instanceof Obstacle)
        {
          board += "O";
        }
        else if (current instanceof Wall)
        {
          board += "X";
        }
        else if (current instanceof Exit)
        {
          board += "e";
        }
        else
        {
          board += " ";
        }
      }
      board += "|\n";
    }

    // draw bottom boarder
    for (int col = 0; col < cols+2; col++)
    {
      board += "-";
    }
    board += "\n\n\n";
    return board;
  }

  private boolean isZombieTile(Tile tile)
  {
    if (tile == getSuperZombieTile())
    {
      return true;
    }

    for (Zombie zombie : zombies)
    {
      if (getZombieTile(zombie) == tile)
      {
        return true;
      }
    }
    return false;
  }

  private boolean validate(int row, int col)
  {
    return (col >= 0 && col < this.getWidth())
            && (row >= 0 && row < this.getHeight());
  }


  private class HouseGenerator
  {
    Random rand;
    AStarFindStrategy pathfinder;

    public HouseGenerator()
    {
      rooms = new ArrayList<>();
      rand = new Random();
      pathfinder = new AStarFindStrategy();
    }

    public void generateHouse()
    {
      addRooms();
      addHallways();
      addWalls();
      addObstacles();
      addExit();
    }

    public void addRooms()
    {
      int row;
      int col;
      int width;
      int height;
      int tries = 0;

      // Weights for how big a room can be
      // Don't want rooms to be too big or
      // there won't be enough space to fit
      // in minRooms in the house.
      int minWidth = 3;
      int maxWidth = (cols * (1/(minRooms+1)));
      int minHeight = 3;
      int maxHeight = (rows * (2/minRooms));

      if (maxWidth < minWidth)
      {
        maxWidth = minWidth+1;
      }

      if (maxHeight < minHeight)
      {
        maxHeight = minHeight+1;
      }

      // Create the minimum number of rooms
      while ((tries < maxTries) && (rooms.size() < minRooms))
      {
        row = rand.nextInt(rows);
        col = rand.nextInt(cols);

        // creates the width and height a random value bertween minWidth/Height and maxWidth/Height
        width = minWidth + rand.nextInt(maxWidth-minWidth);
        height = minHeight + rand.nextInt(maxHeight-minHeight);
        if (validRoom(row, col, width, height))
        {
          // Add a room to the house if it does not overlap any other rooms or is out of bounds of the house
          rooms.add(new Room(row, col, width, height));
        }
        tries++;
      }
    }

    private void addHallways()
    {
      Tile startTile;
      Tile endTile;
      List<Tile> path;

      // The nest loop finds a path from every room
      // to every other room
      // This ensures the house is connected
      for (Room fromRoom : rooms)
      {
        // get the approximate center of the room
        startTile = house[fromRoom.getRow()+(fromRoom.getHeight()/2)][fromRoom.getCol()+(fromRoom.getWidth()/2)];
        for (Room toRoom : rooms)
        {
          if (fromRoom != toRoom)
          {
            endTile = house[toRoom.getRow()+(toRoom.getHeight()/2)][toRoom.getCol()+(toRoom.getWidth()/2)];
            pathfinder.find(House.this, startTile, endTile);
            path = pathfinder.getPath();
            for (Tile tile : path)
            {
              // Draw floor tiles on the path between the two rooms
              // don't create a new tile if it is already a floor
              if (tile instanceof Empty)
              {
                house[tile.getY()][tile.getX()] = new Floor(tile.getX(), tile.getY(), 10);
              }
            }
          }
        }
      }
    }

    private void addWalls()
    {
      // Adds a wall to a tile if the tile is Empty
      // and is touching a Floor tile
      Tile tile;
      Wall wall;
      for (int row = 0; row < rows; row++)
      {
        for (int col = 0; col < cols; col++)
        {
          tile = house[row][col];
          if (tile instanceof Empty)
          {
            if (touchesFloor(tile))
            {
              wall = new Wall(col, row);
              house[row][col] = wall;
              if (touchesEmpty(wall))
              {
                wall.setWallType(WallType.EXTERIOR);
              }
              else
              {
                wall.setWallType(WallType.INTERIOR);
              }
            }
            else
            {
              // Tile is Empty so set a high travel cost
              // so there aren't weird cases where a zombie
              // travels through empty space
              house[row][col].setCost(999999999);
            }
          }
        }
      }
    }

    private void addObstacles()
    {
      Random rand = new Random();
      int row;
      int col;

      for (Room room : rooms)
      {
        row = (room.getRow()+1) + rand.nextInt(room.getHeight()-1);
        col = (room.getCol()+1) + rand.nextInt(room.getWidth()-1);
        house[row][col] = new Obstacle(col, row);
      }
    }

    private void addExit()
    {
      Random rand = new Random();
      List<Tile> walls = getWalls();
      Tile wall;
      int index;

      do
      {
        index = rand.nextInt(walls.size());
        wall = walls.get(index);
      } while (!validExit(wall));

      int row = wall.getY();
      int col = wall.getX();
      exit = new Exit(col, row);
      house[row][col] = exit;
    }

    private List<Tile> getWalls()
    {
      List<Tile> walls = new ArrayList<>();
      for (int row = 0; row < rows; row++)
      {
        for (int col = 0; col < cols; col++)
        {
          if (house[row][col] instanceof Wall)
          {
            walls.add(house[row][col]);
          }
        }
      }
      return walls;
    }

    private boolean validExit(Tile wall)
    {
      boolean touchesEmpty = false;
      boolean touchesFloor = false;

      for (Tile tile: neighbors(wall))
      {
        if (tile instanceof Empty) touchesEmpty = true;
        if (tile instanceof Floor) touchesFloor = true;
      }
      return touchesEmpty && touchesFloor;
    }

    private boolean touchesFloor(Tile current)
    {
      for (Tile tile : getAllNeighbors(current))
      {
        if (tile instanceof Floor)
        {
          return true;
        }
      }
      return false;
    }

    private boolean touchesEmpty(Tile current)
    {
      for (Tile tile : getAllNeighbors(current))
      {
        if (tile instanceof Empty)
        {
          return true;
        }
      }
      return false;
    }

    private boolean validRoom(int row, int col, int width, int height)
    {
      // First, check if the room if in bounds of the house
      // and not right at the edge (leave space for a wall)
      if ((row < 1) || ((row+height) >= (rows-1)))
      {
        return false;
      }
      else if ((col < 1) || ((col+width) >= (cols-1)))
      {
        return false;
      }
      else
      {
        // Make sure there is at least one space for a wall between rooms
        // (two rooms adjacent would just look like one big room)
        // Make sure the room isn't overlapping any other rooms or obstacles
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

    public Room(int row, int col, int width, int height)
    {
      this.row = row;
      this.col = col;
      this.width = width;
      this.height = height;

      for (int i = row; i <= (row + height); i++)
      {
        for (int j = col; j <= (col + width); j++)
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
  }

  public static void main(String[] args)
  {
    House house = new House(new Character());
    house.generateRandomHouse();
    System.out.println(house);
  }
}
