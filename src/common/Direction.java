package common;


public final class Direction
{
  private Direction() { throw new UnsupportedOperationException(); }

  public static final float NORTH = 90f;
  public static final float SOUTH = 270f;
  public static final float EAST = 0f;
  public static final float WEST = 180f;
  public static final float NORTHEAST = 45f;
  public static final float NORTHWEST = 135f;
  public static final float SOUTHEAST = 315f;
  public static final float SOUTHWEST = 225f;
}
