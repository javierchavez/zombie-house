package model;

/**
 * @author Javier Chavez
 * @author Alex Baker
 * @author Erin Sosebee
 * <p>
 * Date September 28, 2015
 * CS 351
 * Zombie House
 * <p>
 * Used to describe any object that can move
 */


public interface Mover
{

  /**
   * Get the x current location of object
   *
   * @return column
   */
  float getCurrentX ();

  /**
   * Get the y current location of the object
   *
   * @return row
   */
  float getCurrentY ();

  /**
   * Get the min speed that object can go. this is a multiplier of time
   * i.e. this will be multiplied by the amount of time the user held a
   * button down. This is a a part of magnitude.
   *
   * @return tiles
   */
  float getSpeed ();

  /**
   * Get the current stamina of the object. Stamina decreases when the
   * object runs, and replenishes once the object stops running.
   *
   * @return stamina
   */
  float getStamina ();

  /**
   * Get the rotation of the object. N = 90, S = 270, W = 180, E = 0
   * x += speed * sin(rotation);
   * y += speed * cos(rotation);
   *
   * @return degree of movement
   */
  float getRotation ();

  /**
   * Move object from current location to new location
   *
   * @param x column
   * @param y row
   */
  void move (float x, float y);

  /**
   * Set the speed at which to move the object
   *
   * @param speed tile
   * @return new speed
   */
  float setSpeed (float speed);

  /**
   * Set the stamina of the object.
   *
   * @param stamina amount of energy a character holds
   * @return stamina that currently available
   */
  float setStamina (float stamina);

  /**
   * Set the rotation of the object. N = 90, S = 270, W = 180, E = 0
   *
   * @param rotation degree of direction
   * @return new rotation degree
   */
  float setRotation (float rotation);

}
