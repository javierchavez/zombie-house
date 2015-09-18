package view;

import model.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;


public class HouseRenderer extends Renderer
{
  private final House house;
  FloorGraphic floor = new FloorGraphic();
  WallGraphic wall = new WallGraphic();
  TrapGraphic trap = new TrapGraphic();

  public HouseRenderer (House house, Converter converter)
  {
    super(house.getPlayerTile().getX(),
          house.getPlayerTile().getY(),
          converter);

    this.house = house;
    // center the renderer on the player tile
  }

  @Override
  public void render (Graphics g)
  {
    Graphics2D g2 = (Graphics2D)g;

    ////////// this is shit code.///////
    // this needs to be abstracted out into a view or camera class
    // it needs to take into account for scale and size of clipping
//    double width = viewBounds.getWidth();
//    double height = viewBounds.getHeight();
//
//    float x = character.getCurrentX();
//    float y = character.getCurrentY();
//
//    float camX = house.getpl- VIEWPORT_SIZE_X / 2;
//    float camY = playerY - VIEWPORT_SIZE_Y / 2;
//    g2.translate(-camX, -camY);
//
//    int cellsX = (int) Math.ceil(width/80f);
//    int cellsY = (int) Math.ceil(height/80f);
//    int tileW = (int) (width / cellsX);
//    int tileH = (int) (height / cellsY);
    int tileW = 80;
    int tileH = 80;
//    System.out.println(viewBounds.getY());
    ////////// end shit code ///////////

    Tile[][] houseMatrix = house.getHouse();

    for (int i = 0; i < houseMatrix.length; i++)
    {
      for (int j = 0; j < houseMatrix[i].length; j++)
      {
        if (houseMatrix[i][j] instanceof Wall)
        {
          g2.drawImage(wall.getImage(), j * tileW,
                       i * tileH, null);
        }
        else if (houseMatrix[i][j] instanceof Floor)
        {
          g2.drawImage(floor.getImage(), j * tileW,
                       i * tileH, null);
          if(houseMatrix[i][j].getTrap() == Trap.FIRE)
          {
            g2.drawImage(trap.getImage(), (j * tileW)+20,
                         (i * tileH)+20, null);

          }
        }
//        if (houseMatrix[i][j] instanceof Empty)
//        {
//          g2.fillRect(j * tileW,
//                        i * tileH, 80,80);
//        }
      }
    }
  }
}
