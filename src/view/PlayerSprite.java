package view;

import java.awt.image.BufferedImage;

public class PlayerSprite
{
  public PlayerSprite () { }

  public static BufferedImage getSprite (int x, int y)
  {
    return Sprite.getSprite("character-sprite.png", x, y);
  }
}
