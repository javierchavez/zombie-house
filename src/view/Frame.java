package view;

import java.awt.image.BufferedImage;

public class Frame
{

  private BufferedImage frame;

  public Frame (BufferedImage frame, int duration)
  {
    this.frame = frame;

  }

  public BufferedImage getFrame ()
  {
    return frame;
  }

  //  public void setFrame (BufferedImage frame)
  //  {
  //    this.frame = frame;
  //  }
  //
  //  public int getDuration ()
  //  {
  //    return duration;
  //  }
  //
  //  public void setDuration (int duration)
  //  {
  //    this.duration = duration;
  //  }

}