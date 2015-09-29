package io;

/**
 * @author Javier Chavez
 * @author Alex Baker
 * @author Erin Sosebee
 *
 * Date September 28, 2015
 * CS 351
 * Zombie House
 *
 * This is the interface for Combustible objects
 */


/**
 * This is the class that we can use to persist data about game state
 */
public class Serializer
{
  private static Serializer ourInstance = new Serializer();

  private Serializer ()
  {
  }

  public static Serializer getInstance ()
  {
    return ourInstance;
  }
}
