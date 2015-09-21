package controller;


import model.GameOptions;
import model.House;

public class MenuController implements GameController
{
  private static GameOptions options;
  private static boolean active;
  private final House house;
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
    if (options.getState() == GameOptions.GAME_STATE.EXIT)
    {
      System.exit(0);
    }
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

  public void next ()
  {
    choice++;
    if (choice == 5) choice = 0;
    options.setState(GameOptions.GAME_STATE.values()[choice]);
  }
}
