package view;


import model.House;
import model.Tile;

import java.awt.*;
import java.awt.geom.Point2D;

public class Converter
{
  private static final double SCALING_FACTOR = 1.5;
  private House house;

  public Converter(House house)
  {
    this.house = house;
  }

  /**
   * Convert a tile to a real graphical point on the screen
   * @param tile object that is in question
   * @return Point on the screen
   */
  public Point tileToPoint(Tile tile)
  {
    return new Point(tile.getX(), tile.getY());
  }

  public Tile pointToHouseTile(Point2D p)
  {
    return house.getTile((int) (p.getX() / SCALING_FACTOR),
                         (int) (-p.getY() / SCALING_FACTOR));
  }

//  public int tileXToX(int x)
//  {
//    return
//  }
//
//  public int tileYtoY(int y)
//  {
//    return
//  }


}
