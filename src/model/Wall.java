package model;


public class Wall extends Tile
{

  /**
   * set the x, y coordinates of wall.
   * The cost is already set to something absurd so A* never chooses it
   * 
   * @param x column
   * @param y row
   */
  Wall (int x, int y)
  {
    super(x, y, 999999999);
  }
}
