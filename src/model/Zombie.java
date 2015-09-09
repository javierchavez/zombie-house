package model;


import java.util.List;

public class Zombie implements Path
{
  @Override
  public List<Object> find (House board, Object start, Object end)
  {
    // since this is Zombie we can use the BFS or Dijkstra alg
    return null;
  }
}
