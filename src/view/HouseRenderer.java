package view;

import model.Floor;
import model.House;
import model.Tile;
import model.Wall;

import java.awt.*;
import java.awt.geom.Rectangle2D;


public class HouseRenderer extends Renderer
{
  private final House house;
  GameGraphic[] tileGraphics = new GameGraphic[2];
  private Rectangle2D viewBounds;
  private Rectangle2D viewBoundsCamera;
  int TILE_WIDTH = 80;
  int TILE_HEIGHT = 80;
  float displayScaleFactor = 1.5f;
  int MAX_TILE_SIZE = 80;
  int MAX_SCREEN_WIDTH = 1920;
  int MAX_SCREEN_HEIGHT = 1080;

  public HouseRenderer (House house)
  {
    super(house.getPlayerTile().getX(),house.getPlayerTile().getY());

    tileGraphics[0] = GameGraphic.FLOOR;
    tileGraphics[1] = GameGraphic.WALL;
    this.house = house;
  }

  @Override
  public void render (java.awt.Graphics g)
  {
    Graphics2D g2 = (Graphics2D)g;

    ////////// this is shit code.///////
    // this needs to be abstracted out into a view or camera class
    // it needs to take into account for scale and size of clipping
    int cellsX = (int) Math.ceil(g2.getClipBounds().getWidth()/80f);
    int cellsY = (int) Math.ceil(g2.getClipBounds().getHeight()/80f);
    ////////// end shit code ///////////

    Tile[][] houseMatrix = house.getHouse();

    for (int i = 0; i < cellsY; i++)
    {
      for (int j = 0; j < cellsX; j++)
      {
        if (houseMatrix[i][j] instanceof Wall)
        {
          g2.drawImage(tileGraphics[1].getImage(), j * TILE_WIDTH,
                       i * TILE_HEIGHT, null);
        }
        else if (houseMatrix[i][j] instanceof Floor)
        {
          g2.drawImage(tileGraphics[0].getImage(), j * TILE_WIDTH,
                       i * TILE_HEIGHT, null);
        }
      }
    }
  }
}
