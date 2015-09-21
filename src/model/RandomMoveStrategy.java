package model;


import java.util.Random;

public class RandomMoveStrategy extends AbstractFindStrategy implements FindStrategy<Tile>
{
  Random rand = new Random();
  protected AStarFindStrategy defaultFinder = new AStarFindStrategy();

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
