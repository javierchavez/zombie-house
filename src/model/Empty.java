package model;


import java.util.Random;

/**
 *
 */
public class Empty extends Tile
{
  int minCost = 15;
  int maxCost = 30;

  public Empty(int x, int y, int cost)
  {
    super(x, y, cost);
  }

  public Empty(int x, int y)
  {
    super(x, y, 0);
    setCost(randomCost());
  }

  /**
   * Sets the minimum cost of the tile when using a random cost
   * @param minCost minumum allowable cost
   */
  public void setMinCost(int minCost)
  {
    this.minCost = minCost;
  }

  /**
   * Sets the maximum cost of the tile when using a random cost
   * @param maxCost maximum allowable cost (exclusive)
   */
  public void setMaxCost(int maxCost)
  {
    this.maxCost = maxCost;
  }

  /**
   * Creates a random cost for the blank tile for use with house generation
   * @return cost
   */
  private int randomCost()
  {
    // gives a random cost between 5 and 15
    // these values can be changed to generate different
    // looking houses
    Random rand = new Random();
    return minCost + rand.nextInt(maxCost-minCost);
  }

  @Override
  public boolean isCombustible ()
  {
    return false;
  }
}
