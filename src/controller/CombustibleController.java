package controller;


import model.Combustible;

import java.awt.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This is a Singleton! So treat it as such.
 *
 */
public class CombustibleController implements GameController
{
  private ConcurrentLinkedQueue<Combustible> combustibles = new ConcurrentLinkedQueue<>();
  private static CombustibleController instance = null;
  private boolean running = false;

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
    if (!running && !combustibles.isEmpty())
    {
      int len = combustibles.size();
      int i = 0;
      running = true; // just in case.

      for (Combustible c = combustibles.poll(); c != null; c = combustibles.poll())
      {
        if (c.incrementCurrentTime())
        {
          combustibles.add(c);
        }
        else
        {
          combustibles.remove(c);
        }
        // counter to force only iterating through the loop once.
        ++i;
        if (i == len-1)
        {
          running = false;
          return;
        }
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
    combustibles.add(c);
  }
}
