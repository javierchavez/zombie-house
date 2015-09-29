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
 * This is the interface for Combustible objects
 */

import java.awt.geom.Rectangle2D;

/**
 *  Wrapper class for a general 2D object
 */
public class Area implements Object2D
{
  private final float x;
  private final float y;
  private float width;
  private float height;

  public Area(float x, float y, float width, float height)
  {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  public Area()
  {
    this(0,0,0,0);
  }

  @Override
  public float getX()
  {
    return x;
  }

  @Override
  public float getY()
  {
    return y;
  }

  @Override
  public float getWidth()
  {
    return width;
  }

  @Override
  public void setWidth (float width)
  {
    this.width = width;
  }

  @Override
  public float getHeight ()
  {
    return height;
  }

  @Override
  public void setHeight(float height)
  {
    this.height = height;
  }

  @Override
  public Rectangle2D getBoundingRectangle()
  {
    return new Rectangle2D.Float(x, y, width, height);
  }

  @Override
  public boolean intersects(Rectangle2D other)
  {
    return other.intersects(this.getBoundingRectangle());
  }

  @Override
  public boolean isOutOfBounds()
  {
    return false;
  }
}
