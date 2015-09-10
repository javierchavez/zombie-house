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
    x = 0;
    y = 0;
  }

  @Override
  public float getCurrentX ()
  {
    return 0;
  }

  @Override
  public float getCurrentY ()
  {
    return 0;
  }

  @Override
  public float getSpeed ()
  {
    return 0;
  }

  @Override
  public float getRotation ()
  {
    return 0;
  }

  @Override
  public void move (float x, float y)
  {
    return;
  }

  @Override
  public float setSpeed (float speed)
  {
    return 0;
  }

  @Override
  public float setRotation (float rotation)
  {
    return 0;
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

  public void getTrap(Tile t)
  {
    t.popTrap();
    traps++;
  }
}
