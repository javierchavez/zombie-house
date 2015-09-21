package model;


import java.util.Random;

public class LineMoveStrategy extends AbstractFindStrategy implements FindStrategy<Tile>
{
  Random rand = new Random();
  protected BFSFindStrategy defaultFinder = new BFSFindStrategy();

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

    int changeDir = rand.nextInt(1);
    if (changeDir == 0) xDir = rand.nextInt(3) - 1;
    else yDir = rand.nextInt(3) - 1;
    return new Move(xDir, yDir, 0);
  }


}
