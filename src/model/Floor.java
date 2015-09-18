package model;


public class Floor extends Tile
{

  /**
   * Create a new floor tile.
   * @param x column
   * @param y row
   * @param cost amount it takes to pass this tile, for A*
   */
  public Floor (int x, int y, int cost)
  {
    super(x, y, cost);
  }
}
