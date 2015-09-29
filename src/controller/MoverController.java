package controller;

/**
 * @author Javier Chavez
 * @author Alex Baker
 * @author Erin Sosebee
 * <p>
 * Date September 28, 2015
 * CS 351
 * Zombie House
 * <p>
 * This is the interface for moveable objects
 */

import model.*;

public interface MoverController<T extends Mover> extends GameController
{


  boolean checkCollision (Move moveToCheck);

  /**
   * If 'R' is pressed, player's speed is updated to 2.0, making them two times faster.
   */
  void run ();

  /**
   * When 'R' is released, player's speed drops back down to 1.0, the default speed.
   */
  void walk ();

  /**
   * Set the character to idle
   */
  void idle ();

  /**
   * If two incompatible directions are pressed; or stop moving the character
   */
  void stopMoving ();

  /**
   * If 'W' or up arrow is pressed, orient the character
   * facing up.
   */
  void moveUp ();

  /**
   * If 'S' or down arrow is pressed, orient the character
   * facing down.
   */
  void moveDown ();

  /**
   * If 'A' or left arrow is pressed, orient the character
   * facing left
   */
  void moveLeft ();

  /**
   * If 'D' or right arrow is pressed, orient the character
   * facing right
   */
  void moveRight ();

  /**
   * If 'up' and 'right' are pressed at the same time, orient the character
   * up and right
   */
  void moveUpRight ();

  /**
   * If 'up' and 'left' are pressed at the same time, orient the character
   * up and left
   */
  void moveUpLeft ();

  /**
   * If 'down' and 'right' are pressed at the same time, orient the character
   * down and right
   */
  void moveDownRight ();

  /**
   * If 'down' and 'left' are pressed at the same time, orient the character
   * down and left.
   */
  void moveDownLeft ();

  /**
   * If the player is not moving.. Due to stamina
   */
  void resting ();
}
