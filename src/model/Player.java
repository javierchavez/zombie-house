package model;


import java.awt.geom.Rectangle2D;

public class Player extends Character
{
  protected float traps = 0;
  protected PlayerState state = PlayerState.ALIVE;

  public Player()
  {
    height = .60f;
    width = .50f;
  }

  public PlayerState getState ()
  {
    return state;
  }

  public void setState (PlayerState state)
  {
    this.state = state;
  }


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


  public enum PlayerState
  {
    ALIVE, DEAD

  }

  @Override
  public Rectangle2D getBoundingRectangle ()
  {
    return super.getBoundingRectangle();
  }

  @Override
  public void setCombustedState (CombustedState s)
  {
    super.setCombustedState(s);
    if (getCombustedState() == CombustedState.IGNITED)
    {
      state = PlayerState.DEAD;
    }
  }
}
