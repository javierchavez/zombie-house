package view;


import java.awt.image.BufferedImage;

public class ZombieSprite
{

  public static BufferedImage getSprite (int x, int y)
  {
    return Sprite.getSprite("zombie-sprite.png", x, y);
  }
}
