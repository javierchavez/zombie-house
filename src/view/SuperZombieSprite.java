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

import common.Size;

import java.awt.image.BufferedImage;

public class SuperZombieSprite
{

  public static BufferedImage getSprite (int x, int y)
  {
    return Sprite.getSprite("szombie-sprite.png", x, y, Size.ZOM_SPRITE_X,
                            Size.ZOM_SPRITE_Y);
  }
}
