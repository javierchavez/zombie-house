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

import java.util.Random;

/**
 * Strategy for a character to move in a randomly
 */
public class RandomMoveStrategy extends AbstractFindStrategy implements FindStrategy<Tile>
{
  protected final AStarFindStrategy defaultFinder = new AStarFindStrategy();
  final Random rand = new Random();

  @Override
  public void find (House board, Tile start, Tile end)
  {
    defaultFinder.find(board, start, end);
    path = defaultFinder.getPath();
  }

  @Override
  public Move getNextMove (House house, Tile start)
  {
    return new Move(rand.nextInt(3) - 1, rand.nextInt(3) - 1, 0);
  }
}
