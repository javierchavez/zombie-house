package view;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuRenderer extends Renderer
{
  private static BufferedImage image;
  private double currentOpacity = 0.0;


  public MenuRenderer ()
  {

  }


  public BufferedImage getImage ()
  {
    try
    {
      MenuRenderer.image = ImageIO.read(new File("resources/main-screen" +
                                                           ".png"));
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
     return image;
  }

  private int oscillator (double opacity)
  {
    return (int) ((Math.sin(opacity) * 127) + 127);
  }

  @Override
  public void render (Graphics2D g)
  {
    g.setColor(new Color(70, 70, 70, this.oscillator(currentOpacity)));
    currentOpacity += .08;
    if (currentOpacity >= Math.PI * 2)
    {
      currentOpacity = 0;
    }
    g.fillRect(0,0, ((int) viewBounds.getWidth()),
               ((int) viewBounds.getHeight()));

    g.drawImage(getImage(),
                (int)(viewBounds.getWidth() - image.getWidth())/2,
                (int)(viewBounds.getHeight() - image.getHeight())/2, null);
  }
}
