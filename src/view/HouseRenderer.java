package view;

import model.*;
import model.Combustible.CombustedState;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import common.Size;


public class HouseRenderer extends Renderer
{
  private final House house;
  FloorGraphic floor = new FloorGraphic();
  WallGraphic wall = new WallGraphic();
  TrapGraphic trap = new TrapGraphic();
  ExitGraphic exit = new ExitGraphic();
  ObstacleGraphic obstacle = new ObstacleGraphic();

  private BufferedImage[] burn = {
          FireSprite.getSprite(4, 0),
          FireSprite.getSprite(0, 1),
          FireSprite.getSprite(1, 1),
          FireSprite.getSprite(2, 1),
          FireSprite.getSprite(3, 1),
          FireSprite.getSprite(4, 1),
          FireSprite.getSprite(0, 2),
          FireSprite.getSprite(1, 2)};


  private Animation burning = new Animation(burn, 2);

  // check direction... need a AnimationFactoryClass
  private Animation animation;

  public HouseRenderer (House house, Converter converter)
  {
    super(house.getCharacterTile(house.getPlayer()).getCol(),
          house.getCharacterTile(house.getPlayer()).getRow(), converter);
    this.house = house;
    animation = burning;
    animation.start();
    // center the renderer on the player tile
  }

  private Rectangle2D.Float project (Ellipse2D start, Point.Float end,
                                     float scalar)
  {
    float dx = (float) (end.x - start.getX());
    float dy = (float) (end.y - start.getY());
    //euclidean length
    float len = (float) Math.sqrt(dx * dx + dy * dy);
    //normalize to unit vector
    if (len != 0)
    { //avoid division by 0
      dx /= len;
      dy /= len;
    }
    //multiply by scalar amount
    dx *= scalar;
    dy *= scalar;
    return new Rectangle2D.Float(end.x + dx, end.y + dy,
                                 (float) start.getWidth(),
                                 (float) start.getHeight());
  }


  @Override
  public void render (Graphics2D g2)
  {

    Tile[][] houseMatrix = house.getHouse();
    animation.update();

    Player player = house.getPlayer();

    for (int i = 0; i < houseMatrix.length; i++)
    {
      for (int j = 0; j < houseMatrix[i].length; j++)
      {
        if (!player.senseSight(house.getTile(i, j)))
        {
          continue;
        }
        // Draw only burned tile then move on (don't draw anything else)
        if (houseMatrix[i][j].getCombustedState() == CombustedState.BURNED)
        {
          g2.drawImage(trap.getImageBurned(), (j * TILE), (i * TILE), null);
          continue;
        }

        if (houseMatrix[i][j] instanceof Wall)
        {
          g2.drawImage(wall.getImage(), j * TILE, i * TILE, null);
        }
        else if (houseMatrix[i][j] instanceof Floor)
        {
          g2.drawImage(floor.getImage(), j * TILE, i * TILE, null);
          if (houseMatrix[i][j].getTrap() == Trap.FIRE)
          {
            g2.drawImage(trap.getImage(), (j * TILE) + 20, (i * TILE) + 20,
                         null);
          }
        }
        else if (houseMatrix[i][j] instanceof Obstacle)
        {
          g2.drawImage(obstacle.getImage(), j * TILE, i * TILE, null);
        }
        else if (houseMatrix[i][j] instanceof Exit)
        {
          g2.drawImage(exit.getImage(), j * TILE, i * TILE, null);
        }

        // Rendering the animation on top of the tile
        if (houseMatrix[i][j].getCombustedState() == CombustedState.IGNITED)
        {
          g2.drawImage(animation.getSprite(), (j * TILE_HEIGHT),
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
  }
}
