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
 * This is the interface for Combustible objects
 */

/**
 * Anything that is deadly
 */
public interface Deadly
{

  float getTakePoints ();

  void setTakePoints (float points);
}
