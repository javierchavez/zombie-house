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
 * The strategy line walk zombies use
 */

import java.util.Random;

/**
 * Strategy for a character to move in a line
 */
public class LineMoveStrategy extends AbstractFindStrategy implements FindStrategy<Tile>
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
    int xDir = 0;
    int yDir = 0;

    boolean changeDir = rand.nextBoolean();
    if (changeDir)
    {
      xDir = rand.nextInt(3) - 1;
    }
    else
    {
      yDir = rand.nextInt(3) - 1;
    }
    return new Move(xDir, yDir, 0);
  }
}
