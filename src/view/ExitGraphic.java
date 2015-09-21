package view;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ExitGraphic
{
  private static BufferedImage image;

  public ExitGraphic ()
  {
    try
    {
      ExitGraphic.image = ImageIO.read(new File("resources/end.png"));
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