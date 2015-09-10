package model;



public class Zombie implements LifeTaker, Mover
{
  protected float
          zombieDecisionRate,
          rotation,
          smell,
          speed,
          x, y;

  protected Finder finder;

  public Zombie()
  {
    zombieDecisionRate = 2f;
    rotation = 0;
    smell = 7f;
    speed = .5f;
    x = 0;
    y = 0;
    finder = new Dijkstra();
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
