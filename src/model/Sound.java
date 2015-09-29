package model;

/**
 * @author Javier Chavez
 * @author Alex Baker
 * @author Erin Sosebee
 *         <p>
 *         Date September 28, 2015
 *         CS 351
 *         Zombie House
 *         <p>
 *         This is the interface for Combustible objects
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
   *
   * @return
   */
  AudioChannel getChannel();

  void setChannel(AudioChannel audioChannel);

  enum AudioChannel
  {
      LEFT, RIGHT, STEREO
  }
}
