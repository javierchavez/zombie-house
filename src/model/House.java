package model;

import java.util.ArrayList;
import java.util.List;
//import java.util.stream.Collectors;
import model.Tile.Trap;

public class House
{

  // These should are tile sizes right?
  // Tile size should probably be defined in Tile?
  //public static final int WIDTH = 80;
  //public static final int HEIGHT = 80;

  // going to use an array
  // private List<List<Object>> board = new ArrayList<>();
  // private List<Tile> tiles = new ArrayList<>();
  private List<Zombie> zombies = new ArrayList<>();
  private Character player;

  // new stuff

  // we can change the scale factor if we want larger levels
  // while still maintaining AR (aspect ratio)
  private static float displayScaleFactor = 1.5f;

  // assume a 1920 by 1080 screen
  // assume tiles are 80 by 80
  // given the requirements, these shouldn't need to be changed for the project
  // these default setting will give a 36 by 21 board
  private static final int MAX_TILE_SIZE = 80;
  private static final int MAX_SCREEN_WIDTH = 1920;
  private static final int MAX_SCREEN_HEIGHT = 1080;

  private static int rows;
  private static int cols;

  private Tile[][] board;

  public House (Character player)
  {
    this.player = player;
    setSize();
  }


  public void setDisplayScaleFactor(float displayScaleFactor)
  {
    this.displayScaleFactor = displayScaleFactor;
  }

  /**
   * Resets the board dimensions based on the new displayScaleFactor
   *
   * NOTE: This will re-initialize the board
   */
  public void setSize ()
  {
    rows = (int) Math.ceil(MAX_SCREEN_HEIGHT / MAX_TILE_SIZE * displayScaleFactor);
    cols = (int) Math.ceil(MAX_SCREEN_WIDTH / MAX_TILE_SIZE * displayScaleFactor);
    board = new Tile[rows][cols];
  }

  //public List<Tile> getObstacles ()
  //{
  // return tiles.stream().filter(tile ->
  //    tile instanceof Obstacle
  //  ).collect(Collectors.toList());
  //}

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

  public List<Zombie> getZombies ()
  {
    return zombies;
  }

  public Character getPlayer ()
  {
    return player;
  }

  public List<Tile> neighbors (Tile current)
  {
    return null;
  }

  public void placeTrap (Tile tile, Trap trap)
  {
    tile.setTrap(trap);
  }
}
