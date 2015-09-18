package model;


public class Character implements Mover
{
  private float
          stamina,
          hearing,
          sight,
          speed,
          regen,
          x, y,
          traps,
          rotation;

  public Character()
  {

    rotation = 0;
    hearing = 10;
    stamina = 5;
    sight = 5;
    speed = 1;
    regen = .2f;
    x = 0f;
    y = 0f;
    traps = 0;
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
  public float getStamina()
  {
    return stamina;
  }

  @Override
  public void move (float x, float y)
  {
    this.x = x;
    this.y = y;
    //System.out.println("Character at x=" + this.x + ", y=" + this.y + ")");
  }

  @Override
  public float setSpeed (float speed)
  {
    this.speed = speed;
    return speed;
  }

  @Override
  public float setStamina(float stamina)
  {
    this.stamina = stamina;
    return stamina;
  }

  @Override
  public float setRotation (float rotation)
  {
    this.rotation = rotation;
    return rotation;
  }

  /**
   * Euclidean distance of tiles
   * @return
   */
  public float getSight ()
  {
    return sight;
  }

  /**
   * Euclidean distance of tiles
   * @return
   */
  public float getHearing ()
  {
    return hearing;
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

}
