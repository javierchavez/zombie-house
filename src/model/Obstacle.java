package model;


public class Obstacle extends Tile
{
  // life or energy points to deduct
  private float points;

  /**
   * New obstacle, cost is already set to something absurd so A* never
   * chooses it
   * @param x column
   * @param y row
   */
  Obstacle (int x, int y)
  {
    super(x, y, 999999999);
  }

}
