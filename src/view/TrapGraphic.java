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
 * Graphic for trap
 */

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TrapGraphic
{
  private static BufferedImage imageBurned;
  private static BufferedImage image;

  public TrapGraphic ()
  {
    try
    {
      TrapGraphic.image = ImageIO.read(new File("resources/firetrap.png"));
      TrapGraphic.imageBurned = ImageIO.read(
              new File("resources/floor-burned" + ".png"));

    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  public BufferedImage getImage ()
  {
    // return image.getSubImage(0, 0, 32, 32);
    // return image;
    // double scaleX = (double)40/image.getWidth();
    // double scaleY = (double)40/image.getHeight();
    AffineTransform scaleTransform = AffineTransform.getScaleInstance(.5, .5);
    AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform,
                                                              AffineTransformOp.TYPE_BILINEAR);

    return bilinearScaleOp.filter(image,
                                  new BufferedImage(70, 70, image.getType()));
  }

  public BufferedImage getImageBurned ()
  {
    return imageBurned;
  }

}
