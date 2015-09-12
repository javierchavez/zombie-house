package model;


public abstract class Tile
{
  // row and col are the tiles location in the house array
  private int row = 0;
  private int col = 0;

  // The cost to travel over a tile (for pathfinding algorithms)
  private int cost;
  private Trap trap = Trap.NONE;

  /**
   * Create a tile with cost 1
   * @param x column
   * @param y row
   */
  Tile(int x, int y)
  {
    this(x, y, 1);
  }

  /**
   * Create a a tile
   * @param x column
   * @param y row
   * @param cost amount it take to pass this tile.
   */
  Tile(int x, int y, int cost)
  {
    this.col = x;
    this.row = y;
    this.cost = cost;
  }

  /**
   * Get the column position of the tile
   * @return column
   */
  int getX()
  {
    return col;
  }

  /**
   * Get the row position of the tile
   * @return row
   */
  int getY()
  {
    return row;
  }

  /**
   * Get the cost of the tile
   * @return cost
   */
  int getCost()
  {
    return cost;
  }

  /**
   * Types of Traps.
   */
  enum Trap {
    NONE, FIRE
  }

  /**
   * Get the trap on this square
   * @return trap
   */
  Trap getTrap()
  {
    return trap;
  }

  /**
   * Set the trap on this tile
   * @param trap can either be NONE or FIRE
   */
  Tile setTrap (Trap trap)
  {
    this.trap = trap;
    return this;
  }

  /**
   * Get the trap and remove it from current tile
   * @return trap
   */
  Trap popTrap()
  {
    Trap t = trap;
    trap = Trap.NONE;
    return t;
  }


}
