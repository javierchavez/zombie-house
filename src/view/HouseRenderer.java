package view;

import model.*;

import java.awt.*;
import java.awt.image.BufferedImage;


public class HouseRenderer extends Renderer
{
  private final House house;
  FloorGraphic floor = new FloorGraphic();
  WallGraphic wall = new WallGraphic();
  TrapGraphic trap = new TrapGraphic();
  ExitGraphic exit = new ExitGraphic();


  private BufferedImage[] burn = {
          FireSprite.getSprite(4, 0),
          FireSprite.getSprite(0, 1),
          FireSprite.getSprite(1, 1),
          FireSprite.getSprite(2, 1),
          FireSprite.getSprite(3, 1),
          FireSprite.getSprite(4, 1),
          FireSprite.getSprite(0, 2),
          FireSprite.getSprite(1, 2)};


  private Animation burning = new Animation(burn, 25);

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
        if (houseMatrix[i][j].getTrap() == Trap.BURNED)
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
          // g2.drawImage(floor.getImage(), j * tileW, i * tileH, null);
          g2.drawRect(j * tileW, i * tileH, 80, 80);
        }
        else if (houseMatrix[i][j] instanceof Exit)
        {
          // g2.drawImage(floor.getImage(), j * tileW, i * tileH, null);
          g2.drawImage(exit.getImage(), j * tileW, i * tileH, null);
        }

        // Rendering the animation on top of the tile
        if (houseMatrix[i][j].getTrap() == Trap.ACTIVATED)
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
  }
}
