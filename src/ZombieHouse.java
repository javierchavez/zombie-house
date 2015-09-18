/**
 * Entrance of the program.
 */


import controller.GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

public class ZombieHouse
{
  private GameEngine game = new GameEngine();
  private static int
          width = 1920,
          height = 1080;
  protected boolean running;
  protected int fps = 60;
//  private JScrollPane scrollPane;
  protected JFrame frame = new JFrame("Zombie House");
  protected JPanel jPanel = new JPanel();

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
    int frames = 0;
    long lastTime, lastSecond;
    lastTime = lastSecond = System.nanoTime();
    running = true;
    BufferedImage screen = new BufferedImage(1920,
                                             1080,
                                             BufferedImage.TYPE_INT_RGB);

    Graphics2D g = (Graphics2D) screen.getGraphics();
    Graphics2D canvasGraphics = (Graphics2D) jPanel.getGraphics();

    while (running)
    {
      long deltaTime = System.nanoTime() - lastTime;
      lastTime += deltaTime;

      update(deltaTime / 1e9);

      g.setColor(Color.black);
      g.clearRect(0, 0, 1920, 1080);

      game.render(g);
      canvasGraphics.drawImage(screen, 0, 0, null);

      if (System.nanoTime() - lastSecond >= 1e9)
      {
        fps = frames;
        frames = 0;
        lastSecond += 1e9;
      }
      
      do
      {
        Thread.yield();
        // tight loop
        // better 
        // swing timer sleep for n seconds
        // thread sleep n time

      } while (System.nanoTime() - lastTime < 1e9 / 60);
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
    // frame.addMouseListener(game);
    // frame.addMouseMotionListener(game);

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

    jPanel.setBackground(Color.black);
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
