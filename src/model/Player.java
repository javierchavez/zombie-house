package model;


public class Player extends Character
{
  protected float traps = 0;


  public int trapsAvailable()
  {
    return (int) traps;
  }

  public void pickupTrap(Tile tile)
  {
    if (tile.getTrap() == Trap.FIRE)
    {
      traps++;
      tile.popTrap();
    }
  }

  public void dropTrap(Tile tile)
  {
    if (traps > 0)
    {
      traps--;
      tile.setTrap(Trap.FIRE);
      return;
    }
    tile.setTrap(Trap.NONE);
  }
}
