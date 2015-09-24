package model;


public class Obstacle extends Tile
{

  /**
   * New obstacle, cost is already set to something absurd so A* never
   * chooses it
   *
   * @param x column
   * @param y row
   */
  Obstacle (int x, int y)
  {
    super(x, y, 999999999);
  }

  @Override
  public boolean isCombustible ()
  {
    return true;
  }
}
