package model;



public class SuperZombie extends Zombie
{
  public SuperZombie()
  {
    findStrategy = new AStarFindStrategy();
    setWidth(.01f);
  }
}
