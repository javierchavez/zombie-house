package model;


import java.awt.geom.Rectangle2D;

/**
 *
 */
public class Room extends Area
{
  public Room(int row, int col, int width, int height)
  {
    super(col, row, width, height);
  }

  public int row()
  {
    return (int) getY();
  }

  public int col()
  {
    return (int) getX();
  }

  public int width()
  {
    return (int) getWidth();
  }

  public int height()
  {
    return (int) getHeight();
  }

  public void addRoom(House house)
  {
    for (int row = row(); row <= height(); row++)
    {
      for (int col = col(); col <= width(); col++)
      {
        house.setTile(row, col, new Floor(col, row));
      }
    }
  }

  public boolean intersects(Room room)
  {
    Rectangle2D.Float thisRoom = new Rectangle2D.Float(x-1,
                                                       y-1,
                                                       width+1,
                                                       height+1);

    Rectangle2D.Float thatRoom = new Rectangle2D.Float(room.getX()-1,
                                                       room.getY()-1,
                                                       room.getWidth()+1,
                                                       room.getHeight()+1);

    return thisRoom.intersects(thatRoom);
  }
}
