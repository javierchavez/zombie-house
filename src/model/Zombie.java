package model;


import java.util.List;

public class Zombie implements Path, LifeTaker
{
  @Override
  public List<Object> find (House board, Object start, Object end)
  {
    // since this is Zombie we can use the BFS or Dijkstra alg
    return null;
  }

  @Override
  public void setTakePoints (float points)
  {

  }

  @Override
  public float getTakePoints ()
  {
    return 0;
  }
}
