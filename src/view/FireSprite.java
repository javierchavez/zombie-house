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


import java.awt.image.BufferedImage;

public class FireSprite implements Sprite
{

  public static BufferedImage getSprite (int x, int y)
  {
    return Sprite.getSprite("fire.png", x, y, 85, 85);
  }
}
