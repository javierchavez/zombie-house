package controller;


import model.Combustible;

import java.awt.*;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * This is a Singleton! So treat it as such.
 *
 */
public class CombustibleController implements GameController
{
  private ConcurrentLinkedDeque<Combustible> combustibles = new ConcurrentLinkedDeque<>();
//  private ArrayBlockingQueue<Combustible> combustibles = new
// ArrayBlockingQueue<>(25); // best bet but hard to tune

  private static CombustibleController instance = null;

  protected CombustibleController () { }

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
          combustibles.add(c);
        }
        // counter to force only iterating through the loop once.
        if (i == len-1)
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
    // add to head of LL
    combustibles.push(c);
  }
}
