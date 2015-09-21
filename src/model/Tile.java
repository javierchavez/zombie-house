package model;


import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Tile implements Object2D
{
  // row and col are the tiles location in the house array
  private int row = 0;
  private int col = 0;
  private float width = 1f;
  private float height = 1f;

  // The cost to travel over a tile (for pathfinding algorithms)
  private int cost;
  private Trap trap = Trap.NONE;

  /**
   * Create a tile with cost 1
   *
   * @param col column
   * @param row row
   */
  public Tile(int col, int row)
  {
    this(col, row, 1);
  }

  /**
   * Create a Tile
   *
   * @param col column
   * @param row row
   * @param cost amount it take to pass this tile.
   */
  public Tile(int col, int row, int cost)
  {
    this.col = col;
    this.row = row;
    this.cost = cost;
  }

  /**
   * Get the column position of the tile
   *
   * @return column
   */
  public int getCol ()
  {
    return col;
  }

  /**
   * Get the row position of the Tile
   *
   * @return row
   */
  public int getRow ()
  {
    return row;
  }

  /**
   * Get the cost of the Tile
   *
   * @return cost
   */
  public int getCost()
  {
    return cost;
  }

  /**
   * Set the travel cost of the tile.
   *
   * @param cost cost to travel over the tile
   */
  public void setCost(int cost)
  {
    this.cost = cost;
  }

  @Override
  public boolean equals (Object o)
  {
    if (this == o) return true;
    if (!(o instanceof Tile)) return false;

    Tile tile = (Tile) o;

    if (row != tile.row) return false;
    return col == tile.col;

  }

  @Override
  public int hashCode ()
  {
    int result = row;
    result = 31 * result + col;
    return result;
  }

  /**
   * Get the trap on this square
   *
   * @return trap
   */
  public Trap getTrap()
  {
    return trap;
  }

  /**
   * Set the trap on this tile
   *
   * @param trap can either be NONE or FIRE
   */
  public Tile setTrap (Trap trap)
  {
    this.trap = trap;
    return this;
  }

  /**
   * Get the trap and remove it from current tile
   *
   * @return trap
   */
  public Trap popTrap()
  {
    Trap t = trap;
    trap = Trap.NONE;
    return t;
  }


  @Override
  public void setWidth (float width)
  {
    this.width = width;
  }

  @Override
  public void setHeight (float height)
  {
    this.height = height;
  }

  @Override
  public float getX ()
  {
    return col;
  }

  @Override
  public float getY ()
  {
    return row;
  }

  @Override
  public float getWidth ()
  {
    return width;
  }

  @Override
  public float getHeight ()
  {
    return height;
  }

  @Override
  public Rectangle2D getBoundingRectangle ()
  {
    return new Rectangle2D.Float(getCol(), getRow(), getWidth(),getHeight());
  }

  @Override
  public boolean intersects (Rectangle2D other)
  {
    return other.intersects(this.getBoundingRectangle());
  }

  @Override
  public boolean isOutOfBounds ()
  {
    return false;
  }
}
