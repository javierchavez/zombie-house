package controller;

import model.Character;
import model.House;
import view.CharacterRenderer;
import view.HouseRenderer;
import view.Renderer;

import java.awt.*;
import java.awt.event.WindowEvent;


public class ZombieHouseGame
{
  private House house;
  private Character player;
  private Renderer houseRenderer;
  private HouseController controller;
  private Renderer playerRenderer;

  public ZombieHouseGame()
  {
    player = new Character();
    player.move(House.WIDTH / 2, House.HEIGHT / 2);
    house = new House(player);

    houseRenderer = new HouseRenderer(house);
    playerRenderer = new CharacterRenderer(player);


    controller = new HouseController(house);
  }


  public void processEvent (AWTEvent e)
  {
    if (e.getID() == WindowEvent.WINDOW_CLOSING)
    {
      // persist some data about the session.
      System.exit(0);
      return;
    }

    switch (e.getID()) {
      // update player speed when r pressed
      case Event.KEY_PRESS:
      case Event.KEY_ACTION:
        break;
      case Event.KEY_RELEASE:
        break;
    }

  }

  public void update(float deltaTime)
  {
    controller.update(deltaTime);
  }

  public void render (Graphics graphics)
  {
    houseRenderer.render(graphics);
    playerRenderer.render(graphics);
  }
}
