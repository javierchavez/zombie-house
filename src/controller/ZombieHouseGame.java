package controller;

import model.Character;
import model.House;
import view.HouseRenderer;
import view.Renderer;

import java.awt.*;


public class ZombieHouseGame
{
  private House house;
  private Character player;
  private Renderer renderer;
  private HouseController controller;

  public ZombieHouseGame()
  {
    player = new Character();
    player.setX(House.WIDTH / 2);
    player.setY(House.HEIGHT / 2);
    house = new House(player);

    renderer = new HouseRenderer(house);

    controller = new HouseController(house);
  }


  public void processEvent (AWTEvent e)
  {
    // events
  }

  public void render (Graphics graphics)
  {
    renderer.render(graphics);
  }
}
