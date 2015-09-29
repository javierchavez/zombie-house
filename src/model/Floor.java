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

/**
 * Floor Tile
 */
public class Floor extends Tile
{

  /**
   * Create a new floor tile.
   *
   * @param x    column
   * @param y    row
   * @param cost amount it takes to pass this tile, for A*
   */
  public Floor (int x, int y, int cost)
  {
    super(x, y, cost);
    setPassable(true);
  }

  public Floor (int x, int y)
  {
    this(x, y, 10);
  }


  @Override
  public boolean isCombustible ()
  {
    return true;
  }
}
