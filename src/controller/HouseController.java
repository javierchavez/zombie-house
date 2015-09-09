package controller;


import model.Character;
import model.House;

public class HouseController
{

  private final House house;

  public HouseController (House house)
  {
    this.house = house;
  }

  public void update (float deltaTime)
  {
    Character player = house.getPlayer();
    // change the player's x and y
  }
}
