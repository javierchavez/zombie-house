package view;

/**
 * @author Javier Chavez
 * @author Alex Baker
 * @author Erin Sosebee
 * <p>
 * Date September 28, 2015
 * CS 351
 * Zombie House
 * <p>
 * Graphic for wall
 */


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
    return image;
  }
}
