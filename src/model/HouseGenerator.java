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
 * This is the interface for Combustible objects
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Logic for generating a valid house.
 * Parameters for house generation come from the
 * HouseParameters class.
 */
public class HouseGenerator
{
  private final HouseParameters params;
  private final AStarFindStrategy pathfinder;
  private final int maxTries;
  private House house;
  private List<Room> rooms;
  private Tile exit;
  private Random rand;
  private int numHallways;
  private int generationAttempts;


  /**
   * Creates a HouseGenerator object
   */
  public HouseGenerator()
  {
    rooms = new ArrayList<>();
    params = new HouseParameters();
    rand = new Random();
    pathfinder = new AStarFindStrategy();
    numHallways = 0;
    maxTries = 500;
    generationAttempts = 0;
  }

  /**
   * Generate a house using default parameters defined in HouseParameters
   *
   * @param house House object where the generated house will be held
   */
  public void generateHouse(House house)
  {
    this.house = house;
    numHallways = 0;
    rand = new Random();
    rooms = new ArrayList<>();
    this.house.setSize(params.rows, params.cols);

    addRooms();
    addHallways();
    addWalls();
    countHallways();
    addObstacles();
    addExit();
    placePlayer();
    generateZombies();
    addSuperZombie();
    generateTraps();

    // make sure the house is valid, if it is not then keep trying until maxTries is reached
    if (generationAttempts <= maxTries && !isHouseValid())
    {
      generationAttempts++;
      generateHouse(house);
    }
    else
    {
      generationAttempts = 0;
    }
  }

  /**
   * Generate a house from the specified level
   *
   * @param house House object to generate from
   * @param level Difficulty level of the house layout defined in HouseParameters
   */
  public void generateHouse(House house, GameOptions.GAME_STATE level)
  {
    this.house = house;
    numHallways = 0;
    rand = new Random();
    rooms = new ArrayList<>();
    params.setLevel(level);
    this.house.setSize(params.rows, params.cols);

    addRooms();
    addHallways();
    addWalls();
    countHallways();
    addObstacles();
    addExit();
    placePlayer();
    generateZombies();
    addSuperZombie();
    generateTraps();

    // make sure the house is valid, if it is not then keep trying until maxTries is reached
    if (generationAttempts <= maxTries && !isHouseValid())
    {
      generationAttempts++;
      generateHouse(house, level);
    }
    else
    {
      generationAttempts = 0;
    }
  }

  public HouseParameters getParams()
  {
    return params;
  }

  private void addRooms()
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
    int maxWidth = (int) (house.getWidth() * .15);
    int minHeight = 3;
    int maxHeight = (int) (house.getHeight() * .15);

    if (maxWidth <= minWidth)
    {
      maxWidth = minWidth+1;
    }

    if (maxHeight <= minHeight)
    {
      maxHeight = minHeight+1;
    }

    // Create the minimum number of rooms
    while ((tries < maxTries) && (rooms.size() < params.minRooms))
    {
      // don't put room on the boarder
      row = rand.nextInt(house.getRows()-2) + 1;
      col = rand.nextInt(house.getCols()-2) + 1;

      // creates the width and height a random value between minWidth/Height and maxWidth/Height
      width = minWidth + rand.nextInt(maxWidth-minWidth);
      height = minHeight + rand.nextInt(maxHeight-minHeight);
      Room room = new Room(row, col, width, height);
      if (validRoom(room))
      {
        room.addRoom(house);
        rooms.add(room);
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
      startTile = house.getTile(fromRoom.row()+(fromRoom.height()/2), fromRoom.col()+(fromRoom.width()/2));
      for (Room toRoom : rooms)
      {
        if (fromRoom != toRoom)
        {
          endTile = house.getTile(toRoom.row()+(toRoom.height()/2), toRoom.col()+(toRoom.width()/2));
          pathfinder.find(house, startTile, endTile);
          path = pathfinder.getPath();
          for (Tile tile : path)
          {
            // Draw floor tiles on the path between the two rooms
            house.setTile(tile.getRow(), tile.getCol(), new Floor(tile.getCol(), tile.getRow()));
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
    for (int row = 0; row < house.getRows(); row++)
    {
      for (int col = 0; col < house.getCols(); col++)
      {
        tile = house.getTile(row, col);
        if (tile instanceof Empty)
        {
          if (touchesFloor(tile))
          {
            house.setTile(row, col, new Wall(col, row));
          }
          else
          {
            // Tile is Empty so set a high travel cost
            // so there aren't weird cases where a zombie
            // travels through empty space
            house.getTile(row, col).setCost(999999999);
          }
        }
      }
    }

    for (Tile wall : getWalls())
    {
      if (touchesEmpty(wall) || onBoarder(wall))
      {
        ((Wall) wall).setWallType(WallType.EXTERIOR);
      }
      else
      {
        ((Wall) wall).setWallType(WallType.INTERIOR);
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
      row = (room.row()+1) + rand.nextInt(room.height()-1);
      col = (room.col()+1) + rand.nextInt(room.width()-1);
      house.setTile(row, col, new Obstacle(col, row));
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

    int row = wall.getRow();
    int col = wall.getCol();
    exit = new Exit(col, row);
    house.setTile(row, col, exit);
  }

  private boolean validRoom(Room room)
  {
    // First, check if the room if in bounds of the house
    // and not right at the edge (leave space for a wall)
    if ((room.row() < 1) || ((room.row()+room.height()) >= (house.getRows()-1)))
    {
      return false;
    }
    else if ((room.col() < 1) || ((room.col()+room.width()) >= (house.getCols()-1)))
    {
      return false;
    }
    else
    {
      // Make sure there is at least one space for a wall between rooms
      // (two rooms adjacent would just look like one big room)
      // Make sure the room isn't overlapping any other rooms or obstacles
      for (int row = (room.row()-1); row <= (room.row()+room.height()+1); row++)
      {
        if ((row > 0) && (row < house.getRows()))
        {
          for (int col = (room.col()-1); col <= (room.col()+room.width()+1); col++)
          {
            if ((col > 0) && (col < house.getCols()))
            {
              if (!(house.getTile(row, col) instanceof Empty))
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

  private boolean validExit(Tile wall)
  {
    boolean touchesEmpty = false;
    boolean touchesFloor = false;

    for (Tile tile: house.neighbors(wall))
    {
      if (tile instanceof Empty) touchesEmpty = true;
      if (tile instanceof Floor) touchesFloor = true;
    }
    return touchesEmpty && touchesFloor;
  }

  private void countHallways()
  {
    numHallways = 0;
    int hallwayLength = 0;

    // check horizontal hallways
    for (int row = 1; row < house.getRows()-1; row++)
    {
      for (int col = 1; col < house.getCols()-1; col++)
      {
        if (isHorizontalHallway(house.getTile(row, col)))
        {
          hallwayLength++;
        }
        else
        {
          if (hallwayLength >= params.minHallwayLength)
          {
            numHallways++;
          }
          hallwayLength = 0;
        }
      }
      hallwayLength = 0;
    }

    // check vertical hallways
    for (int col = 1; col < house.getCols()-1; col++)
    {
      for (int row = 1; row < house.getRows()-1; row++)
      {
        if (isVerticalHallway(house.getTile(row, col)))
        {
          hallwayLength++;
        }
        else
        {
          if (hallwayLength >= params.minHallwayLength)
          {
            numHallways++;
          }
          hallwayLength = 0;
        }
      }
      hallwayLength = 0;
    }
  }

  private boolean isHorizontalHallway(Tile tile)
  {
    Tile topTile;
    Tile bottomTile;

    if (!(tile instanceof Floor))
    {
      return false;
    }

    topTile = house.getTile(tile.getRow()-1, tile.getCol());
    bottomTile = house.getTile(tile.getRow()+1, tile.getCol());

    if (topTile == null || bottomTile == null)
    {
      return false;
    }
    else
    {
      return (topTile instanceof Wall) && (bottomTile instanceof Wall);
    }
  }

  private boolean isVerticalHallway(Tile tile)
  {
    Tile leftTile;
    Tile rightTile;

    if (!(tile instanceof Floor))
    {
      return false;
    }

    leftTile = house.getTile(tile.getRow(), tile.getCol()-1);
    rightTile = house.getTile(tile.getRow(), tile.getCol()+1);

    if (leftTile == null || rightTile == null)
    {
      return false;
    }
    else
    {
      return (leftTile instanceof Wall) && (rightTile instanceof Wall);
    }
  }

  private List<Tile> getWalls()
  {
    List<Tile> walls = new ArrayList<>();
    for (int row = 0; row < house.getRows(); row++)
    {
      for (int col = 0; col < house.getCols(); col++)
      {
        if (house.getTile(row, col) instanceof Wall)
        {
          walls.add(house.getTile(row, col));
        }
      }
    }
    return walls;
  }


  private boolean touchesFloor(Tile current)
  {
    for (Tile tile : house.getAllNeighbors(current))
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
    for (Tile tile : house.getAllNeighbors(current))
    {
      if (tile instanceof Empty)
      {
        return true;
      }
    }
    return false;
  }

 private boolean onBoarder(Tile tile)
 {
   int row = tile.getRow();
   int col = tile.getCol();
   return row <= 0 || col <= 0 || row >= house.getRows() - 1 || col >= house.getCols() - 1;
 }

  private void placePlayer()
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
      row = room.row() + rand.nextInt(room.height()+1);
      col = room.col() + rand.nextInt(room.width()+1);
      tile = house.getTile(row, col);
      tries++;
    } while ((tries<maxTries)&&(!(tile instanceof Floor)||(house.getDistance(tile, exit)<params.minTravelDistance)));
    house.getPlayer().move(col, row);
  }

  private void generateZombies()
  {
    Random rand = new Random();
    Zombie zombie;
    Tile tile;

    // zombies only spawn in rooms and not in hallways
    for (Room room : rooms)
    {
      for (int row = room.row(); row <= room.row()+room.height(); row++)
      {
        for (int col = room.col(); col <= room.col()+room.width(); col++)
        {
          tile = house.getTile(row, col);
          if (tile instanceof Floor && tile != house.getCharacterTile(house.getPlayer()))
          {
            if (rand.nextFloat() < params.zombieSpawn)
            {
              if (rand.nextBoolean())
              {
                zombie = new Zombie(new RandomMoveStrategy());
              }
              else
              {
                zombie = new Zombie(new LineMoveStrategy());
              }

              if (house.getDistance(tile, house.getCharacterTile(house.getPlayer())) > zombie.getSmell())
              {
                zombie.move(col, row);
                house.addZombie(zombie);
              }
            }
          }
        }
      }
    }
  }

  private void addSuperZombie()
  {
    Random rand = new Random();
    int row;
    int col;
    int tries = 0;
    float distance;
    Room room;
    Tile tile;
    SuperZombie superZombie = new SuperZombie();

    do
    {
      room = rooms.get(rand.nextInt(rooms.size()));
      row = room.row() + rand.nextInt(room.height()+1);
      col = room.col() + rand.nextInt(room.width()+1);
      tile = house.getTile(row, col);
      distance = house.getDistance(tile, house.getCharacterTile(house.getPlayer()));
      tries++;
    } while ((tries<maxTries)&&(!(tile instanceof Floor)||house.isZombieTile(tile)||(distance<(2*params.minZombieDistance))));
    superZombie.move(tile.getCol(), tile.getRow());
    house.setSuperZombie(superZombie);
  }

  private void generateTraps()
  {
    Random rand = new Random();
    Tile tile;

    for (int row = 0; row < house.getRows(); row++)
    {
      for (int col = 0; col < house.getCols(); col++)
      {
        tile = house.getTile(row, col);
        if (tile instanceof Floor && tile != house.getCharacterTile(house.getPlayer()) && !house.isZombieTile(tile))
        {
          if (rand.nextFloat() < params.trapSpawn)
          {
            house.placeTrap(tile, Trap.FIRE);
          }
          else
          {
            house.placeTrap(tile, Trap.NONE);
          }
        }
      }
    }
  }

  private boolean isHouseValid()
  {
    try
    {
      assertion(rooms.size() >= params.minRooms);
      assertion(house.getObstacles().size() >= params.minObstacles);
      assertion(numHallways >= params.minHallways);
      assertion(house.getDistance(house.getCharacterTile(house.getPlayer()), exit) >= params.minTravelDistance);
      assertion(!isPlayerTooCloseToZombies());
    }
    catch (AssertionError ex)
    {
      return false;
    }
    return true;
  }

  private boolean isPlayerTooCloseToZombies()
  {
    if (house.getDistance(house.getCharacterTile(house.getPlayer()),house.getCharacterTile(house.getSuperZombie()))<2*params.minZombieDistance)
    {
      return true;
    }

    for (Zombie zombie : house.getZombies())
    {
      if (house.getDistance(house.getCharacterTile(house.getPlayer()), house.getCharacterTile(zombie)) < zombie.getSmell())
      {
        return true;
      }
    }
    return false;
  }

  private void assertion(boolean expr) throws AssertionError
  {
    if (!expr) throw new AssertionError();
  }
}
