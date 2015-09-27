package view;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ObstacleGraphic
{
  private static BufferedImage image;

  public ObstacleGraphic ()
  {
    try
    {
      ObstacleGraphic.image = ImageIO.read(new File("resources/obstacle.png"));
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  public BufferedImage getImage ()
  {
    //    return image.getSubimage(0, 0, 32, 32);
    return image;
  }
}
