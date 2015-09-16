package view;

import model.*;
import model.Character;

import java.awt.*;
import java.awt.image.BufferedImage;


public class CharacterRenderer extends Renderer
{
  private final Character character;
  private final int h;
  private final int w;
  Sound sound;
  CharacterSprite characterSprite = new CharacterSprite();


  private BufferedImage[] walkingLeft = {
          CharacterSprite.getSprite(0, 1),
          CharacterSprite.getSprite(2, 1)};

  private BufferedImage[] walkingRight = {
          CharacterSprite.getSprite(0, 1),
          CharacterSprite.getSprite(1, 1),
          CharacterSprite.getSprite(2, 1)};


  private Animation walkLeft = new Animation(walkingLeft, 1);
  private Animation walkRight = new Animation(walkingRight, 1);

  // check direction... need a AnimationFactoryClass
  private Animation animation = walkRight;

  public CharacterRenderer (Character character, int w, int h)
  {
    this.w = w;
    this.h = h;
    this.character = character;
  }

  @Override
  public void render (Graphics g)
  {
    Graphics2D g2 = (Graphics2D) g;

    if (character.getSpeed() > 0)
    {
      animation.start();
      animation.update();
    }
    else
    {
      animation.stop();
      animation.reset();
    }

    ////////// this is shit code.///////
    // this needs to be abstracted out into a view or camera class
    // it needs to take into account for scale and size of clipping
    double width = g2.getClipBounds().getWidth();
    double height = g2.getClipBounds().getHeight();

    int tileW = (int) (width / w);
    int tileH = (int) (height / h);



    float x = character.getCurrentX();
    float y = character.getCurrentY();

    // this needs to be in the converter class
    g.drawImage(animation.getSprite(), (int)((x * tileW)+x),
                (int)((y * tileH) +y), null);

    g2.setColor(Color.white);
    g2.drawRect((int) width / 2, 39, (int) (width / 4)+1, 21);
    g2.setColor(Color.RED);
    g2.fillRect((int) (width / 2), 40,
                (int) ((character.getStamina() / 5) * (width/4)), 20);

  }


}
