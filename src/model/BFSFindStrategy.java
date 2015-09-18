package model;


import java.util.*;

public class BFSFindStrategy extends AbstractFindStrategy implements FindStrategy<Tile>
{

  @Override
  public void find (House board, Tile start, Tile end)
  {
    Queue<Tile> frontier = new LinkedList<>();
    frontier.add(start);
    HashMap<Tile, Tile> cameFrom = new HashMap<>();
    cameFrom.put(start, null);

    while (!frontier.isEmpty())
    {
      Tile current = frontier.remove();
      List<Tile> neighbors = board.neighbors(current);

      if (current.equals(end))
      {
        break;
      }

      for (Tile next : neighbors)
      {
        if (!cameFrom.containsKey(next))
        {
          frontier.add(next);
          cameFrom.put(next, current);
        }
      }
    }

    path = reconstructPath(cameFrom, start, end);
  }

}
