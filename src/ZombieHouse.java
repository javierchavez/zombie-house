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

    Graphics g = screen.getGraphics();
    Graphics jFrameGraphics = getGraphics();

    while (true)
    {
      long lastTime = System.nanoTime();

      g.setColor(Color.black);
      g.fillRect(0, 0, 1920, 1080);


      // convert milliseconds to seconds and update
      game.update((float) (delta / 1000000000.0));

      game.render(g);


      jFrameGraphics.drawImage(screen, 0, 0, null);

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


  private void init(){
    setVisible(true);
    setSize(1920, 1080);
    setLocationRelativeTo(null);
    addKeyListener(game);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }
}
