package view;

import model.*;
import model.Character;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.*;


public class HouseRenderer extends Renderer
{
  private final House house;
  FloorGraphic floor = new FloorGraphic();
  WallGraphic wall = new WallGraphic();
  TrapGraphic trap = new TrapGraphic();
  ExitGraphic exit = new ExitGraphic();
  ObstacleGraphic obstacle = new ObstacleGraphic();
  protected final static Color[] GRADIENT_COLORS = new Color[] { new Color
          (0f,0f,0f,0f), Color.black };
  protected final static float GRADIENT_SIZE = 200f;
  protected java.util.List<Tile> entities = new ArrayList<>();
  protected final static Polygon POLYGON = new Polygon();

  protected final static float[] GRADIENT_FRACTIONS = new float[]{0f, 1f};

  private BufferedImage[] burn = {
          FireSprite.getSprite(4, 0),
          FireSprite.getSprite(0, 1),
          FireSprite.getSprite(1, 1),
          FireSprite.getSprite(2, 1),
          FireSprite.getSprite(3, 1),
          FireSprite.getSprite(4, 1),
          FireSprite.getSprite(0, 2),
          FireSprite.getSprite(1, 2)};


  private Animation burning = new Animation(burn, 20);

  // check direction... need a AnimationFactoryClass
  private Animation animation;

  public HouseRenderer (House house, Converter converter)
  {
    super(house.getCharacterTile(house.getPlayer()).getCol(),
          house.getCharacterTile(house.getPlayer()).getRow(),
          converter);
    this.house = house;
    animation = burning;
    animation.start();
    // center the renderer on the player tile
  }

  private Rectangle2D.Float project(Ellipse2D start, Point.Float
  end, float scalar) {
    float dx = (float) (end.x - start.getX());
    float dy = (float) (end.y - start.getY());
    //euclidean length
    float len = (float)Math.sqrt(dx * dx + dy * dy);
    //normalize to unit vector
    if (len != 0) { //avoid division by 0
      dx /= len;
      dy /= len;
    }
    //multiply by scalar amount
    dx *= scalar;
    dy *= scalar;
    return new Rectangle2D.Float(end.x + dx, end.y + dy,
                                 (float)start.getWidth(),
                                 (float) start.getHeight());
  }


  protected void renderShadows(Graphics2D g) {
    //old Paint object for resetting it later

    //minimum distance (squared) which will save us some checks
    float minDistSq = GRADIENT_SIZE*GRADIENT_SIZE;
    //amount to extrude our shadow polygon by
    //use a large enough value to ensure that it is way off screen
    final float SHADOW_EXTRUDE = GRADIENT_SIZE*GRADIENT_SIZE;

    //we'll use a radial gradient from the mouse center
    final Paint GRADIENT_PAINT = new RadialGradientPaint(new Point2D.Float
                                                                 (house
                                                                          .getPlayer().getCurrentX()*80, house.getPlayer().getCurrentY()*80),
                                                         house.getPlayer()
                                                                 .getSight()*80,
                                                         GRADIENT_FRACTIONS, GRADIENT_COLORS);

    Character pl = house.getPlayer();

    g.setPaint(GRADIENT_PAINT);
    g.fillRect(0,0,
               (int)house.getWidth()*80,
               (int)house.getHeight()*80);
  }
  @Override
  public void render (Graphics2D g2)
  {



    int tileW = 80;
    int tileH = 80;


    Tile[][] houseMatrix = house.getHouse();


    for (int i = 0; i < houseMatrix.length; i++)
    {
      for (int j = 0; j < houseMatrix[i].length; j++)
      {
        // Draw only burned tile then move on (don't draw anything else)
        if (houseMatrix[i][j].getCombustedState() == Combustible
                .CombustedState.BURNED)
        {
          g2.drawImage(trap.getImageBurned(), (j * tileW), (i * tileH), null);
          continue;
        }

        if (houseMatrix[i][j] instanceof Wall)
        {
          g2.drawImage(wall.getImage(), j * tileW, i * tileH, null);
        }
        else if (houseMatrix[i][j] instanceof Floor)
        {
          g2.drawImage(floor.getImage(), j * tileW, i * tileH, null);
          if (houseMatrix[i][j].getTrap() == Trap.FIRE)
          {
            g2.drawImage(trap.getImage(),
                         (j * tileW) + 20,
                         (i * tileH) + 20, null);

          }
        }
        else if (houseMatrix[i][j] instanceof Obstacle)
        {
           g2.drawImage(obstacle.getImage(), j * tileW, i * tileH, null);
//          g2.drawRect(j * tileW, i * tileH, 80, 80);
        }
        else if (houseMatrix[i][j] instanceof Exit)
        {
          // g2.drawImage(floor.getImage(), j * tileW, i * tileH, null);
          g2.drawImage(exit.getImage(), j * tileW, i * tileH, null);
        }

        // Rendering the animation on top of the tile
        if (houseMatrix[i][j].getCombustedState() == Combustible
                .CombustedState.IGNITED)
        {
          animation.update();
          g2.drawImage(animation.getSprite(),
                       (j * TILE_HEIGHT),
                       (i * TILE_HEIGHT), null);

        }

        //        g2.setColor(Color.green);
        //
        //        if (j == houseMatrix[i].length - 1 ||
        //                i == houseMatrix.length - 1 ||
        //                j == 0 || i == 0)
        //        {
        //          g2.setColor(Color.red);
        //        }
        //        g2.drawRect(j * tileW, i * tileH, 80, 80);
      }
    }
//    renderShadows(g2);
//    for (int i=0; i<entities.size(); i++) {
//      Rectangle2D e = entities.get(i).getBoundingRectangle();
//      g2.setColor(Color.WHITE);
//      g2.fill(e);
//    }
  }
}
