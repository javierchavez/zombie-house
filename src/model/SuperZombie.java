package model;


/**
 * Super Zombie class with special characteristics
 */
public class SuperZombie extends Zombie
{
  public SuperZombie()
  {
    findStrategy = new AStarFindStrategy();
  }

  private Zombie zombie;

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
