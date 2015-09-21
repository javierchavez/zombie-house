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
  private static int
          width = 1920,
          height = 1080;
  protected boolean running;
  protected int fps = 60;
  protected JFrame frame = new JFrame("Zombie House");
  protected JPanel jPanel = new JPanel();
  private BufferedImage screen;

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
    long delta = 0l;
    screen = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB);

    Graphics2D g = (Graphics2D) screen.getGraphics();
    Graphics jFrameGraphics = jPanel.getGraphics();
    AffineTransform oldXForm = g.getTransform();
    while (true)
    {
      long lastTime = System.nanoTime();

      g.setTransform(oldXForm);
      g.setColor(Color.black);
      g.clearRect(0, 0, 1920, 1080);

      // DEBUG sprites
       // g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));

      g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
                         RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                         RenderingHints.VALUE_ANTIALIAS_ON);

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

  }
}
