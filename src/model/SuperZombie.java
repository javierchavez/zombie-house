package model;



public class SuperZombie extends Zombie
{
  public SuperZombie()
  {
    findStrategy = new AStarFindStrategy();
  }
}
