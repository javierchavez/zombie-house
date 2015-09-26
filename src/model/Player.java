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

  public boolean sense(Tile zombieTile, Tile playerTile)
  {
    float hearing = getHearing();
    int zx = zombieTile.getCol();
    int zy = zombieTile.getRow();
    int px = playerTile.getCol();
    int py = playerTile.getRow();

    int dx = (zx - px) * (zx - px);
    int dy = (zy - py) * (zy - py);
    return ((int) Math.sqrt(dx+dy) <= hearing);
  }

  public boolean senseSight (Tile playerTile)
  {
    float smell = getSight();
    float zx = getCurrentX();
    float zy = getCurrentY();
    int px = playerTile.getCol();
    int py = playerTile.getRow();

    float dx = (zx - px) * (zx - px);
    float dy = (zy - py) * (zy - py);
    return (Math.sqrt(dx+dy) <= smell);
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
