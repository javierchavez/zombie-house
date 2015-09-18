package view;

import model.House;
import model.Mover;
import model.Sound;
import model.Zombie;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;


public class ZombieRenderer extends Renderer
{

  private final House house;
  private final int w;
  private final int h;
  ZombieSprite zombieSprite = new ZombieSprite();


  private BufferedImage[] walkingWest = {
          ZombieSprite.getSprite(0, 3),
          ZombieSprite.getSprite(1, 3),
          ZombieSprite.getSprite(2, 3)};

  private BufferedImage[] walkingEast = {
          ZombieSprite.getSprite(0, 1),
          ZombieSprite.getSprite(1, 1),
          ZombieSprite.getSprite(2, 1)};

  private BufferedImage[] walkingNorth = {
          ZombieSprite.getSprite(0, 2),
          ZombieSprite.getSprite(1, 2),
          ZombieSprite.getSprite(2, 2)};

  private BufferedImage[] walkingSouth = {
          ZombieSprite.getSprite(0, 0),
          ZombieSprite.getSprite(1, 0),
          ZombieSprite.getSprite(2, 0)};


  private Animation west = new Animation(walkingWest, 1);
  private Animation east = new Animation(walkingEast, 1);
  private Animation north = new Animation(walkingNorth, 1);
  private Animation south = new Animation(walkingSouth, 1);

  // check direction... need a AnimationFactoryClass
  private Animation animation;


  public ZombieRenderer(House house)
  {
    this.w = house.getWidth();
    this.h = house.getHeight();

    this.house = house;

  }

  @Override
  public void render (Graphics g)
  {
    Graphics2D g2 = (Graphics2D)g;

    ////////// this is shit code.///////
    // this needs to be abstracted out into a view or camera class
    // it needs to take into account for scale and size of clipping

    List<Zombie> zombies =  house.getZombies();
    for (int i = 0; i < zombies.size(); i++)
    {
      float x = zombies.get(i).getCurrentX();
      float y = zombies.get(i).getCurrentY();
      setAnimation(zombies.get(i));
      g2.drawImage(animation.getSprite(), (int) ((x * TILE_HEIGHT-Sprite.SIZE) + x),
                   (int) ((y * TILE_HEIGHT-Sprite.SIZE) + y), null);
      
    }

  }

  private void setAnimation(Zombie zombie)
  {
    switch ((int) zombie.getRotation())
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
