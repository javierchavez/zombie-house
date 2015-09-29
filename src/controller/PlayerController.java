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
 * This is the interface for Combustible objects
 */

import common.CharacterAttributes;
import common.Speed;
import common.Duration;
import model.*;

public class PlayerController extends AbstractCharacterController<Player>
{
  Tile playerTile;
  private int trapSetTimer = 0;
  private boolean pKeyPressed = false;
  private boolean DEBUG = false;

  public PlayerController (House house)
  {
    super(house, house.getPlayer());
  }

  @Override
  public void update (float deltaTime)
  {
    float playerSpeed;
    mover = house.getPlayer();
    playerTile = house.getCharacterTile(mover);

    if (playerTile.getCombustedState() == Combustible.CombustedState.IGNITED)
    {
      mover.setState(Player.PlayerState.DEAD);
    }
    else if (house.isZombieTile(playerTile))
    {
      mover.setState(Player.PlayerState.DEAD);
    }

    float direction = mover.getRotation();
    float stamina = mover.getStamina();
    float x = mover.getCurrentX();
    float y = mover.getCurrentY();

    if (stamina == 0)
    {
      isMoving = false;
      mover.setSpeed(Speed.IDLE);
    }

    if (!isMoving)
    {
      // Stamina regeneration
      if (stamina < CharacterAttributes.MAX_STAMINA)
      {
        stamina += (CharacterAttributes.STAMINA_REGEN * deltaTime) + 0.01; // Stamina regenerates faster if mover is not moving
        if (stamina > CharacterAttributes.MAX_STAMINA) stamina = CharacterAttributes.MAX_STAMINA;
        mover.setStamina(stamina);
      }

      // Trap interaction
      if (pKeyPressed) trapInteraction();
    }

    if (isMoving)
    {
      if (running)
      {
        playerSpeed = Speed.RUN;
        if (stamina > 0)
        {
          stamina -= deltaTime;
          if (stamina < 0) stamina = 0;
          if (stamina == 0)
          {
            running = false;
          }
          mover.setStamina(stamina);
        }
      }
      else // if player is not running
      {
        playerSpeed = Speed.WALK;
        if (stamina < CharacterAttributes.MAX_STAMINA)
        {
          stamina += CharacterAttributes.STAMINA_REGEN * deltaTime;
          if (stamina > CharacterAttributes.MAX_STAMINA) stamina = 5;
          mover.setStamina(stamina);
        }
      }

      mover.setSpeed(playerSpeed * deltaTime);
      mover.setRotation(direction);

      if (moveUp || moveDown) y = (float) (y + mover.getSpeed() * Math.sin(Math.toRadians(direction)));
      if (moveLeft || moveRight) x = (float) (x + mover.getSpeed() * Math.cos(Math.toRadians(direction)));

      checkCollision(new Move(x,y, (int) direction));
      isMoving = false;
    }
  }

  /**
   * If 'P' is pressed.
   */
  public void trapKeyPressed()
  {
    pKeyPressed = true;
    trapSetTimer++;
  }

  public void trapKeyReleased()
  {
    pKeyPressed = false;
    trapSetTimer = 0;
  }

  /**
   * If 'P' key is held down for long enough for player to pick up/set trap.
   */
  public void trapInteraction()
  {
    Tile tile = house.getCharacterTile(house.getPlayer());
    int TRAP_SET_TIME = (int) (60 * Duration.PICKIP_TIME);
    if (house.isTrap(tile))
    {
//      if (DEBUG) System.out.println("PICKING UP TRAP");
      if (trapSetTimer % TRAP_SET_TIME == 0) mover.pickupTrap(tile);
    }
    else
    {
//      if (DEBUG) System.out.println("SETTING TRAP");
      int numTraps = mover.trapsAvailable();
      if (trapSetTimer % TRAP_SET_TIME == 0)
      {
        if (numTraps > 0) mover.dropTrap(tile);
      }
    }
  }

  @Override
  public boolean checkCollision(Move moveToCheck)
  {
    if (house.getCharacterTile(mover) == house.getExit())
    {
      mover.setState(Player.PlayerState.WINNER);
    }

    if (super.checkCollision(moveToCheck))
    {
      mover.setSpeed(0);
      return true;
    }
    else
    {
      mover.move(moveToCheck.col, moveToCheck.row);
    }

    return false;
  }
}
