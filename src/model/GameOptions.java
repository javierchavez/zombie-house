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
    PLAY, RESTART, EXIT, LEVEL1, LEVEL2
  }

}
