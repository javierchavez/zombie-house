package model;


import java.util.*;
import java.util.List;

public class AStarFindStrategy extends AbstractFindStrategy implements FindStrategy<Tile>
{

  @Override
  public void find (House house, Tile start, Tile end)
  {
    Queue<TilePriority> frontier = new PriorityQueue<>();
    List<Tile> visited = new ArrayList<>();
    Map<Tile, Tile> cameFrom = new HashMap<>();
    Map<Tile, Integer> costSoFar = new HashMap<>();

    frontier.add(new TilePriority(start, 0));
    cameFrom.put(start, null);
    costSoFar.put(start, 0);

    while (frontier.size() > 0)
    {
      Tile current = frontier.poll().getTile();
      visited.add(current);

      if (current.equals(end))
      {
        break;
      }

      for (Tile next : house.neighbors(current))
      {
        if (visited.contains(next))
        {
          continue;
        }

        int new_cost = costSoFar.get(current) + next.getCost();
        if (!costSoFar.containsKey(next) || (new_cost < costSoFar.get(next)))
        {
          costSoFar.put(next, new_cost);
          int priority = (int) (new_cost+(Math.abs(end.getRow()-next.getRow())+Math.abs(end.getCol()-next.getCol())));
          frontier.add(new TilePriority(next, priority));
          cameFrom.put(next, current);
        }
      }
    }

    path = reconstructPath(cameFrom, start, end);
  }

  @Override
  public Move getNextMove (House house, Tile start)
  {
    return null;
  }


  private class TilePriority implements Comparable<TilePriority>
  {
    private Tile tile;
    private int cost;

    public TilePriority (Tile tile, int cost)
    {
      this.tile = tile;
      this.cost = cost;
    }

    public Tile getTile()
    {
      return tile;
    }

    public int getCost()
    {
      return cost;
    }

    public int compareTo (TilePriority obj)
    {
      if (this.getCost() < obj.getCost())
      {
        return -1;
      }
      else if (this.getCost() == obj.getCost())
      {
        return 0;
      }
      else
      {
        return 1;
      }
    }
  }
}