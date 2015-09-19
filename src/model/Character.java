package model;


import java.awt.geom.Rectangle2D;

public class Character implements Mover, Object2D
{

  protected float
          stamina,
          hearing,
          sight,
          speed,
          regen,
          x, y,
          width, height,
          smell,
          rotation;

  public Character()
  {
    height = width = 60;
    rotation = 0;
    hearing = 10;
    stamina = 5;
    sight = 5;
    speed = 1;
    regen = .2f;
    x = 0f;
    y = 0f;
    smell = 7f;
  }

  @Override
  public float getCurrentX ()
  {
    return x;
  }

  @Override
  public float getCurrentY ()
  {
    return y;
  }

  @Override
  public float getSpeed ()
  {
    return speed;
  }

  @Override
  public float getRotation ()
  {
    return rotation;
  }

  @Override
  public float getStamina()
  {
    return stamina;
  }

  @Override
  public void move (float x, float y)
  {
    this.x = x;
    this.y = y;
    //System.out.println("Character at x=" + this.x + ", y=" + this.y + ")");
  }

  @Override
  public float setSpeed (float speed)
  {
    this.speed = speed;
    return speed;
  }

  @Override
  public float setStamina(float stamina)
  {
    this.stamina = stamina;
    return stamina;
  }

  @Override
  public float setRotation (float rotation)
  {
    this.rotation = rotation;
    return rotation;
  }

  /**
   * Euclidean distance of tiles
   * @return
   */
  public float getSight ()
  {
    return sight;
  }

  /**
   * Euclidean distance of tiles
   * @return
   */
  public float getHearing ()
  {
    return hearing;
  }


  public float getSmell()
  {
    return smell;
  }

  @Override
  public float getX ()
  {
    return x;
  }

  @Override
  public float getY ()
  {
    return y;
  }

  @Override
  public float getWidth ()
  {
    return 60;
  }

  @Override
  public float getHeight ()
  {
    return 60;
  }

  @Override
  public void setWidth (float width)
  {
    this.width = width;
  }

  @Override
  public void setHeight (float height)
  {
    this.height = height;
  }

  @Override
  public Rectangle2D getBoundingRectangle ()
  {
    return new Rectangle2D.Float(getCurrentX(),
                                 getCurrentY(),
                                 getWidth(),
                                 getHeight());
  }

  @Override
  public boolean intersects (Rectangle2D other)
  {
    return other.intersects(this.getBoundingRectangle());
  }

  @Override
  public boolean isOutOfBounds ()
  {
    return false;
  }
}
