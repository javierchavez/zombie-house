package model;

import java.util.ArrayList;
import java.util.List;


public class House
{

  public static final int WIDTH = 11;
  public static final int HEIGHT = 11;

  private List<List<Object>> board = new ArrayList<>();
  private List<Stationary> obstacles = new ArrayList<>();
  private List<Zombie> zombies = new ArrayList<>();
  private Character player;

  public House (Character player)
  {

  }


  public List<Stationary> getObstacles()
  {
    return obstacles;
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
