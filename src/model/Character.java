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
 * This is the interface for Combustible objects
 */

import common.CharacterAttributes;
import common.Direction;
import common.Duration;
import common.Speed;

import java.awt.geom.Rectangle2D;

/**
 * Generic Character in the game. (Includes Player)
 */
public class Character implements Mover, Object2D, Combustible, Sound
{

  // attributes about a character (measure is in tiles)
  protected float stamina, hearing, sight, speed, regen, x, y, width, height, smell, rotation;
  AudioChannel channel = AudioChannel.STEREO;
  private CombustedState combustedState = CombustedState.NONE;
  private float volume;
  private int time = 0;

  public Character ()
  {
    height = .80f;
    width = .5f;
    rotation = 0;
    hearing = CharacterAttributes.HEARING;
    stamina = CharacterAttributes.MAX_STAMINA;
    sight = CharacterAttributes.SIGHT;
    speed = Speed.WALK;
    regen = CharacterAttributes.STAMINA_REGEN;
    x = 0f;
    y = 0f;
    smell = CharacterAttributes.SMELL;
  }

  @Override
  public float getCurrentX ()
  {
    return x;
  }

  @Override
  public float getCurrentY ()
  {
    return y;
  }

  @Override
  public float getSpeed ()
  {
    return speed;
  }

  @Override
  public float getStamina ()
  {
    return stamina;
  }

  @Override
  public float getRotation ()
  {
    return rotation;
  }

  @Override
  public void move (float x, float y)
  {
    this.x = x;
    this.y = y;
    //System.out.println("Character at x=" + this.x + ", y=" + this.y + ")");
  }

  @Override
  public float setSpeed (float speed)
  {
    this.speed = speed;
    return speed;
  }

  @Override
  public float setStamina (float stamina)
  {
    this.stamina = stamina;
    return stamina;
  }

  @Override
  public float setRotation (float rotation)
  {
    this.rotation = rotation;
    return rotation;
  }

  /**
   * Get regeneration rate
   *
   * @return rate at which health is generated
   */
  public float getRegen ()
  {
    return regen;
  }

  /**
   * Speed that a character will regenerate health
   *
   * @param regen rate health units per update
   */
  public void setRegen (float regen)
  {
    this.regen = regen;
  }

  /**
   * Euclidean distance of tiles
   *
   * @return distance a character can see in tiles
   */
  public float getSight ()
  {
    return sight;
  }

  public void setSight(float sight)
  {
    this.sight = sight;
  }

  /**
   * Euclidean distance of tiles
   *
   * @return distance of tiles a character can hear
   */
  public float getHearing ()
  {
    return hearing;
  }

  /**
   * Set the hearing of the character
   *
   * @param hearing distance of tiles a character can hear
   */
  public void setHearing (float hearing)
  {
    this.hearing = hearing;
  }

  /**
   * How far can a character smell
   *
   * @return number of tiles a character can smell away
   */
  public float getSmell ()
  {
    return smell;
  }

  /**
   * Set the number of tiles a character can smell
   *
   * @param smell tiles away character can smell
   */
  public void setSmell (float smell)
  {
    this.smell = smell;
  }

  @Override
  public float getX ()
  {
    return x;
  }

  @Override
  public float getY ()
  {
    return y;
  }

  @Override
  public float getWidth ()
  {
    return width;
  }

  @Override
  public void setWidth (float width)
  {
    this.width = width;
  }

  @Override
  public float getHeight ()
  {
    return height;
  }

  @Override
  public void setHeight (float height)
  {
    this.height = height;
  }

  @Override
  public Rectangle2D getBoundingRectangle ()
  {
    return new Rectangle2D.Float(getCurrentX(), getCurrentY(), getWidth(),
                                 getHeight());
  }

  @Override
  public boolean intersects (Rectangle2D other)
  {
    return other.intersects(this.getBoundingRectangle());
  }

  @Override
  public boolean isOutOfBounds ()
  {
    return getCurrentX() < 21 &&
            getCurrentY() < 36 &&
            getCurrentX() >= 0 &&
            getCurrentY() >= 0;
  }

  @Override
  public boolean isCombustible ()
  {
    return true;
  }

  @Override
  public CombustedState getCombustedState ()
  {
    return combustedState;
  }

  @Override
  public void setCombustedState (CombustedState s)
  {
    this.combustedState = s;
  }

  @Override
  public boolean setCurrentTime (int time)
  {
    this.time = time;
    if (this.time >= (Duration.BURN_DURATION * 60))
    {
      setCombustedState(CombustedState.BURNED);
      return false;
    }
    return true;
  }

  @Override
  public boolean incrementCurrentTime ()
  {
    return this.setCurrentTime(++this.time);
  }

  @Override
  public int getCurrentTime ()
  {
    return time;
  }

  @Override
  public float getVolume ()
  {
    return volume;
  }

  @Override
  public void setVolume (float volume)
  {
    this.volume = volume;
  }

  @Override
  public AudioChannel getChannel ()
  {
    return channel;
  }

  @Override
  public void setChannel (AudioChannel audioChannel)
  {
    this.channel = audioChannel;
  }

  /**
   * Set the channel that the character is making noise in
   *
   * @param direction direction of noise
   */
  public void setChannel (float direction)
  {
    if (direction == Direction.NORTH || direction == Direction.SOUTH)
    {
      this.channel = AudioChannel.STEREO;
    }
    else if (direction == Direction.EAST)
    {
      this.channel = AudioChannel.RIGHT;
    }
    else if (direction == Direction.WEST)
    {
      this.channel = AudioChannel.LEFT;
    }
  }

  /**
   * Get the angle betweem two characters
   *
   * @param character other character
   * @return angle theta between character and this character
   */
  public float getAngleBetween (Character character)
  {
    float theta = (float) Math.toDegrees(
            Math.atan2(character.getY() - y, character.getX() - x));

    if (theta < 0)
    {
      theta += 360;
    }

    return theta;
  }

  /**
   * Get the cardinal direction N,S,E,W (more coarse)
   *
   * @param character direction
   * @return direction of the other character
   */
  public float getCardinalDirectionBetween (Character character)
  {
    float theta = getAngleBetween(character);
    if (theta <= Direction.NORTHEAST && theta >= Direction.SOUTHEAST)
    {
      return Direction.EAST;
    }
    else if (theta >= Direction.NORTHEAST && theta <= Direction.NORTHWEST)
    {
      return Direction.NORTH;
    }
    else if (theta >= Direction.NORTHWEST && theta <= Direction.SOUTHWEST)
    {
      return Direction.WEST;
    }
    else if (theta >= Direction.SOUTHWEST && theta <= Direction.SOUTHEAST)
    {
      return Direction.SOUTH;
    }

    return theta;
  }


}
