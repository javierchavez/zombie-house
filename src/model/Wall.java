package model;


public class Wall extends Tile
{
  private WallType type;
  /**
   * set the x, y coordinates of wall.
   * The cost is already set to something absurd so A* never chooses it
   * 
   * @param x column
   * @param y row
   */
  public Wall (int x, int y)
  {
    super(x, y, 999999999);
    type = WallType.INTERIOR;
    setPassable(false);
  }

  public WallType getWallType()
  {
    return type;
  }

  public void setWallType(WallType type)
  {
    this.type = type;
  }

  @Override
  public boolean isCombustible ()
  {
    return type == WallType.INTERIOR;
  }
}
