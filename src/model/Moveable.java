package model;


public interface Moveable
{
  float x = 0;
  float y = 0;
  float speed = 2f;
  float rotation = 0f;

  float getX();

  float getY();

  float getSpeed();

  float getRotation();

  float setX(float x);

  float setY(float y);

  float setSpeed(float speed);

  float setRoatation(float rotation);

}
