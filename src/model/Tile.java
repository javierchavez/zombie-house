package model;


public abstract class Tile
{

  Trap trap;

  /**
   * Get the column position of the tile
   * @return column
   */
  float getX()
  {
    return 0f;
  }

  /**
   * Get the row position of the tile
   * @return row
   */
  float getY()
  {
    return 0f;
  }


  class Trap
  {
    private final String name;

    public Trap(String s)
    {
      this.name = s;
    }
  }

  Trap getTrap()
  {
    return trap;
  }

  void setTrap(Trap trap)
  {
    this.trap = trap;
  }

  Trap popTrap()
  {
    Trap t = trap;
    trap = null;
    return t;
  }


}
