package view;

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
  int TILE_WIDTH = 80;
  int TILE_HEIGHT = 80;
  public static int TILE = 80;
  int MAX_SCREEN_WIDTH = 1920;
  int MAX_SCREEN_HEIGHT = 1080;

  // scale factor
  private static final double ASPECT_RATIO = 1.78;
  private static final double BASE_W = 320;

  private static final double BASE_H = BASE_W / ASPECT_RATIO;


  static protected Rectangle2D viewBounds = new Rectangle2D.Float(0,0,1920,
                                                                  1080);
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
