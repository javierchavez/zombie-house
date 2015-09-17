/**
 * Entrance of the program.
 */


import controller.GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class ZombieHouse
{
  private GameEngine game = new GameEngine();
  private static int width = 1920, height = 1080;
//  protected BufferStrategy strategy;
  private BufferedImage screen;
  protected boolean running;
  protected int fps = 60;
  protected JFrame frame = new JFrame("Zombie House");
  final JPanel canvas = new JPanel();
  private JScrollPane scrollPane;

  public ZombieHouse ()
  {
    init();
  }

  public static int getWidth ()
  {
    return Math.max(0, width);
  }

  public static int getHeight ()
  {
    return Math.max(0, height);
  }

  public static void main (String[] args)
  {
    new ZombieHouse().run();
  }


  public void run ()
  {
    int frames = 0;
    long lastTime, lastSec;
    lastTime = lastSec = System.nanoTime();
    running = true;
    screen = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB);

    Graphics2D g = (Graphics2D) screen.getGraphics();
    Graphics2D canvasGraphics = (Graphics2D) canvas.getGraphics();

    while (running)
    {
      long deltaTime = System.nanoTime() - lastTime;
      lastTime += deltaTime;

      update(deltaTime / 1e9);

      g.setColor(Color.black);
      g.fillRect(0, 0, 1920, 1080);

      game.render(g);
      canvasGraphics.drawImage(screen, 0, 0, null);

      if (System.nanoTime() - lastSec >= 1e9)
      {
        fps = frames;
        frames = 0;
        lastSec += 1e9;
      }
      do
      {
        Thread.yield();
      } while (System.nanoTime() - lastTime < 1e9 / 60);
    }
  }

  protected void update (double deltaTime)
  {
    game.update((float) deltaTime);
  }


//  protected void render (Graphics2D g)
//  {
//    game.render(g);
//
//
//    jFrameGraphics.drawImage(screen, 0, 0, null);
//  }


  private void init ()
  {
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.addKeyListener(game);
    frame.addMouseListener(game);
    frame.addMouseMotionListener(game);


//    canvas.setIgnoreRepaint(true);
    canvas.setPreferredSize(new Dimension(width, height));
    canvas.addComponentListener(new ComponentAdapter()
    { //on resize...
      public void componentResized (ComponentEvent arg0)
      {
        width = canvas.getWidth();
        height = canvas.getHeight();
        game.setViewPort(new Rectangle(width, height));
      }
    });
    scrollPane = new JScrollPane(canvas,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                       JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

//    frame.getContentPane().add(scrollPane);
//    frame.add(canvas);

    canvas.setBackground(Color.black);
    frame.add(scrollPane, BorderLayout.CENTER);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
