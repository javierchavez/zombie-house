package model;



public class Zombie implements Path, LifeTaker, Movable
{
  private float
          zombieDecisionRate,
          rotation,
          smell,
          speed,
          x, y;

  public Zombie()
  {
    zombieDecisionRate = 2f;
    rotation = 0;
    smell = 7f;
    speed = .5f;
    x = 0;
    y = 0;
  }

  @Override
  public void find (House board, Object start, Object end)
  {
    // since this is Zombie we can use the BFS or Dijkstra alg
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
}
