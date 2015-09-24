package model;


public interface Combustible
{
  boolean isCombustible ();

  void setCombustedState (CombustedState s);

  CombustedState getCombustedState();

  boolean setCurrentTime(int time);

  boolean incrementCurrentTime();

  int getCurrentTime();

  enum CombustedState
  {
      IGNITED, BURNED, NONE
  }
}
