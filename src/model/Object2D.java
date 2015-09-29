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

public interface Object2D
{
  /**
   * Get the object x/column position
   *
   * @return x coordinate of upper left corner of object
   */
  float getX();

  /**
   * Get the object y/row position
   *
   * @return y coordinate of upper left corner of object
   */
  float getY();

  /**
   * Get the object width
   *
   * @return width of the object
   */
  float getWidth();

  /**
   * Set the objects width
   *
   * @param width
   */
  void setWidth(float width);

  /**
   * Get the object height
   *
   * @return height of the object
   */
  float getHeight ();

  /**
   * Set the object height
   *
   * @param height
   */
  void setHeight(float height);

  /**
   * Get the bounding rectangle for the object.
   *
   * @return Bounding rectangle.
   */
  Rectangle2D getBoundingRectangle();

  /**
   * Check if one object intersects another
   *
   * @param other The other object to check.
   * @return True if objects intersect.
   */
  boolean intersects(Rectangle2D other);

  /**
   * Is the object outside of the game area
   *
   * @return True if part of object is out of bounds.
   */
  boolean isOutOfBounds();
}
