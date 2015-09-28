package controller;


import java.awt.*;

public interface GameController
{
  /**
   * The game loop
   *
   * @param deltaTime the amount of time that it took between updates
   */
  void update (float deltaTime);

  /**
   * Method to pass a graphics object for rendering images to screen
   *
   * @param graphics graphics object that will be drawn on the screen
   */
  void render (Graphics2D graphics);
}
