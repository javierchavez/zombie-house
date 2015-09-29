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


public interface Sprite
{


  /**
   * Get the sprite
   *
   * @param fileName name of the image file
   * @param x        this is a multiplier in x direction i.e. x*TILE_SIZE(40)
   * @param y        this is a multiplier in y direction i.e. y*TILE_SIZE(40)
   * @return image cropped to sprite
   */
  static BufferedImage getSprite (String fileName, int x, int y, int SZ_X,
                                  int SZ_Y)
  {
    BufferedImage image = loadSprite(fileName);
    return image.getSubimage(x * SZ_X, y * SZ_Y, SZ_X, SZ_Y);
  }

  static BufferedImage loadSprite (String file)
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
