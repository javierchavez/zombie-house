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
 * The exit to the house
 */

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
