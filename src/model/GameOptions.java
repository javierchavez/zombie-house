package model;

/**
 * @author Javier Chavez
 * @author Alex Baker
 * @author Erin Sosebee
 * <p>
 * Date September 28, 2015
 * CS 351
 * Zombie House
 * <p>
 * Defines different options and states for the game
 */

public class GameOptions
{
  private static House house;
  private static GAME_STATE state = GAME_STATE.LEVEL1;
  private static GAME_STATUS status = GAME_STATUS.STARTUP;

  public GameOptions (House house)
  {
    GameOptions.house = house;
  }

  public GAME_STATE getState ()
  {
    return state;
  }

  public void setState (GAME_STATE state)
  {
    GameOptions.state = state;
  }

  public GAME_STATUS getStatus ()
  {
    return status;
  }

  public void setStatus (GAME_STATUS status)
  {
    GameOptions.status = status;
  }

  public GAME_STATE getNextLevel (GAME_STATE level)
  {
    GAME_STATE nextLevel;
    switch (level)
    {
      case LEVEL1:
        nextLevel = GAME_STATE.LEVEL2;
        break;
      case LEVEL2:
        nextLevel = GAME_STATE.LEVEL3;
        break;
      case LEVEL3:
        nextLevel = GAME_STATE.LEVEL4;
        break;
      case LEVEL4:
        nextLevel = GAME_STATE.LEVEL5;
        break;
      default:
        nextLevel = GAME_STATE.LEVEL1;
    }
    return nextLevel;
  }

  public String getMessage ()
  {
    String message = "";
    Player player = house.getPlayer();
    if (status == GAME_STATUS.LOADING)
    {
      message = "Loading...";
    }
    if (player.getState() == Player.PlayerState.WINNER)
    {
      message = "Level Complete";
    }
    else if (player.getState() == Player.PlayerState.DEAD)
    {
      message = "Game Over";
    }
    else if (status == GAME_STATUS.PAUSED)
    {
      message = "Paused";
    }
    return message;
  }

  public enum GAME_STATUS
  {
    PLAYING, PAUSED, LOADING, STARTUP
  }

  public enum GAME_STATE
  {
    PLAY, RESTART, EXIT, SETTINGS, GENERATE, LEVEL1, LEVEL2, LEVEL3, LEVEL4, LEVEL5
  }

}
