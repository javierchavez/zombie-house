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
 * Defines a super zombie roaming the house
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

  /**
   * Set the zombie as a dependency injection informally, the super zombie is
   * reading from this zombies mind... Kinda like the super zombie is inside
   * the head of this zombie.
   *
   * @param zombie reference that will be used for sensing smell
   */
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
