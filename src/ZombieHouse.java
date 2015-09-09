/**
 * Entrance of the program.
 */


import controller.ZombieHouseGame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ZombieHouse extends JFrame implements Runnable
{
  private ZombieHouseGame game = new ZombieHouseGame();
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

    while (true)
    {
      volatileGraphics.setColor(Color.black);
      volatileGraphics.fillRect(0, 0, 1920, 1080);

      game.render(volatileGraphics);

      panelGraphics.drawImage(screen, 0, 0, this);


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
