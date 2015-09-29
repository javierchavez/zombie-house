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
 * Defines the functionality of a zombie
 */


import common.Duration;
import common.Speed;

/**
 * Zombie character
 */
public class Zombie extends Character implements Deadly
{
  protected final float zombieDecisionRate;
  protected FindStrategy findStrategy;
  private boolean smellsPlayer;

  public Zombie ()
  {
    this(new AStarFindStrategy());
  }

  /**
   * Set the type of findStrategy this zombie should use
   *
   * @param findStrategy the algorithm the zombie shall use for finding Character
   */
  public Zombie (FindStrategy<Tile> findStrategy)
  {
    zombieDecisionRate = Duration.ZOMBIE_UPDATE;
    rotation = 0;
    speed = Speed.STAGGER;
    height = .65f;
    width = .5f;
    this.findStrategy = findStrategy;
  }

  @Override
  public float getTakePoints ()
  {
    return 0;
  }

  @Override
  public void setTakePoints (float points)
  {
  }

  /**
   * Checks if player is within distance of zombie's smell.
   *
   * @param playerTile check if this can sense playerTile
   * @return true if the player is within the zombie's smell radius
   * otherwise false
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
    smellsPlayer = Math.sqrt(dx + dy) <= smell;
    return smellsPlayer;
  }

  /**
   * Returns the strategy used to find anther character
   *
   * @return class for finding a tile
   */
  public FindStrategy getStrategy ()
  {
    return findStrategy;
  }

  /**
   * @return true if player was sensed
   */
  public boolean sensesPlayer ()
  {
    return smellsPlayer;
  }
}
