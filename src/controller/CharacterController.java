package controller;


import model.Character;
import model.House;

public class CharacterController
{

  private final House house;

  public CharacterController (House house)
  {
    this.house = house;
  }

  /**
   *
   * collision detection
   */
  public void checkCollision(float deltaTime)
  {

  }


  public void update (float deltaTime)
  {
    Character player = house.getPlayer();
    // change the player's x and y
    // the distance of the player is dependant on time...
    // the player's x and y are in Pixels
  }

  public void pickup ()
  {

  }
}
