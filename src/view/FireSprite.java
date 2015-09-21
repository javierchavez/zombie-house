package view;


import java.awt.image.BufferedImage;

public class FireSprite
{
  public static BufferedImage getSprite (int x, int y)
  {
    return Sprite.getSprite("explosion-sprite.png", x, y);
  }
}
