package model;


/**
 * Exit Tile
 */
public class Exit extends Tile
{
  Exit (int x, int y)
  {
    super(x, y);
  }

  @Override
  public boolean isCombustible ()
  {
    return false;
  }
}
