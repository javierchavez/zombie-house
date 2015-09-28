package model;



public class GameOptions
{
  private static House house;
  private static GAME_STATE state = GAME_STATE.PLAY;
  private static GAME_STATUS status = GAME_STATUS.PAUSED;

  public GameOptions(House house)
  {
    this.house = house;
  }

  public void setState (GAME_STATE state)
  {
    GameOptions.state = state;
  }

  public GAME_STATE getState ()
  {
    return state;
  }

  public void setStatus(GAME_STATUS status)
  {
    this.status = status;
  }

  public GAME_STATUS getStatus()
  {
    return status;
  }

  public enum GAME_STATUS {
    PLAYING, PAUSED, LOADING
  }

  public enum GAME_STATE {
    PLAY, RESTART, EXIT, LEVEL1, LEVEL2, LEVEL3, LEVEL4, LEVEL5
  }

  public GAME_STATE getNextLevel(GAME_STATE level)
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

  public String getMessage()
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

}
