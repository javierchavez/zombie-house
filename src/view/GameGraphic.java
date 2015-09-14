package view;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public enum GameGraphic
{
  FLOOR(1, "floor.png"),
  EMPTY(0, ""),
  WALL(9999999, "wall.png");

  private int weight;
  private BufferedImage image;


  GameGraphic (int weight, String image)
  {
    this.weight = weight;
    if(weight == 0) return;
    try
    {
      this.image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(
              "resources/" + image));
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }

  }

  public int getWeight ()
  {
    return weight;
  }

  public BufferedImage getImage ()
  {
    return image;
  }
}
