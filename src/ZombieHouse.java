/**
 * @author Javier Chavez
 * @author Alex Baker
 * @author Erin Sosebee
 * <p>
 * Date September 28, 2015
 * CS 351
 * Zombie House
 * <p>
 * Main enterence of the game
 */

import common.Size;
import controller.GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ZombieHouse
{
  private static int width = Size.SCREEN_WIDTH, height = Size.SCREEN_HEIGHT;
  protected final JFrame frame = new JFrame("Zombie House");
  protected final JPanel jPanel = new JPanel();
  private final GameEngine game = new GameEngine();
  private boolean running = false;
  private AffineTransform oldXForm;
  private BufferedImage screen;
  private Graphics2D g;

  public ZombieHouse ()
  {
    init();
  }

  public static void main (String[] args)
  {
    new ZombieHouse().run();
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
    new Thread(new Runnable()
    {

      @Override
      public void run ()
      {
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        Thread.currentThread().setName("GameThread");
        long delta = 0l;


        while (true)
        {
          long lastTime = System.nanoTime();

          game.update(delta / 1000000000.0f);

          render();
          drawToPanel();

          delta = System.nanoTime() - lastTime;
          if (delta < 20000000L)
          {
            try
            {
              Thread.sleep((20000000L - delta) / 1000000L);
            }
            catch (Exception e)
            {
            }
          }

        }
      }
    }).start();
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
    jPanel.setPreferredSize(
            new Dimension(Size.SCREEN_WIDTH, Size.SCREEN_HEIGHT));
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
