package view;

import model.*;

import javax.sound.sampled.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;


public class PlayerRenderer extends Renderer
{
  private final Player player;
  private final int h;
  private final int w;
  PlayerSprite playerSprite = new PlayerSprite();


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
  Clip c = null;
  public PlayerRenderer (Player player, int w, int h)
  {
    this.w = w;
    this.h = h;
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
//    Graphics2D g2 = (Graphics2D) g;
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

    ////////// this is shit code.///////
    // this needs to be abstracted out into a view or camera class
    // it needs to take into account for scale and size of clipping
//    double width = g2.getClipBounds().getWidth();
//    double height = g2.getClipBounds().getHeight();

//    int tileW = (int) (width / w);
//    int tileH = (int) (height / h);
//    double width = g2.getClipBounds().getWidth();
//    double height = g2.getClipBounds().getHeight();
    double width = viewBounds.getWidth();
    double height = viewBounds.getHeight();




//    int cellsX = (int) Math.ceil(width/80f);
//    int cellsY = (int) Math.ceil(height/80f);
//    int tileW = (int) (width / cellsX);
//    int tileH = (int) (height / cellsY);

    float x = player.getCurrentX();
    float y = player.getCurrentY();

    // this needs to be in the converter class
    g2.drawImage(animation.getSprite(), (int) ((x * TILE_HEIGHT-Sprite.SIZE) + x),
                 (int) ((y * TILE_HEIGHT-Sprite.SIZE) + y), null);
//
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

  private void setAnimation()
  {
    switch ((int) player.getRotation())
    {
      case (int) Mover.EAST:
        animation = east;
        break;
      case (int) Mover.NORTH:
        animation = south;
        break;
      case (int) Mover.SOUTH:
        animation = north;
        break;
      case (int) Mover.WEST:
        animation = west;
        break;
    }
  }

}
