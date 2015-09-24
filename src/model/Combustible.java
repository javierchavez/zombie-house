package model;


public interface Combustible
{
  boolean isCombustible ();

  void setCombustedState (CombustedState s);

  CombustedState getCombustedState();

  enum CombustedState
  {
      IGNITED, BURNED, NONE
  }
}
