package model;



public class Zombie implements Deadly, Mover
{
  protected float
          zombieDecisionRate,
          rotation,
          smell,
          speed,
          x, y;

  protected FindStrategy findStrategy;

  public Zombie()
  {
    zombieDecisionRate = 2f;
    rotation = 0;
    smell = 7f;
    speed = .5f;
    x = 0;
    y = 0;
    findStrategy = new BFSFindStrategy();
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
    smell = 7f;
    speed = .5f;
    x = 0;
    y = 0;
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

  @Override
  public float getCurrentX ()
  {
    return x;
  }

  @Override
  public float getCurrentY ()
  {
    return y;
  }

  @Override
  public float getSpeed ()
  {
    return speed;
  }

  @Override
  public float getRotation ()
  {
    return rotation;
  }

  @Override
  public void move (float x, float y)
  {
    this.x = x;
    this.y = y;
  }

  @Override
  public float setSpeed (float speed)
  {
    return this.speed = speed;
  }

  @Override
  public float setRotation (float rotation)
  {
    return this.rotation = rotation;
  }

  @Override
  public float getStamina() { return 0; }

  @Override
  public float setStamina(float stamina)
  {
    return 0;
  }

  public float getSmell()
  {
    return smell;
  }
}
