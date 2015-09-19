package controller;


import model.*;

public interface MoverController <T extends Mover> extends GameController
{



  void checkCollision (float deltaTime);


  /**
   * If 'R' is pressed, player's speed is updated to 2.0, making them two times faster.
   */
  void run ();
  /**
   * When 'R' is released, player's speed drops back down to 1.0, the default speed.
   */
  void walk ();
  /**
   * When the  isn't moving at all
   */
  void idle ();
  /**
   * If two incompatible directions are pressed;
   */
  void stopMoving();
  /**
   * If 'P' is pressed.
   */
//  void trapInteraction ();

  /**
   * If 'W' or up arrow is pressed.
   */
  void moveUp ();
  /**
   * If 'S' or down arrow is pressed.
   */
  void moveDown ();
  /**
   * If 'A' or left arrow is pressed.
   */
  void moveLeft ();
  /**
   * If 'D' or right arrow is pressed.
   */

  void moveRight ();

  void moveUpRight();

  void moveUpLeft();

  void moveDownRight();

  void moveDownLeft();

  void resting();
}
