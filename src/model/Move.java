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


/**
 * Wrapper class for holding a move. which is just a Point.float without
 * extra super classes
 */
public class Move
{

  public final float row;
  public final float col;
  public float direction = 0.0f;

  public Move (float col, float row, float direction)
  {
    this.row = row;
    this.col = col;
    this.direction = direction;
  }
}
