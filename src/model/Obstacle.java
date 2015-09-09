package model;


public class Obstacle implements Stationary, LifeTaker
{
  // life or energy points to deduct
  private float points;

  @Override
  public float getX ()
  {
    return 0;
  }

  @Override
  public float getY ()
  {
    return 0;
  }

  @Override
  public void setTakePoints (float points)
  {
    this.points = points;
  }

  @Override
  public float getTakePoints ()
  {
    return points;
  }
}
