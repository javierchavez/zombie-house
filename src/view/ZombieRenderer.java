package view;

import model.House;
import model.Sound;
import model.Zombie;

import java.awt.*;
import java.util.*;
import java.util.List;


public class ZombieRenderer extends Renderer
{

  private final House house;
  Sound sound;

  public ZombieRenderer (House house)
  {
    this.house = house;
  }

  @Override
  public void render (Graphics g)
  {
    Graphics2D g2 = (Graphics2D)g;

    ////////// this is shit code.///////
    // this needs to be abstracted out into a view or camera class
    // it needs to take into account for scale and size of clipping
    double width = g2.getClipBounds().getWidth();
    double height = g2.getClipBounds().getHeight();

    int tileW = (int) (width / house.getWidth());
    int tileH = (int) (height / house.getHeight());


    List<Zombie> zombies =  house.getZombies();
    for (int i = 0; i < zombies.size(); i++)
    {
      float x = zombies.get(i).getCurrentX();
      float y = zombies.get(i).getCurrentY();
      g2.setColor(Color.GREEN);
      g2.drawRect((int)x*tileW, (int)y*tileH, tileW, tileH);


    }

  }
}
