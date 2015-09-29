package view;


import common.Size;
import model.House;
import model.Tile;

import java.awt.*;
import java.awt.geom.Point2D;

public class Converter
{
  private static final double SCALING_FACTOR = 1.5;
  private House house;

  public Converter (House house)
  {
    this.house = house;
  }

  /**
   * Convert a tile to a real graphical point on the screen
   *
   * @param tile object that is in question
   * @return Point on the screen
   */
  public Point tileToPoint (Tile tile)
  {
    return new Point(tile.getCol(), tile.getRow());
  }

  public Tile pointToHouseTile (Point2D p)
  {
    return house.getTile((int) (p.getX() / SCALING_FACTOR),
                         (int) (-p.getY() / SCALING_FACTOR));
  }


  public Point charCenter (float x, float y)
  {
    return new Point(
            (int) (((x * Size.TILE) + (x * Size.TILE)) + ((x * Size.TILE) - (x * Size.TILE))) / 2,
            (int) (((y * Size.TILE) + (y * Size.TILE)) + ((y * Size.TILE) - (y * Size.TILE))) / 2);
  }


  //  public int tileXToX(int x)
  //  {

  //    float x = house.getPlayer().getCurrentX();
  //    float y = house.getPlayer().getCurrentY();
  //    graphics.translate((int) ((x * Renderer.TILE-Sprite.SIZE) + x),
  //                       (int) ((y * Renderer.TILE-Sprite.SIZE) + y));
  //  }
  //
  //  public int tileYtoY(int y)
  //  {
  //    return
  //  }


}
