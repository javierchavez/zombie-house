package model;


import java.awt.*;
import java.util.Random;

public class RandomFindStrategy extends AbstractFindStrategy implements FindStrategy<Tile>
{
  @Override
  public void find (House board, Tile start, Tile end)
  {

  }

  @Override
  public Point getNext ()
  {
    Random rand = new Random();
    return new Point(rand.nextInt(3) - 1, rand.nextInt(3) - 1);
  }
}
