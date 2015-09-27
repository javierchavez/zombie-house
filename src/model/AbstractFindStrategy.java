package model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class AbstractFindStrategy implements FindStrategy<Tile>
{
  protected List<Tile> path;

  protected List<Tile> reconstructPath (Map<Tile, Tile> cameFrom,
                                        Tile start, Tile end)
  {
    Tile current = end;
    path = new ArrayList<>();
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


  @Override
  public List<Tile> getPath ()
  {
    return path;
  }
}
