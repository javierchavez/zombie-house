package common;

/**
 * @author Javier Chavez
 * @author Alex Baker
 * @author Erin Sosebee
 *         <p>
 *         Date September 28, 2015
 *         CS 351
 *         Zombie House
 *         <p>
 *         This is the interface for Combustible objects
 */


public final class Direction
{
  public static final float NORTH = 90f;
  public static final float SOUTH = 270f;
  public static final float EAST = 0f;
  public static final float WEST = 180f;
  public static final float NORTHEAST = 45f;
  public static final float NORTHWEST = 135f;
  public static final float SOUTHEAST = 315f;
  public static final float SOUTHWEST = 225f;

  private Direction ()
  {
    throw new UnsupportedOperationException();
  }
}
