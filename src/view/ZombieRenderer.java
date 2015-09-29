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
 * Rendering zombies
 */


import common.Direction;
import common.Size;
import controller.MenuController;
import model.*;
import model.GameOptions.GAME_STATUS;
import model.Sound.SoundType;

import javax.sound.sampled.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


public class ZombieRenderer extends Renderer
{

  private final House house;
  private final ZombieSprite zombieSprite = new ZombieSprite();
  private final SuperZombieSprite superZombieSprite = new SuperZombieSprite();

  private final BufferedImage[] superWalkingWest = {
          SuperZombieSprite.getSprite(0, 3),
          SuperZombieSprite.getSprite(1, 3),
          SuperZombieSprite.getSprite(2, 3)};

  private final BufferedImage[] superWalkingEast = {
          SuperZombieSprite.getSprite(0, 1),
          SuperZombieSprite.getSprite(1, 1),
          SuperZombieSprite.getSprite(2, 1)};

  private final BufferedImage[] superWalkingNorth = {
          SuperZombieSprite.getSprite(0, 2),
          SuperZombieSprite.getSprite(1, 2),
          SuperZombieSprite.getSprite(2, 2)};

  private final BufferedImage[] superWalkingSouth = {
          SuperZombieSprite.getSprite(0, 0),
          SuperZombieSprite.getSprite(1, 0),
          SuperZombieSprite.getSprite(2, 0)};


  private final BufferedImage[] walkingWest = {
          ZombieSprite.getSprite(0, 3),
          ZombieSprite.getSprite(1, 3),
          ZombieSprite.getSprite(2, 3)};

  private final BufferedImage[] walkingEast = {
          ZombieSprite.getSprite(0, 1),
          ZombieSprite.getSprite(1, 1),
          ZombieSprite.getSprite(2, 1)};

  private final BufferedImage[] walkingNorth = {
          ZombieSprite.getSprite(0, 2),
          ZombieSprite.getSprite(1, 2),
          ZombieSprite.getSprite(2, 2)};

  private final BufferedImage[] walkingSouth = {
          ZombieSprite.getSprite(0, 0),
          ZombieSprite.getSprite(1, 0),
          ZombieSprite.getSprite(2, 0)};


  private final Animation west = new Animation(walkingWest, 1);
  private final Animation east = new Animation(walkingEast, 1);
  private final Animation north = new Animation(walkingNorth, 1);
  private final Animation south = new Animation(walkingSouth, 1);

  private final Animation superWest = new Animation(superWalkingWest, 1);
  private final Animation superEast = new Animation(superWalkingEast, 1);
  private final Animation superNorth = new Animation(superWalkingNorth, 1);
  private final Animation superSouth = new Animation(superWalkingSouth, 1);


  // check direction... need a AnimationFactoryClass
  private Animation animation;
  private Animation animationSuper;
  private Clip walkLeft = null;
  private Clip walkRight = null;
  private Clip walkStereo = null;

  private Clip hit = null;
  private Clip hitLeft = null;
  private Clip hitRight = null;

  private Clip sound = null;
  private Clip soundWalk = null;

  private GameOptions options;


  public ZombieRenderer (House house, GameOptions options)
  {
    this.options = options;
    this.house = house;
    try
    {

      AudioInputStream asHit = AudioSystem.getAudioInputStream(
              new File("resources/wall-hit.wav"));
      AudioInputStream asL = AudioSystem.getAudioInputStream(
              new File("resources/wall-hit-L.wav"));
      AudioInputStream asR = AudioSystem.getAudioInputStream(
              new File("resources/wall-hit-R.wav"));

      AudioInputStream asStereo = AudioSystem.getAudioInputStream(
              new File("resources/zombie-walk.wav"));
      AudioInputStream asLW = AudioSystem.getAudioInputStream(
              new File("resources/zombie-walk-L.wav"));
      AudioInputStream asRW = AudioSystem.getAudioInputStream(
              new File("resources/zombie-walk-R.wav"));

      walkStereo = AudioSystem.getClip();
      walkStereo.open(asStereo);

      walkLeft = AudioSystem.getClip();
      walkLeft.open(asLW);

      walkRight = AudioSystem.getClip();
      walkRight.open(asRW);

      hit = AudioSystem.getClip();
      hit.open(asHit);

      hitLeft = AudioSystem.getClip();
      hitLeft.open(asL);

      hitRight = AudioSystem.getClip();
      hitRight.open(asR);
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

  private Zombie zombieC = null;

  @Override
  public void render (Graphics2D g2)
  {
    List<Zombie> zombies = house.getZombies();
    Player player = house.getPlayer();

    for (int i = 0; i < zombies.size(); i++)
    {
      Zombie zombie = zombies.get(i);

      float x = zombie.getCurrentX();
      float y = zombie.getCurrentY();
      Tile t = house.getTile((int) y, (int) x);
      if (t != null && !player.senseSight(t))
      {
        continue;
      }

      setAnimation(zombie);
      g2.drawImage(animation.getSprite(), (int) ((x * Size.TILE)),
                   (int) ((y * Size.TILE)), null);
      if (zombie.getVolume() == 1f)
      {
        setSound(zombie);
        setWalkSound(zombie);

        if (zombie.getSoundType() == SoundType.HIT)
        {
          if (!sound.isRunning())
          {
            sound.start();
          }
          else
          {
            sound.setFramePosition(0);
            sound.stop();
          }
        }
        else if (zombie.getSoundType() == SoundType.WALK)
        {
          if (!soundWalk.isRunning())
          {
            zombieC = zombie;
            soundWalk.start();
            soundWalk.loop(Clip.LOOP_CONTINUOUSLY);
          }
//          }
//          else
//          {
//            soundWalk.setFramePosition(0);
//            soundWalk.stop();
//          }
        }
      }
    }

    SuperZombie superZombie = house.getSuperZombie();
    setSuperAnimation(superZombie);
    g2.drawImage(animationSuper.getSprite(),
                 (int) ((superZombie.getCurrentX() * Size.TILE)),
                 (int) ((superZombie.getCurrentY() * Size.TILE)), null);

    if (animation != null)
    {
      animation.start();
      animation.update();
    }
    if (animationSuper != null)
    {
      animationSuper.start();
      animationSuper.update();
    }


  }

  public void stopSounds()
  {
    if(sound != null)
    {
      sound.stop();
    }
    if (soundWalk != null)
    {
      soundWalk.stop();
    }
  }

  private void setAnimation (Zombie zombie)
  {
    switch ((int) zombie.getRotation())
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

  private void setSound (Zombie zombie)
  {
    switch (zombie.getChannel())
    {
      case LEFT:
        sound = hitLeft;
        break;
      case RIGHT:
        sound = hitRight;
        break;
      case STEREO:
        sound = hit;
        break;
    }
  }

  private void setWalkSound (Zombie zombie)
  {
    switch (zombie.getChannel())
    {
      case LEFT:
        soundWalk = walkLeft;
        break;
      case RIGHT:
        soundWalk = walkRight;
        break;
      case STEREO:
        soundWalk = walkStereo;
        break;
    }
  }

  private void setSuperAnimation (SuperZombie zombie)
  {
    switch ((int) zombie.getRotation())
    {
      case (int) Direction.EAST:
        animationSuper = superEast;
        break;
      case (int) Direction.NORTH:
        animationSuper = superSouth;
        break;
      case (int) Direction.SOUTH:
        animationSuper = superNorth;
        break;
      case (int) Direction.WEST:
        animationSuper = superWest;
        break;
    }
  }

  private void setSuperSoud (SuperZombie zombie)
  {
    switch ((int) zombie.getRotation())
    {
      case (int) Direction.EAST:
        animationSuper = superEast;
        break;
      case (int) Direction.NORTH:
        animationSuper = superSouth;
        break;
      case (int) Direction.SOUTH:
        animationSuper = superNorth;
        break;
      case (int) Direction.WEST:
        animationSuper = superWest;
        break;
    }
  }

}
