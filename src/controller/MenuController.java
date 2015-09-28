package controller;


import model.GameOptions;
import model.House;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MenuController implements GameController, KeyListener
{
  private static GameOptions options;
  private static boolean active;
  private House house;
  private static int choice = 0;

  public MenuController(House house, GameOptions options)
  {
    this.house = house;
    MenuController.options = options;
  }

  public static boolean isActive ()
  {
    return active;
  }

  public static void toggleActive ()
  {

    MenuController.active = !active;
  }

  public static void setActive (boolean active)
  {
    MenuController.active = active;
  }


  @Override
  public void update (float deltaTime)
  {

  }

  @Override
  public void render (Graphics2D graphics)
  {

  }

  public void next ()
  {
    choice = options.getState().ordinal()+1;
    if (choice == GameOptions.GAME_STATE.values().length) choice = 0;
    options.setState(GameOptions.GAME_STATE.values()[choice]);
  }

  public void previous ()
  {
    choice = options.getState().ordinal()-1;
    if (choice < 0) choice = GameOptions.GAME_STATE.values().length-1;
    options.setState(GameOptions.GAME_STATE.values()[choice]);
  }

  @Override
  public void keyTyped (KeyEvent e)
  {

  }

  @Override
  public void keyPressed (KeyEvent e)
  {

  }

  @Override
  public void keyReleased (KeyEvent e)
  {

  }
}
