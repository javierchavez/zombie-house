package model;



public class GameOptions
{
  private static GAME_STATE state = GAME_STATE.PLAY;

  public void setState (GAME_STATE state)
  {
    GameOptions.state = state;
  }

  public GAME_STATE getState ()
  {
    return state;
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

}
