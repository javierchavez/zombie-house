package view;

import java.awt.*;

public interface Renderer
{
  int TILE_WIDTH = 80;
  int TILE_HEIGHT = 80;
  float displayScaleFactor = 1.5f;
  int MAX_TILE_SIZE = 80;
  int MAX_SCREEN_WIDTH = 1920;
  int MAX_SCREEN_HEIGHT = 1080;


  void render(Graphics g);
}
