package model;

/**
 * @author Javier Chavez
 * @author Alex Baker
 * @author Erin Sosebee
 *         <p>
 *         Date September 28, 2015
 *         CS 351
 *         Zombie House
 *         <p>
 *         This is the interface for Combustible objects
 */

public class Obstacle extends Tile
{

  /**
   * New obstacle, cost is already set to something absurd so A* never
   * chooses it
   *
   * @param x column
   * @param y row
   */
  public Obstacle (int x, int y)
  {
    super(x, y, 999999999);
    setPassable(false);
  }

  @Override
  public boolean isCombustible ()
  {
    return true;
  }
}
