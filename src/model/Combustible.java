package model;


public interface Combustible
{
  /**
   * Get the status of Combustibility
   * @return true if combustible
   */
  boolean isCombustible ();

  /**
   * Set the object combustible state
   * @param s see CombustibleState
   */
  void setCombustedState (CombustedState s);

  /**
   * Get the State
   * @return see CombustibleState
   */
  CombustedState getCombustedState();

  /**
   * Set current length of time the object has been in current state
   * @param time seconds time
   * @return True if Ignited false if Burned
   */
  boolean setCurrentTime(int time);

  /**
   * Increment the time
   * @return True if Ignited false if Burned
   */
  boolean incrementCurrentTime();

  /**
   * Get the Current length of time the object has been in state
   * @return current time in seconds
   */
  int getCurrentTime();

  /**
   * States of Combustion
   */
  enum CombustedState
  {
      IGNITED, BURNED, NONE
  }
}
