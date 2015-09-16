package view;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FloorGraphic
{
  private static BufferedImage image;

  public FloorGraphic ()
  {
    try
    {
      FloorGraphic.image = ImageIO.read(new File("resources/floor.png"));
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
