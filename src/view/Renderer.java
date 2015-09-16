package view;

import model.Tile;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public abstract class Renderer
{
  int TILE_WIDTH = 80;
  int TILE_HEIGHT = 80;

  int MAX_SCREEN_WIDTH = 1920;
  int MAX_SCREEN_HEIGHT = 1080;

  // scale factor
  private static final double ASPECT_RATIO = 1.78;
  private static final double BASE_W = 320;

  private static final double BASE_H = BASE_W / ASPECT_RATIO;


  private Rectangle2D viewBounds;
  private Rectangle2D limitingRect;
  private double height;
  private double scale;

  public abstract void render(Graphics g);


  public Renderer(float x, float y, Converter converter)
  {
  }

  public Renderer()
  {

  }
}
