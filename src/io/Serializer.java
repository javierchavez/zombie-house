package io;

/**
 * This is the class that we can use to persist data about game state
 */
public class Serializer
{
  private static Serializer ourInstance = new Serializer();

  public static Serializer getInstance ()
  {
    return ourInstance;
  }

  private Serializer ()
  {
  }
}
