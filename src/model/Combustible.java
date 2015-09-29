package model;

/**
 * @author Javier Chavez
 * @author Alex Baker
 * @author Erin Sosebee
 *         <p>
 *         Date September 28, 2015
 *         CS 351
 *         Zombie House
 *         <p>
 *         This is the interface for Combustible objects
 */

public interface Combustible
{
  /**
   * Get the status of Combustibility
   *
   * @return true if combustible
   */
  boolean isCombustible ();

  /**
   * Get the State
   *
   * @return see CombustibleState
   */
  CombustedState getCombustedState ();

  /**
   * Set the object combustible state
   *
   * @param s see CombustibleState
   */
  void setCombustedState (CombustedState s);

  /**
   * Set current length of time the object has been in current state
   *
   * @param time seconds time
   * @return True if Ignited false if Burned
   */
  boolean setCurrentTime (int time);

  /**
   * Increment the time
   *
   * @return True if Ignited false if Burned
   */
  boolean incrementCurrentTime ();

  /**
   * Get the Current length of time the object has been in state
   *
   * @return current time in seconds
   */
  int getCurrentTime ();

  /**
   * States of Combustion
   */
  enum CombustedState
  {
    IGNITED, BURNED, NONE
  }
}
