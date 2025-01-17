package controller;

/**
 * @author Javier Chavez
 * @author Alex Baker
 * @author Erin Sosebee
 * <p>
 * Date September 28, 2015
 * CS 351
 * Zombie House
 * <p>
 * This is the class for igniting combustible objects
 */

import model.Combustible;

import java.awt.*;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * This is a Singleton! So treat it as such.
 */
public class CombustibleController implements GameController
{
  private static CombustibleController instance = null;
  private final ConcurrentLinkedDeque<Combustible> combustibles = new ConcurrentLinkedDeque<>();

  protected CombustibleController ()
  {
  }

  public static CombustibleController getInstance ()
  {
    if (instance == null)
    {
      instance = new CombustibleController();
    }
    return instance;
  }


  @Override
  public void update (float deltaTime)
  {
    if (!combustibles.isEmpty())
    {
      int len = combustibles.size();
      int i = 0;

      for (Combustible c = combustibles.poll(); c != null; c = combustibles.poll())
      {
        if (c.incrementCurrentTime())
        {
          // if obj is still burning add tail node
          combustibles.add(c);
        }
        // counter to force only iterating through the loop once.
        if (i == len - 1)
        {
          return;
        }
        ++i;
      }
    }
  }

  @Override
  public void render (Graphics2D graphics)
  {

  }

  public void addCombustible (Combustible c)
  {
    if (c.getCombustedState() != Combustible.CombustedState.IGNITED)
    {
      c.setCombustedState(Combustible.CombustedState.IGNITED);
    }
    // add to head
    combustibles.push(c);
  }
}
