package controller;

import model.Character;
import model.House;
import view.HouseRenderer;
import view.Renderer;

import java.awt.*;
import java.awt.event.WindowEvent;


public class ZombieHouseGame
{
  private House house;
  private Character player;
  private Renderer renderer;
  private HouseController controller;

  public ZombieHouseGame()
  {
    player = new Character();
    player.move(House.WIDTH / 2, House.HEIGHT / 2);
    house = new House(player);

    renderer = new HouseRenderer(house);

    controller = new HouseController(house);
  }


  public void processEvent (AWTEvent e)
  {
    if (e.getID() == WindowEvent.WINDOW_CLOSING)
    {
      // persist some data about the session.
      System.exit(0);
    }

    // events
  }

  public void update(float deltaTime)
  {
    controller.update(deltaTime);
  }

  public void render (Graphics graphics)
  {
    renderer.render(graphics);
  }
}
