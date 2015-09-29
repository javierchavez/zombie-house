package model;


/**
 * @author Javier Chavez
 * @author Alex Baker
 * @author Erin Sosebee
 * <p>
 * Date September 28, 2015
 * CS 351
 * Zombie House
 * <p>
 * This is the interface for Combustible objects
 */


/**
 * Super Zombie class with special characteristics
 */
public class SuperZombie extends Zombie
{
  private Zombie zombie;

  public SuperZombie ()
  {
    findStrategy = new AStarFindStrategy();
  }

  public void setZombie (Zombie zombie)
  {
    this.zombie = zombie;
  }

  @Override
  public boolean sense (Tile playerTile)
  {
    return super.sense(playerTile) || telepathySmellSense();
  }

  private boolean telepathySmellSense ()
  {
    if (zombie == null)
    {
      return false;
    }

    if (!zombie.sensesPlayer())
    {
      zombie = null;
    }
    else
    {
      return zombie.sensesPlayer();
    }

    return false;
  }
}
