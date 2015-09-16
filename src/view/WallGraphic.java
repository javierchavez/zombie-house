package view;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WallGraphic
{
  static BufferedImage image;

  public WallGraphic ()
  {
    try
    {
      WallGraphic.image = ImageIO.read(new File("resources/wall.png"));
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  public BufferedImage getImage ()
  {
    //return image.getSubimage(0, 0, 32, 32);
    return image;
  }

}
