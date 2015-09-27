package view;


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
