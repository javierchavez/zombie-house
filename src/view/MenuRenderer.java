package view;


import model.GameOptions;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuRenderer extends Renderer
{
  private static BufferedImage image;
  private final GameOptions options;
  private double currentOpacity = 0.0;

  MenuSprite menuSprite = new MenuSprite();


  private BufferedImage[] selections = {
          MenuSprite.getSprite(0, 0),
          MenuSprite.getSprite(0, 1),
          MenuSprite.getSprite(0, 2),
          MenuSprite.getSprite(0, 3),
          MenuSprite.getSprite(0, 4)};

  // check direction... need a AnimationFactoryClass
  private Animation animation;

  public MenuRenderer (GameOptions options)
  {
    this.options = options;
  }


  public BufferedImage getImage (String s)
  {
    try
    {
      MenuRenderer.image = ImageIO.read(new File("resources/" + s));
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
     return image;
  }

  private int oscillator (double opacity)
  {
    return (int) ((Math.sin(opacity) * 127) + 127); // non transparent
  }

  private int blinker (double opacity)
  {
    return (int) ((int) 127f + 127f * Math.sin( opacity * 2.0 * Math.PI));
  }

  @Override
  public void render (Graphics2D g)
  {
    g.setColor(new Color(0, 0, 0, this.oscillator(currentOpacity)));
    currentOpacity += .09;
    if (currentOpacity >= Math.PI * 2)
    {
      currentOpacity = 0;
    }
    g.drawImage(getImage("background-menu.png"),
                0,
                0,
                 null);

    g.fillRect(0,0, ((int) viewBounds.getWidth()),
               ((int) viewBounds.getHeight()));

    g.drawImage(getImage("main-screen.png"),
                (int)(viewBounds.getWidth() - image.getWidth())/2,
                (int)(viewBounds.getHeight() - image.getHeight())/2, null);


    g.setColor(new Color(255,255,255, this.blinker(currentOpacity)));
    g.setFont(new Font("Verdana", Font.BOLD, 40));

    // add text or variable to render here
    g.drawString("Level Complete",
                 ((int)((viewBounds.getWidth() - image.getWidth())/2)+ 250),
                 ((int)(viewBounds.getHeight() - image.getHeight())/2) +150);

    g.drawImage(selections[options.getState().ordinal()],
                ((int)(viewBounds.getWidth() - image.getWidth())/2) + 341,
                ((int)(viewBounds.getHeight() - image.getHeight())/2) + 264,
                null);

  }
}
