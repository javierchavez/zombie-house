package view;


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


import common.Size;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Renderer
{
  public static final int TILE = Size.TILE;
  // scale factor
  private static final double ASPECT_RATIO = 1.78;
  private static final double BASE_W = 320;
  private static final double BASE_H = BASE_W / ASPECT_RATIO;
  static protected Rectangle2D viewBounds = new Rectangle2D.Float(0, 0,
                                                                  Size.SCREEN_WIDTH,
                                                                  Size.SCREEN_HEIGHT);
  final int TILE_HEIGHT = Size.TILE;
  final int MAX_SCREEN_WIDTH = Size.SCREEN_WIDTH;
  final int MAX_SCREEN_HEIGHT = Size.SCREEN_HEIGHT;
  int TILE_WIDTH = Size.TILE;
  private Rectangle2D limitingRect;
  private double height;
  private double scale;


  public Renderer (float x, float y, Converter converter)
  {
    float offsetMaxX = MAX_SCREEN_WIDTH - x;
    float offsetMaxY = MAX_SCREEN_HEIGHT - y;
    int offsetMinX = 0;
    int offsetMinY = 0;
    // offsetX = (playerWorldX * tileWidth) - (screenWidth / 2);
    // screenX = (worldX * tileWidth) - offsetX;
  }

  protected Renderer ()
  {
  }

  //  public Renderer()
  //  {
  //
  //  }


  public abstract void render (Graphics2D g);

  public void setViewBounds (Rectangle2D viewBounds)
  {
    Renderer.viewBounds = viewBounds;
  }
}
