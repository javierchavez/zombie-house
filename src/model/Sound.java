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
 * Describes any object which can make a sound
 */

public interface Sound
{

  /**
   * Get the volume of this sound
   *
   * @return volume
   */
  float getVolume ();

  /**
   * Set the volume of the sound.
   * This should be done very carefully as sharp volume changes are not ok.
   *
   * @param volume volume to be set to
   */
  void setVolume (float volume);


  /**
   * Get the set channel
   *
   * @return Channel that sound will be played
   */
  AudioChannel getChannel ();

  /**
   * Set the channel for audio to be played
   *
   * @param audioChannel set the Channel
   */
  void setChannel (AudioChannel audioChannel);

  /**
   * Set the channel for audio to be played
   *
   * @param soundType set the type of sound
   */
  void setSoundType (SoundType soundType);

  /**
   *  Get the type of sound
   */
  SoundType getSoundType ();

  enum SoundType
  {
    HIT, WALK, NONE
  }

  /**
   * Channels for playback
   */
  enum AudioChannel
  {
    LEFT, RIGHT, STEREO
  }
}
