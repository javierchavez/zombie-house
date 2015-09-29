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
 * A well defined room in the house
 */
public class Room extends Area
{
  /**
   * Creates a Room with upper left corner at (row, col)
   * and with a size of (width, height)
   *
   * @param row Row of the top edge of the room
   * @param col Column of the left edge of the room
   * @param width How many columns the room spans
   * @param height How many rows the room spans
   */
  public Room(int row, int col, int width, int height)
  {
    super(col, row, width, height);
  }


  /**
   * Gets the row of the top edge of the room
   *
   * @return row
   */
  public int row()
  {
    return (int) getY();
  }

  /**
   * Gets the col of the left edge of the room
   *
   * @return col
   */
  public int col()
  {
    return (int) getX();
  }

  /**
   * Gets how many columns the room spans
   *
   * @return width
   */
  public int width()
  {
    return (int) getWidth();
  }

  /**
   * Gets how many rows the room spans
   *
   * @return height
   */
  public int height()
  {
    return (int) getHeight();
  }

  /**
   * Adds a room to the House house play placing floor tiles
   * in its defined area
   *
   * @param house House to place the room in
   */
  public void addRoom(House house)
  {
    for (int row = row(); row <= row()+height(); row++)
    {
      for (int col = col(); col <= col()+width(); col++)
      {
        house.setTile(row, col, new Floor(col, row));
      }
    }
  }
}
