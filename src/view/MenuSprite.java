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
 * This is the interface for Combustible objects
 */


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuSprite
{

  public static BufferedImage getSprite (int x, int y)
  {
    return MenuSprite.getSprite("menu-sprite.png", x, y);
  }

  public static BufferedImage getSprite (String fileName, int x, int y)
  {
    BufferedImage image = loadSprite(fileName);
    int SIZE_X = 111;
    int SIZE_Y = 49;
    return image.getSubimage(x * SIZE_X, y * SIZE_Y, SIZE_X, SIZE_Y);
  }

  private static BufferedImage loadSprite (String file)
  {
    BufferedImage sprite = null;

    try
    {
      sprite = ImageIO.read(new File("resources/" + file));
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }

    return sprite;
  }
}
