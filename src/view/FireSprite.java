package view;


import java.awt.image.BufferedImage;

public class FireSprite implements Sprite
{

  public static BufferedImage getSprite (int x, int y)
  {
    return Sprite.getSprite("fire.png", x, y, 85, 85);
  }
}
