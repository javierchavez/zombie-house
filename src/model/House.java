package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class House
{

  public static final int WIDTH = 11;
  public static final int HEIGHT = 11;

  private List<List<Object>> boerd = new ArrayList<>();
  private List<Tile> tiles = new ArrayList<>();
  private List<Zombie> zombies = new ArrayList<>();
  private Character player;

  public House (Character player)
  {

  }


  public List<Tile> getObstacles ()
  {
    return tiles.stream().filter(tile ->
      tile instanceof Obstacle
    ).collect(Collectors.toList());
  }

  public List<Zombie> getZombies ()
  {
    return zombies;
  }

  public Character getPlayer ()
  {
    return player;
  }
}
