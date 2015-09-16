/**
 * Entrance of the program.
 */


import controller.GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ZombieHouse extends JFrame implements Runnable
{
  private GameEngine game = new GameEngine();
  private BufferedImage screen;

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
    screen = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB);

    Graphics2D g = (Graphics2D) screen.getGraphics();
    Graphics2D jFrameGraphics = (Graphics2D) getGraphics();

    while (true)
    {
      long lastTime = System.nanoTime();

      g.setColor(Color.black);
      g.fillRect(0, 0, 1920, 1080);
      g.setClip(0, 0, getWidth(), getHeight());

      game.update(delta / 1000000000.0f);
      game.render(g);


      jFrameGraphics.drawImage(screen, 0, 0, null);

      delta = System.nanoTime() - lastTime;
      if (delta < 20000000L)
      {
        try
        {
          Thread.sleep((20000000L - delta) / 1000000L);
        }
        catch (Exception e) { }
      }
    }
  }


  private void init(){
    setVisible(true);
    setSize(new Dimension(1920, 1080));
    setMinimumSize(new Dimension(480, 320));
    setMaximumSize(new Dimension(1920, 1080));
    setSize(480, 320);
    System.out.println(getBounds());
    setLocationRelativeTo(null);
    addKeyListener(game);
    addMouseListener(game);
    addMouseMotionListener(game);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }
}
