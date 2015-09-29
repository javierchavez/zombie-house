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
 * Defines a tile that is blank (no floor, wall, etc...)
 */

import java.util.Random;

/**
 * Empty tile
 */
public class Empty extends Tile
{
  public static int minCost = 15;
  public static int maxCost = 30;

  public Empty (int x, int y, int cost)
  {
    super(x, y, cost);
  }

  public Empty (int x, int y)
  {
    super(x, y, 0);
    setCost(randomCost());
    setPassable(false);
  }

  /**
   * Sets the minimum cost of the tile when using a random cost
   *
   * @param minCost minimum allowable cost
   */
  public void setMinCost (int minCost)
  {
    Empty.minCost = minCost;
  }

  /**
   * Sets the maximum cost of the tile when using a random cost
   *
   * @param maxCost maximum allowable cost (exclusive)
   */
  public void setMaxCost (int maxCost)
  {
    Empty.maxCost = maxCost;
  }

  /**
   * Creates a random cost for the blank tile for use with house generation
   *
   * @return cost
   */
  private int randomCost ()
  {
    // gives a random cost between 5 and 15
    // these values can be changed to generate different
    // looking houses
    Random rand = new Random();
    return minCost + rand.nextInt(maxCost - minCost);
  }

  @Override
  public boolean isCombustible ()
  {
    return false;
  }
}
