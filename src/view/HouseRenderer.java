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
  TrapGraphic trap = new TrapGraphic();

  public HouseRenderer (House house, Converter converter)
  {
    super(house.getPlayerTile().getX(),house.getPlayerTile().getY(), converter);
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
    double width = g2.getClipBounds().getWidth();
    double height = g2.getClipBounds().getHeight();



    int cellsX = (int) Math.ceil(g2.getClipBounds().getWidth()/80f);
    int cellsY = (int) Math.ceil(g2.getClipBounds().getHeight()/80f);
    int tileW = (int) (width / cellsX);
    int tileH = (int) (height / cellsY);
    ////////// end shit code ///////////

    Tile[][] houseMatrix = house.getHouse();

    for (int i = 0; i < cellsY; i++)
    {
      for (int j = 0; j < cellsX; j++)
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
          if(houseMatrix[i][j].getTrap() == Tile.Trap.FIRE)
          {
            g2.drawImage(trap.getImage(), (j * tileW)+20,
                         (i * tileH)+20, null);

          }
        }

      }
    }
  }
}
