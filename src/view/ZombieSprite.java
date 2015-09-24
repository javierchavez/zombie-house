package view;


import common.Size;

import java.awt.image.BufferedImage;

public class ZombieSprite implements Sprite
{

  public static BufferedImage getSprite (int x, int y)
  {
    return Sprite.getSprite("zombie-sprite.png", x, y,
                            Size.ZOM_SPRITE_X, Size.ZOM_SPRITE_Y);
  }
}
