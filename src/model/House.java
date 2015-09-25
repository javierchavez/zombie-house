package model;

import common.Direction;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;


public class House extends Area
{
  private Tile[][] house;

  private List<Zombie> zombies = new ArrayList<>();
  private Player player;
  private SuperZombie superZombie = new SuperZombie();

  private HouseGenerator generator;
  private String savedHouse;


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

  public int getRows()
  {
    return (int) getHeight();
  }

  public int getCols()
  {
    return (int) getWidth();
  }

  /**
   * Initialize the house to all empty Tiles
   * Resets zombies
   */
  public void initHouse()
  {
    house = new Tile[getRows()][getCols()];
    zombies = new ArrayList<>();

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

  public void generateRandomHouse()
  {
    generator.generateHouse(this);
    save();
  }

  /**
   * Generates a random house based on class parameters
   */
  public void generateRandomHouse(GameOptions.GAME_STATE level)
  {
    generator.generateHouse(this, level);
    save();
  }


  public void save()
  {
    savedHouse = toString();
  }

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
            zombie = new Zombie();
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
            house[row][col] = new Wall(col, row);
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
   * Gets all of the zombies inside the house
   *
   * @return List<Zombie>
   */
  public List<Zombie> getZombies()
  {
    return zombies;
  }

  public void addZombie(Zombie zombie)
  {
    zombies.add(zombie);
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
   * Gets the super zombie in the house
   *
   * @return SuperZombie
   */
  public SuperZombie getSuperZombie()
  {
    return superZombie;
  }

  public void setSuperZombie(SuperZombie superZombie)
  {
    this.superZombie = superZombie;
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
    else if (dir == Direction.NORTHEAST)
    {
      if ((neighbor = getTile(row-1, col)) != null) neighbors.add(neighbor);
      if ((neighbor = getTile(row-1, col+1)) != null) neighbors.add(neighbor);
      if ((neighbor = getTile(row, col+1)) != null) neighbors.add(neighbor);
    }
    else if (dir == Direction.NORTHWEST)
    {
      if ((neighbor = getTile(row-1, col)) != null) neighbors.add(neighbor);
      if ((neighbor = getTile(row-1, col-1)) != null) neighbors.add(neighbor);
      if ((neighbor = getTile(row, col-1)) != null) neighbors.add(neighbor);
    }
    else if (dir == Direction.SOUTHEAST)
    {
      if ((neighbor = getTile(row+1, col)) != null) neighbors.add(neighbor);
      if ((neighbor = getTile(row+1, col+1)) != null) neighbors.add(neighbor);
      if ((neighbor = getTile(row, col+1)) != null) neighbors.add(neighbor);
    }
    else if (dir == Direction.SOUTHWEST)
    {
      if ((neighbor = getTile(row+1, col)) != null) neighbors.add(neighbor);
      if ((neighbor = getTile(row+1, col-1)) != null) neighbors.add(neighbor);
      if ((neighbor = getTile(row, col-1)) != null) neighbors.add(neighbor);
    }
    else
    {
      neighbors = getAllNeighbors(current);
    }
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

  public void setTile(int row, int col, Tile tile)
  {
    if (validate(row, col))
    {
      house[row][col] = tile;
    }
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

  public Tile getCharacterTile(Character character)
  {
    return getTile((int) character.getCurrentY(), (int) character.getCurrentX());
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

  public List<Combustible> getCombustibleNeighbors(Tile tile)
  {
    List<Combustible> neighbors = new ArrayList<>();
    List<Tile> _nt = getAllNeighbors(tile);
    for (Tile neighbor : _nt)
    {
      if (neighbor.isCombustible())
      {
        neighbors.add(neighbor);
      }
      /////////////// if any tiles contain zombies add the zombie as well /////
      //      if (isZombieTile(tile))
      //      {
      //        neighbors.add(zombies.stream().filter(z -> z.getCurrentX() == neighbor
      //                .getX() && z.getCurrentY() == neighbor.getY())
      //                              .collect(Collectors.toList()).get(0));
      //      }
    }
    /////////////// add the player if on a combusted tile ///////////////
    //    if (_nt.contains(getCharacterTile(player)));
    //    {
    //      neighbors.add(player);
    //    }
    /////////////// add the sz if on a combusted tile ///////////////
    //    if (_nt.contains(getCharacterTile(superZombie)));
    //    {
    //      neighbors.add(superZombie);
    //    }

    return neighbors;
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
      board += "\n";
    }
    return board;
  }

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

  private boolean validate(int row, int col)
  {
    return (col >= 0 && col < this.getWidth())
            && (row >= 0 && row < this.getHeight());
  }

  public void slowReset()
  {
    new Thread(() -> {

      // zombies.clear();
      while(true)
      {

        for (int i = 0; i < house.length; i++)
        {
          for (int j = 0; j < house[i].length; j++)
          {

            house[i][j] = new Empty(i, j);

          }

          try
          {
            Thread.sleep(110);
          }
          catch (InterruptedException e)
          {

            e.printStackTrace();
          }
        }

      }
    }).start();
  }

  public static void main(String[] args)
  {
    House house = new House(new Player());
    house.generateRandomHouse();
    System.out.println(house);
  }
}
