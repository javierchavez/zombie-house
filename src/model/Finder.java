package model;


import java.util.List;

public interface Finder<T extends Tile>
{
  /**
   * Find a path. This is the API for the controller to call once a vicinity
   * condition has been met.
   *
   * @param board current house (might not need this)
   * @param start start position (tile)
   * @param end end position (title that *Character is standing on*)
   */
  void find (House board, T start, T end);

  List<T> getPath ();
}
