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
 * Defines anything that can kill a player
 */

/**
 * Anything that is deadly
 */
public interface Deadly
{

  float getTakePoints ();

  void setTakePoints (float points);
}
