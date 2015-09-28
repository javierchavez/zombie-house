package view;

import common.Size;
import model.Tile;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public abstract class Renderer
{
  private int offsetMinY;
  private int offsetMinX;
  private float offsetMaxX;
  private float offsetMaxY;
  int TILE_WIDTH = Size.TILE;
  int TILE_HEIGHT = Size.TILE;
  public static int TILE = Size.TILE;
  int MAX_SCREEN_WIDTH = Size.SCREEN_WIDTH;
  int MAX_SCREEN_HEIGHT = Size.SCREEN_HEIGHT;

  // scale factor
  private static final double ASPECT_RATIO = 1.78;
  private static final double BASE_W = 320;

  private static final double BASE_H = BASE_W / ASPECT_RATIO;


  static protected Rectangle2D viewBounds = new Rectangle2D.Float(0,0,Size.SCREEN_WIDTH,
                                                                  Size.SCREEN_HEIGHT);
  private Rectangle2D limitingRect;
  private double height;
  private double scale;


  public Renderer(float x, float y, Converter converter)
  {
    offsetMaxX = MAX_SCREEN_WIDTH - x;
    offsetMaxY = MAX_SCREEN_HEIGHT - y;
    offsetMinX = 0;
    offsetMinY = 0;
  }

  protected Renderer ()
  {
  }

  //  public Renderer()
//  {
//
//  }


  public abstract void render(Graphics2D g);

  public void setViewBounds (Rectangle2D viewBounds)
  {
    this.viewBounds = viewBounds;
  }
}
