package model;


import java.util.*;

public class BFS implements Finder
{
  List<Tile> path;

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

  private List<Tile> reconstructPath (HashMap<Tile, Tile> cameFrom, Tile start,
                                      Tile end)
  {
    Tile current = end;
    List<Tile> path = new ArrayList<>();
    path.add(current);

    while (current != start)
    {
      current = cameFrom.get(current);
      if (current != null)
      {
        path.add(current);
      }
    }

    Collections.reverse(path);

    return path;
  }
}
