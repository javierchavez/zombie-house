package model;


/**
 * Wrapper class for holding a move. which is just a Point.float without
 * extra super classes
 *
 */
public class Move
{

  public float row, col;
  public float direction = 0.0f;

  public Move (float col, float row, float direction)
  {
    this.row = row;
    this.col = col;
    this.direction = direction;
  }
}
