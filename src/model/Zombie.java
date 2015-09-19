package model;



public class Zombie extends Character implements Deadly
{
  protected float
          zombieDecisionRate;
  protected FindStrategy findStrategy;

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
  public boolean sense(House house)
  {
    Tile zombie = house.getTile((int) getCurrentX(), (int) getCurrentY());
    Tile player = house.getPlayerTile();
    int smell = 7;

    int zx = zombie.getCol();
    int zy = zombie.getRow();
    int px = player.getCol();
    int py = player.getRow();

    int dx = (zx - px) * (zx - px);
    int dy = (zy - py) * (zy - py);
    return ((int) Math.sqrt(dx+dy) == smell);
  }

  public Tile find(House house)
  {
    // calls findStrategy
    Tile zombie = house.getTile((int) getCurrentX(), (int) getCurrentY());
    findStrategy.find(house, zombie, house.getPlayerTile());
    return (Tile) findStrategy.getPath().get(0);
  }
}
