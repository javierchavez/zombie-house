package model;

import java.util.List;
import java.util.Random;

public class Zombie extends Character implements Deadly
{
  protected float zombieDecisionRate;
  protected FindStrategy findStrategy;
  private Random rand = new Random();
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
    zombieDecisionRate = 2f;
    rotation = 0;
    speed = .5f;
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
  public boolean sense(Tile zombieTile, Tile playerTile)
  {
    float smell = getSmell();
    int zx = zombieTile.getCol();
    int zy = zombieTile.getRow();
    int px = playerTile.getCol();
    int py = playerTile.getRow();

    int dx = (zx - px) * (zx - px);
    int dy = (zy - py) * (zy - py);
    return ((int) Math.sqrt(dx+dy) <= smell);
  }

  public Tile find(House house, Tile zombieTile, Tile playerTile)
  {
    findStrategy.find(house, zombieTile, playerTile);
    return (Tile) findStrategy.getPath().get(0);
  }

  public int setIntelligence(int intelligence)
  {
    this.intelligence = intelligence;
    return intelligence;
  }

  public int getIntelligence()
  {
    return intelligence;
  }

  public FindStrategy getStrategy ()
  {
    return findStrategy;
  }


}
