package view;

import model.Floor;
import model.House;
import model.Tile;
import model.Wall;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;


public class HouseRenderer extends Renderer
{
  private final House house;
  FloorGraphic floor = new FloorGraphic();
  WallGraphic wall = new WallGraphic();

  public HouseRenderer (House house, Converter converter)
  {
    super(house.getPlayerTile().getX(),house.getPlayerTile().getY(), converter);
    this.house = house;
  }

  @Override
  public void render (java.awt.Graphics g)
  {
    Graphics2D g2 = (Graphics2D)g;

    ////////// this is shit code.///////
    // this needs to be abstracted out into a view or camera class
    // it needs to take into account for scale and size of clipping
    double width = g2.getClipBounds().getWidth();
    double height = g2.getClipBounds().getHeight();

    int tileW = (int) (width / house.getWidth());
    int tileH = (int) (height / house.getHeight());


    // int cellsX = (int) Math.ceil(g2.getClipBounds().getWidth()/80f);
    // int cellsY = (int) Math.ceil(g2.getClipBounds().getHeight()/80f);
    ////////// end shit code ///////////

    Tile[][] houseMatrix = house.getHouse();

    for (int i = 0; i < houseMatrix.length; i++)
    {
      for (int j = 0; j < houseMatrix[0].length; j++)
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
        }
      }
    }
  }
}
