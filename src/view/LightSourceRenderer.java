package view;

import model.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;


public class LightSourceRenderer extends Renderer
{
  private final House house;
  protected final static float GRADIENT_SIZE = 200f;
  protected java.util.List<Tile> entities = new ArrayList<>();
  protected final static Polygon POLYGON = new Polygon();

  protected final static Color[] GRADIENT_COLORS = new Color[] {
          new Color(0f, 0f, 0f, 0f),
          Color.black};

  protected final static float[] GRADIENT_FRACTIONS = new float[] {
          0f,
          1f};

  public LightSourceRenderer (House house)
  {
    this.house = house;
  }

  private void renderPlayerLight (Graphics2D g)
  {

    model.Character pl = house.getPlayer();
    final Paint GRADIENT_PAINT = new RadialGradientPaint(
            new Point2D.Float(pl.getCurrentX() * 80, pl.getCurrentY() * 80),
            pl.getSight() * TILE, GRADIENT_FRACTIONS, GRADIENT_COLORS);

    g.setPaint(GRADIENT_PAINT);
  }

  public void renderLightSource (Graphics2D g, int x, int y)
  {

    final Paint GRADIENT_PAINT = new RadialGradientPaint(
            new Point2D.Float(x * 80, y * 80), 200f, GRADIENT_FRACTIONS,
            GRADIENT_COLORS);

    g.setPaint(GRADIENT_PAINT);
  }


  @Override
  public void render (Graphics2D g)
  {
    renderPlayerLight(g);
    //    renderBurningTile(g);
    g.fillRect(0, 0, (int) house.getWidth() * 80 + 2,
               (int) house.getHeight() * 80 + 2);
  }
}
