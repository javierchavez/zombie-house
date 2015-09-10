/**
 * Entrance of the program.
 */


import controller.GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ZombieHouse extends JFrame implements Runnable
{
  private GameEngine game = new GameEngine();
  private JPanel gamePanel = new JPanel(true);
  private BufferedImage screen;
  private Graphics panelGraphics;
  private Graphics volatileGraphics;


  public void start() {
    new Thread(this).start();
  }

  public static void main (String[] args)
  {
    new ZombieHouse().start();
  }

  @Override
  public void run ()
  {
    init();
    long delta = 0l;

    while (true)
    {
      long lastTime = System.nanoTime();

      volatileGraphics.setColor(Color.black);
      volatileGraphics.fillRect(0, 0, 1920, 1080);

      game.render(volatileGraphics);

      panelGraphics.drawImage(screen, 0, 0, this);

      // convert milliseconds to seconds and update
      game.update((float) (delta / 1000000000.0));

      // set to 60fps
      delta = System.nanoTime() - lastTime;
      if (delta < 10000000L) {
        try {
          Thread.sleep((10000000L - delta) / 1000000L);
        } catch (Exception e) {
        }
      }

      delta = System.nanoTime() - lastTime;

      if (!isActive()) {
        return;
      }
    }
  }

  @Override
  protected void processEvent (AWTEvent e)
  {
    game.processEvent(e);
  }

  private void init(){
    // create a jPanel and add it to this frame
    setVisible(true);
    setSize(1920, 1080);
    setLocationRelativeTo(null); //center on screen
    add(gamePanel);

    // image
    screen = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_ARGB);
    // image that is passed so others can render
    volatileGraphics = screen.getGraphics();
    // where the graphics will be displayed
    panelGraphics = gamePanel.getGraphics();
  }
}
