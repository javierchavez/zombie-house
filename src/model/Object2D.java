package model;


import java.awt.geom.Rectangle2D;

public interface Object2D
{
  /** @return x coordinate of upper left corner of object. */
  float getX();

  /** @return y coordinate of upper left corner of object. */
  float getY();

  /** @return object width. */
  float getWidth();

  /** @return object height. */
  float getHeight();

  void setWidth(float width);


  void setHeight(float width);

  /**
   * Get the bounding rectangle for the object.
   * @return Bounding rectangle.
   */
  Rectangle2D getBoundingRectangle();

  /**
   * Does this object intersect another? (Checking if the bounding
   * rectangles intersect will generally suffice.)
   * @param other The other object to check.
   * @return True if objects intersect.
   */
  boolean intersects(Rectangle2D other);

  /**
   * Is any part of the object outside of the game area?
   * @return True if part of object is out of bounds.
   */
  boolean isOutOfBounds();
}
