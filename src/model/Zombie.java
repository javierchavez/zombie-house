package model;

import common.Duration;
import common.Speed;

import java.util.List;

public class Zombie extends Character implements Deadly
{
  protected float zombieDecisionRate;
  protected FindStrategy findStrategy;
  int intelligence;

  public Zombie()
  {
    this(new BFSFindStrategy());
  }

  /**
   * Set the type of findStrategy this zombie should use
   *
   * @param findStrategy the algorithm the zombie shall use for finding Character
   */
  public Zombie(FindStrategy<Tile> findStrategy)
  {
    zombieDecisionRate = Duration.ZOMBIE_UPDATE;
    rotation = 0;
    speed = Speed.STAGGER;
    height = .65f;
    width = .5f;
    this.findStrategy = findStrategy;
  }

  @Override
  public void setTakePoints (float points)
  {
    return;
  }

  @Override
  public float getTakePoints ()
  {
    return 0;
  }

  /**
   * Checks if player is within distance of zombie's smell.
   * @return true if the player is within the zombie's smell radius
   *         otherwise false
   */
  public boolean sense (Tile playerTile)
  {
    float smell = getSmell();
    float zx = getCurrentX();
    float zy = getCurrentY();
    int px = playerTile.getCol();
    int py = playerTile.getRow();

    float dx = (zx - px) * (zx - px);
    float dy = (zy - py) * (zy - py);
    return (Math.sqrt(dx+dy) <= smell);
  }

  public FindStrategy getStrategy ()
  {
    return findStrategy;
  }
}
