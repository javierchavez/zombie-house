package view;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuSprite
{
  private static int SIZE_X = 111;
  private static int SIZE_Y = 49;

  public static BufferedImage getSprite (int x, int y)
  {
    return MenuSprite.getSprite("menu-sprite.png", x, y);
  }

  public static BufferedImage getSprite (String fileName, int x, int y)
  {
    BufferedImage image = loadSprite(fileName);
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
