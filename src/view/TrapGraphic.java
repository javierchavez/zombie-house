package view;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TrapGraphic
{
  private static BufferedImage image;

  public TrapGraphic ()
  {
    try
    {
      TrapGraphic.image = ImageIO.read(new File("resources/firetrap.png"));
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  public BufferedImage getImage ()
  {
    // return image.getSubimage(0, 0, 32, 32);
    // return image;
    // double scaleX = (double)40/image.getWidth();
    // double scaleY = (double)40/image.getHeight();
    AffineTransform scaleTransform = AffineTransform.getScaleInstance(.5, .5);
    AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);

    return bilinearScaleOp.filter(
            image,
            new BufferedImage(70, 70, image.getType()));
  }
}
