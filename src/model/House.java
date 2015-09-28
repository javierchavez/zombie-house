package model;

import common.Direction;
import model.GameOptions.GAME_STATE;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Can be considered House or Level
 */
public class House extends Area
{
  private Tile[][] house;

  private List<Zombie> zombies = new ArrayList<>();
  private Player player;
  private SuperZombie superZombie = new SuperZombie();

  private HouseGenerator generator;
  private String savedHouse;
  private GAME_STATE level;


  /**
   * Creates a House object with a player in it
   *
   * @param player Player object to be stored in the house
   */
  public House(Player player)
  {
    this.player = player;
    this.generator = new HouseGenerator();
    this.initHouse();
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
    setHeight(rows);
    setWidth(cols);
    initHouse();
  }

  /**
   * Initialize the house to all empty Tiles
   * Resets zombies
   */
  public void initHouse()
  {
    house = new Tile[getRows()][getCols()];
    zombies = new ArrayList<>();
    superZombie = new SuperZombie();

    // Initialize the house to Empty tiles with random costs
    for (int row = 0; row < getRows(); row++)
    {
      for (int col = 0; col < getCols(); col++)
      {
        if (row == 0 || row == getRows()-1 || col == 0 || col == getCols()-1)
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
   * Generates a random house with default settings
   */
  public void generateRandomHouse()
  {
    generator.generateHouse(this);
    this.level = GAME_STATE.PLAY;
    save();
  }

  /**
   * Generates a random house for the specified level
   *
   * @param level GAME_STATE level for the house layout difficulty
   */
  public void generateRandomHouse(GAME_STATE level)
  {
    generator.generateHouse(this, level);
    this.level = level;
    save();
  }


  /**
   * Saves the current house state
   */
  public void save()
  {
    savedHouse = toString();
  }

  /**
   * Resets the house to the previous save state
   */
  public void reset()
  {
    int row = 0;
    int col = 0;
    house = new Tile[getRows()][getCols()];
    zombies = new ArrayList<>();
    Zombie zombie;
    Scanner scanner = new Scanner(savedHouse);
    while (scanner.hasNextLine())
    {
      for (char c : scanner.nextLine().toCharArray())
      {
        switch (c)
        {
          case 'P':
            player.move(col, row);
            house[row][col] = new Floor(col, row, 10);
            break;
          case 'S':
            superZombie = new SuperZombie();
            superZombie.move(col, row);
            house[row][col] = new Floor(col, row, 10);
            break;
          case 'Z':
            zombie = new Zombie(new RandomMoveStrategy());
            zombie.move(col, row);
            zombies.add(zombie);
            house[row][col] = new Floor(col, row, 10);
            break;
          case 'z':
            zombie = new Zombie(new LineMoveStrategy());
            zombie.move(col, row);
            zombies.add(zombie);
            house[row][col] = new Floor(col, row, 10);
            break;
          case 'T':
            house[row][col] = new Floor(col, row, 10);
            house[row][col].setTrap(Trap.FIRE);
            break;
          case 'O':
            house[row][col] = new Obstacle(col, row);
            break;
          case '*':
            house[row][col] = new Floor(col, row, 10);
            break;
          case 'X':
            Wall eWall = new Wall(col, row);
            house[row][col] = eWall;
            eWall.setWallType(WallType.EXTERIOR);
            break;
          case 'x':
            Wall iWall = new Wall(col, row);
            house[row][col] = iWall;
            iWall.setWallType(WallType.INTERIOR);
            break;
          case 'e':
            house[row][col] = new Exit(col, row);
            break;
          default:
            house[row][col] = new Empty(col, row, 999999999);
        }
        col++;
      }
      col = 0;
      row++;
    }
  }

  /**
   * Get the current house layout
   *
   * @return House array of tiles
   */
  public Tile[][] getHouse()
  {
    return house;
  }

  /**
   * Gets the number of rows in the house array
   *
   * @return Number of rows
   */
  public int getRows()
  {
    return (int) getHeight();
  }

  /**
   * Gets the number of columns in the house array
   *
   * @return Number of columns
   */
  public int getCols()
  {
    return (int) getWidth();
  }

  /**
   * Gets the level of the house
   *
   * @return level
   */
  public GAME_STATE getLevel()
  {
    return level;
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
    return validate(row, col) ? house[row][col] : null;
  }

  /**
   * Places a tile in the house
   *
   * @param row row in the house array
   * @param col col in the house array
   * @param tile Tile to add the the row and col in the house array
   */
  public void setTile(int row, int col, Tile tile)
  {
    if (validate(row, col))
    {
      house[row][col] = tile;
    }
  }

  /**
   * Get the Exit tile of the house
   *
   * @return Tile where whe exit is
   */
  public Tile getExit()
  {
    for (int row = 0; row < getRows(); row++)
    {
      for (int col = 0; col < getCols(); col++)
      {
        if (house[row][col] instanceof Exit)
        {
          return house[row][col];
        }
      }
    }
    return null;
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
    for (int row = 0; row < getRows(); row++)
    {
      for (int col = 0; col < getCols(); col++)
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
   * Gets the tile which the character is standing on
   *
   * @param character Character object
   * @return Tile from the house array where the character is standing
   */
  public Tile getCharacterTile(Character character)
  {
    return getTile((int) character.getCurrentY(), (int) character.getCurrentX());
  }

  /**
   * Gets the player inside the house
   *
   * @return player
   */
  public Player getPlayer()
  {
    return player;
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
   * Adds a zombie to the house
   *
   * @param zombie Zombie to add to the hosue
   */
  public void addZombie(Zombie zombie)
  {
    zombies.add(zombie);
  }

  /**
   * Gets the super zombie in the house
   *
   * @return SuperZombie
   */
  public SuperZombie getSuperZombie()
  {
    return superZombie;
  }

  /**
   * Add a super zombie object to the house
   *
   * @param superZombie SuperZombie
   */
  public void setSuperZombie(SuperZombie superZombie)
  {
    this.superZombie = superZombie;
  }

  /**
   * Tests Whether a zombie is standing on the tile
   *
   * @param tile Tile
   * @return True if a zombie is standing on the tile, false otherwise
   */
  public boolean isZombieTile(Tile tile)
  {
    if (tile == getCharacterTile(superZombie))
    {
      return true;
    }

    for (Zombie zombie : zombies)
    {
      if (getCharacterTile(zombie) == tile)
      {
        return true;
      }
    }
    return false;
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
    int row = current.getRow();
    int col = current.getCol();
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
    int row = current.getRow();
    int col = current.getCol();
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
   * Gets the neighbors only in the specified direction of the current tile
   *
   * @param current Tile to get neighbors around
   * @param dir A direction specifying which side of the current tile to get neighbors from
   * @return A List of tiles neighboring current in the specified direction
   */
  public List<Tile> neighborsInDirection(Tile current, float dir)
  {
    List<Tile> neighbors = new ArrayList<>(4);
    int row = current.getRow();
    int col = current.getCol();
    Tile neighbor;
    if (dir == Direction.NORTH)
    {
      if ((neighbor = getTile(row+1, col-1)) != null) neighbors.add(neighbor);
      if ((neighbor = getTile(row+1, col)) != null) neighbors.add(neighbor);
      if ((neighbor = getTile(row+1, col+1)) != null) neighbors.add(neighbor);
    }
    else if (dir == Direction.SOUTH)
    {
      if ((neighbor = getTile(row-1, col-1)) != null) neighbors.add(neighbor);
      if ((neighbor = getTile(row-1, col)) != null) neighbors.add(neighbor);
      if ((neighbor = getTile(row-1, col+1)) != null) neighbors.add(neighbor);
    }
    else if (dir == Direction.EAST)
    {
      if ((neighbor = getTile(row-1, col+1)) != null) neighbors.add(neighbor);
      if ((neighbor = getTile(row, col+1)) != null) neighbors.add(neighbor);
      if ((neighbor = getTile(row+1, col+1)) != null) neighbors.add(neighbor);
    }
    else if (dir == Direction.WEST)
    {
      if ((neighbor = getTile(row-1, col-1)) != null) neighbors.add(neighbor);
      if ((neighbor = getTile(row, col-1)) != null) neighbors.add(neighbor);
      if ((neighbor = getTile(row+1, col-1)) != null) neighbors.add(neighbor);
    }
    else if (dir == Direction.SOUTHEAST)
    {
      if ((neighbor = getTile(row-1, col)) != null) neighbors.add(neighbor);
      if ((neighbor = getTile(row-1, col+1)) != null) neighbors.add(neighbor);
      if ((neighbor = getTile(row, col+1)) != null) neighbors.add(neighbor);
    }
    else if (dir == Direction.SOUTHWEST)
    {
      if ((neighbor = getTile(row-1, col)) != null) neighbors.add(neighbor);
      if ((neighbor = getTile(row-1, col-1)) != null) neighbors.add(neighbor);
      if ((neighbor = getTile(row, col-1)) != null) neighbors.add(neighbor);
    }
    else if (dir == Direction.NORTHEAST)
    {
      if ((neighbor = getTile(row+1, col)) != null) neighbors.add(neighbor);
      if ((neighbor = getTile(row+1, col+1)) != null) neighbors.add(neighbor);
      if ((neighbor = getTile(row, col+1)) != null) neighbors.add(neighbor);
    }
    else if (dir == Direction.NORTHWEST)
    {
      if ((neighbor = getTile(row+1, col)) != null) neighbors.add(neighbor);
      if ((neighbor = getTile(row+1, col-1)) != null) neighbors.add(neighbor);
      if ((neighbor = getTile(row, col-1)) != null) neighbors.add(neighbor);
    }
    else
    {
      // Return all neighbors if direction is unknown
      neighbors = getAllNeighbors(current);
    }
    return neighbors;
  }

  /**
   * Gets all of the tiles around tile which can combuest
   *
   * @param tile Tile to get neighbors around
   * @return A List of tiles which can combust around tile
   */
  public List<Combustible> getCombustibleNeighbors(Tile tile)
  {
    List<Combustible> neighbors = new ArrayList<>();
    List<Zombie> _zList = new ArrayList<>();
    _zList.addAll(zombies);
    List<Tile> _nt = getAllNeighbors(tile);
    for (Tile neighbor : _nt)
    {
      if (neighbor.isCombustible())
      {
        neighbors.add(neighbor);
      }
      if (isZombieTile(neighbor))
      {
        // very brute force..... needs optimization
        for (int i = 0; i < _zList.size(); i++)
        {
          if (getCharacterTile(_zList.get(i)) == neighbor)
          {
            neighbors.add(_zList.get(i));
            _zList.remove(i);
          }
        }
      }
      else if (getCharacterTile(superZombie) == neighbor)
      {
        neighbors.add(superZombie);
      }
    }
    return neighbors;
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
   * Check a given tile if it contains a trap
   *
   * @param tile Must be a Tile retrieved from model.
   * @return True if trap present
   */
  public boolean isTrap(Tile tile)
  {
    return tile.getTrap() == Trap.FIRE;
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
    return (float) Math.sqrt(Math.pow((end.getCol()-start.getCol()),2) + Math.pow((end.getRow()-start.getRow()),2));
  }

  /**
   * Slowly resets the board
   */
  public void slowReset()
  {
    for (int i = 0; i < house.length; i++)
    {
      for (int j = 0; j < house[i].length; j++)
      {
        house[i][j] = new Empty(i, j);
      }

      try
      {
        Thread.currentThread().sleep(110);
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }
  }

  @Override
  public String toString()
  {
    String board = "";
    Tile current;
    for (int row = 0; row < getRows(); row++)
    {
      for (int col = 0; col < getCols(); col++)
      {
        current = house[row][col];
        if (current == getCharacterTile(player))
        {
          board += "P";
        }
        else if (current == getCharacterTile(superZombie))
        {
          board += "S";
        }
        else if (isZombieTile(current))
        {
          for (Zombie zombie : zombies)
          {
            if (current == getCharacterTile(zombie))
            {
              if (zombie.getStrategy() instanceof RandomMoveStrategy)
              {
                board += "Z";
              }
              else
              {
                board += "z";
              }
            }
          }
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
          if (((Wall) current).getWallType() == WallType.EXTERIOR)
          {
            board += "X";
          }
          else
          {
            board += "x";
          }
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
      board += "\n";
    }
    return board;
  }

  private boolean validate(int row, int col)
  {
    return (col >= 0 && col < this.getWidth())
            && (row >= 0 && row < this.getHeight());
  }

  public static void main(String[] args)
  {
    House house = new House(new Player());
    house.generateRandomHouse(GameOptions.GAME_STATE.LEVEL5);
    System.out.println(house);
  }
}
