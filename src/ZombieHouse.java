/**
 * Entrance of the program.
 */


import controller.GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ZombieHouse
{
  private GameEngine game = new GameEngine();
  private static int width = 1920, height = 1080;
  protected JFrame frame = new JFrame("Zombie House");
  protected JPanel jPanel = new JPanel();
  private boolean running = false;
  private final int FPS = 60;
  private final long targetTime = 1000 / FPS;

  private AffineTransform oldXForm;
  private BufferedImage screen;
  private Graphics2D g;

  public ZombieHouse ()
  {
    init();
  }

  public boolean isRunning ()
  {
    return running;
  }

  public void setRunning (boolean running)
  {
    this.running = running;
  }

  public void run ()
  {
    long start;
    long elapsed = 0;
    long wait;

    while (true)
    {
      start = System.nanoTime();

      game.update(elapsed / 1000000000.0f);

      render();
      drawToPanel();

      elapsed = System.nanoTime() - start;
      wait = targetTime - elapsed / 1000000;

      if (wait < 0) wait = 5;

      try
      {
        Thread.sleep(wait);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
  }

  public static void main (String[] args)
  {
    new ZombieHouse().run();
  }

  protected void update (double deltaTime)
  {
    game.update((float) deltaTime);
  }

  private void init ()
  {
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.addKeyListener(game);
    //    frame.addMouseListener(game);
    //    frame.addMouseMotionListener(game);

    jPanel.setBackground(Color.black);
    jPanel.setIgnoreRepaint(true);
    jPanel.setPreferredSize(new Dimension(1920, 1080));
    jPanel.addComponentListener(new ComponentAdapter()
    {
      public void componentResized (ComponentEvent arg0)
      {
        width = jPanel.getWidth();
        height = jPanel.getHeight();
        game.setViewPort(new Rectangle(jPanel.getVisibleRect()));
        System.out.println("[INFO] Resized to: " + width + " " + height);
      }
    });
    /**
     large one image to draw level
     smaller to draw
     */

    frame.getContentPane().add(jPanel, BorderLayout.CENTER);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);

    screen = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    g = (Graphics2D) screen.getGraphics();
    g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
                       RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                       RenderingHints.VALUE_ANTIALIAS_ON);

    oldXForm = g.getTransform();
  }

  public void render ()
  {
    g.setTransform(oldXForm);
    g.clearRect(0, 0, width, height);
    game.render(g);
  }

  public void drawToPanel ()
  {
    Graphics g2 = jPanel.getGraphics();
    g2.drawImage(screen, 0, 0, null);
    g2.dispose();
  }
}
