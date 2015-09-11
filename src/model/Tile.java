package model;


public abstract class Tile
{
  // row and col are the tiles location in the house array
  int row = 0;
  int col = 0;

  // The cost to travel over a tile (for pathfinding algorithms)
  int cost;
  private Trap trap = Trap.NONE;

  /**
   * Get the column position of the tile
   * @return column
   */
  float getX()
  {
    return (float) col;
  }

  /**
   * Get the row position of the tile
   * @return row
   */
  float getY()
  {
    return (float) row;
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
