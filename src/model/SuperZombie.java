package model;


/**
 * Super Zombie class with special characteristics
 */
public class SuperZombie extends Zombie
{
  public SuperZombie()
  {
    findStrategy = new AStarFindStrategy();
  }
}
