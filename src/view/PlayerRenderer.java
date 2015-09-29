package view;

import common.Direction;
import common.Size;
import model.*;

import java.awt.*;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.*;


public class PlayerRenderer extends Renderer
{
  private final Player player;


  private BufferedImage[] walkingWest = {
          PlayerSprite.getSprite(0, 3),
          PlayerSprite.getSprite(1, 3),
          PlayerSprite.getSprite(2, 3)};

  private BufferedImage[] walkingEast = {
          PlayerSprite.getSprite(0, 1),
          PlayerSprite.getSprite(1, 1),
          PlayerSprite.getSprite(2, 1)};

  private BufferedImage[] walkingNorth = {
          PlayerSprite.getSprite(0, 2),
          PlayerSprite.getSprite(1, 2),
          PlayerSprite.getSprite(2, 2)};

  private BufferedImage[] walkingSouth = {
          PlayerSprite.getSprite(0, 0),
          PlayerSprite.getSprite(1, 0),
          PlayerSprite.getSprite(2, 0)};


  private Animation west = new Animation(walkingWest, 1);
  private Animation east = new Animation(walkingEast, 1);
  private Animation north = new Animation(walkingNorth, 1);
  private Animation south = new Animation(walkingSouth, 1);

  // check direction... need a AnimationFactoryClass
  private Animation animation;
  private AudioInputStream as = null;
  private Clip c = null;
  public PlayerRenderer (Player player)
  {
    this.player = player;
    setAnimation();
    try
    {

      as = AudioSystem.getAudioInputStream(
              new File("resources/character-walking.wav"));

      c = AudioSystem.getClip();
      c.open(as);

    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    catch (UnsupportedAudioFileException e)
    {
      e.printStackTrace();
    }
    catch (LineUnavailableException e)
    {
      e.printStackTrace();
    }

  }

  @Override
  public void render (Graphics2D g2)
  {
    setAnimation();
    if (player.getSpeed() > 0)
    {
      if (!c.isRunning()) c.start();
      animation.start();
      animation.update();
    }
    else
    {
      animation.stop();
      animation.reset();
      c.setFramePosition(0);
      c.stop();
    }

    float x = player.getCurrentX();
    float y = player.getCurrentY();

    // note the minus signs
    g2.drawImage(animation.getSprite(),
                 (int) ((x * Size.TILE)),
                 (int) ((y * Size.TILE)), null);

  }

  private void setAnimation()
  {
    switch ((int) player.getRotation())
    {
      case (int) Direction.EAST:
        animation = east;
        break;
      case (int) Direction.NORTH:
        animation = south;
        break;
      case (int) Direction.SOUTH:
        animation = north;
        break;
      case (int) Direction.WEST:
        animation = west;
        break;
    }
  }
  // this is information about the player
  //    g2.setColor(Color.white);
  //    g2.drawRect((int) width / 2, 40, (int) (width / 4), 20);
  //
  //    g2.setColor(Color.RED);
  //    g2.fillRect((int) (width / 2) + 1, 41,
  //                (int) ((player.getStamina() / 5) * (width / 4)) - 2, 18);
  //
  //    if (player.trapsAvailable() > 0)
  //    {
  //      g2.drawImage(new TrapGraphic().getImage(), (int) (width / 2) - 20, 40,
  //                   null);
  //    }
  //    g2.setColor(Color.white);
  //    g2.drawString("tile (x, y) = " + x + ", " + y, (int) (width / 3), 50);
  //    g2.drawString("px (x, y) = " + x*TILE_HEIGHT + ", " + y*TILE_HEIGHT,
  //                  (int) (width / 3), 61);
  //
}
