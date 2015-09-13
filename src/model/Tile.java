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
   *
   * @param x column
   * @param y row
   */
  Tile(int x, int y)
  {
    this(x, y, 1);
  }

  /**
   * Create a Tile
   *
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
   *
   * @return column
   */
  int getX()
  {
    return col;
  }

  /**
   * Get the row position of the Tile
   *
   * @return row
   */
  int getY()
  {
    return row;
  }

  /**
   * Get the cost of the Tile
   *
   * @return cost
   */
  int getCost()
  {
    return cost;
  }

  /**
   * Set the travel cost of the tile.
   *
   * @param cost cost to travel over the tile
   */
  void setCost(int cost)
  {
    this.cost = cost;
  }

  @Override
  public boolean equals (Object o)
  {
    if (this == o) return true;
    if (!(o instanceof Tile)) return false;

    Tile tile = (Tile) o;

    if (row != tile.row) return false;
    return col == tile.col;

  }

  @Override
  public int hashCode ()
  {
    int result = row;
    result = 31 * result + col;
    return result;
  }

  /**
   * Types of Traps that a tile can house
   */
  enum Trap {
    NONE, FIRE
  }

  /**
   * Get the trap on this square
   *
   * @return trap
   */
  Trap getTrap()
  {
    return trap;
  }

  /**
   * Set the trap on this tile
   *
   * @param trap can either be NONE or FIRE
   */
  Tile setTrap (Trap trap)
  {
    this.trap = trap;
    return this;
  }

  /**
   * Get the trap and remove it from current tile
   *
   * @return trap
   */
  Trap popTrap()
  {
    Trap t = trap;
    trap = Trap.NONE;
    return t;
  }


}
