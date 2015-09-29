package view;

/**
 * @author Javier Chavez
 * @author Alex Baker
 * @author Erin Sosebee
 * <p>
 * Date September 28, 2015
 * CS 351
 * Zombie House
 * <p>
 */


import common.CharacterAttributes;
import common.Duration;
import common.Speed;
import model.House;
import model.Player;

import javax.swing.*;
import java.awt.*;

/**
 * This class changes game settings from the menu
 */
public class AttributeOptions extends JFrame
{
  private final House house;

  public AttributeOptions(House house)
  {
    super("Zombie House Settings");
    this.house = house;
    initFrame();
  }

  public static void main (String[] args)
  {
    AttributeOptions ao = new AttributeOptions(new House(new Player()));
  }

  private void initFrame()
  {
    JButton saveButton = new JButton("Save");
    JButton cancelButton = new JButton("Cancel");

    JTextField playerSight = new JTextField(String.valueOf(CharacterAttributes.SIGHT), 3);
    JTextField playerHearing = new JTextField(String.valueOf(CharacterAttributes.HEARING), 3);
    JTextField playerSpeed = new JTextField((String.valueOf(Speed.WALK)), 3);
    JTextField playerStamina = new JTextField(String.valueOf(CharacterAttributes.MAX_STAMINA), 3);
    JTextField playerRegen = new JTextField(String.valueOf(CharacterAttributes.STAMINA_REGEN), 3);
    JTextField zombieSpawn = new JTextField(String.valueOf(house.getZombieSpawn()), 4);
    JTextField zombieSpeed = new JTextField(String.valueOf(Speed.STAGGER), 3);
    JTextField superZombieSpeed = new JTextField(String.valueOf(Speed.FAST_WALK), 3);
    JTextField zombieDecisionRate = new JTextField(String.valueOf(Duration.ZOMBIE_UPDATE), 3);
    JTextField superZombieDecisionRate = new JTextField(String.valueOf(Duration.SUPER_ZOMBIE_UPDATE), 3);
    JTextField zombieSmell = new JTextField(String.valueOf(CharacterAttributes.SMELL), 3);
    JTextField fireTrapSpawn = new JTextField(String.valueOf(house.getTrapSpawn()));
    JTextField houseRows = new JTextField(String.valueOf(house.getRows()), 3);
    JTextField houseCols = new JTextField(String.valueOf(house.getCols()), 3);
    JTextField houseRooms = new JTextField(String.valueOf(house.getNumRooms()), 3);
    JTextField pickupTime = new JTextField(String.valueOf(Duration.PICKUP_TIME));
    JTextField burnDuration = new JTextField(String.valueOf(Duration.BURN_DURATION));

    setSize(500, 800);
    setLayout(new GridLayout(25, 2, 5, 5));

    add(new JLabel("Player Settings:"));
    add(new JLabel());
    add(new JLabel("Sight:"));
    add(playerSight);
    add(new JLabel("Hearing:"));
    add(playerHearing);
    add(new JLabel("Speed:"));
    add(playerSpeed);
    add(new JLabel("Stamina:"));
    add(playerStamina);
    add(new JLabel("Stamina Regen:"));
    add(playerRegen);
    add(new JLabel("Trap Pickup Time:"));
    add(pickupTime);

    add(new JLabel());
    add(new JLabel());

    add(new JLabel("Zombie Settings:"));
    add(new JLabel());
    add(new JLabel("Zombie Speed:"));
    add(zombieSpeed);
    add(new JLabel("Zombie Smell:"));
    add(zombieSmell);
    add(new JLabel("Zombie Decision Rate (sec):"));
    add(zombieDecisionRate);

    add(new JLabel());
    add(new JLabel());

    add(new JLabel("Super Zombie Settings:"));
    add(new JLabel());
    add(new JLabel("Super Zombie Speed:"));
    add(superZombieSpeed);
    add(new JLabel("Super Zombie Decision Rate (sec):"));
    add(superZombieDecisionRate);

    add(new JLabel());
    add(new JLabel());

    add(new JLabel("House Settings:"));
    add(new JLabel());
    add(new JLabel("Rows:"));
    add(houseRows);
    add(new JLabel("Columns:"));
    add(houseCols);
    add(new JLabel("Zombie Spawn:"));
    add(zombieSpawn);
    add(new JLabel("Fire Trap Spawn:"));
    add(fireTrapSpawn);
    add(new JLabel("Rooms:"));
    add(houseRooms);
    add(new JLabel("Trap Burn Duration:"));
    add(burnDuration);

    add(saveButton);
    add(cancelButton);

    setLocationRelativeTo(null);
    setVisible(true);

    saveButton.addActionListener(e -> {
      CharacterAttributes.SIGHT = Float.parseFloat(playerSight.getText());
      CharacterAttributes.HEARING = Float.parseFloat(playerHearing.getText());
      float newSpeed = Float.parseFloat(playerSpeed.getText());
      Speed.WALK = newSpeed;
      Speed.RUN = 2 * newSpeed;
      CharacterAttributes.MAX_STAMINA = Float.parseFloat(playerStamina.getText());
      CharacterAttributes.STAMINA_REGEN = Float.parseFloat(playerRegen.getText());
      newSpeed = Float.parseFloat(zombieSpeed.getText());
      Speed.STAGGER = newSpeed;
      Speed.STAGGER_RUN = 1.25f * newSpeed;
      Speed.FAST_WALK = Float.parseFloat(superZombieSpeed.getText());
      Duration.ZOMBIE_UPDATE = Float.parseFloat(zombieDecisionRate.getText());
      Duration.SUPER_ZOMBIE_UPDATE = Float.parseFloat(superZombieDecisionRate.getText());
      CharacterAttributes.SMELL = Float.parseFloat(zombieSmell.getText());
      house.setZombieSpawn(Float.parseFloat(zombieSpawn.getText()));
      house.setTrapSpawn(Float.parseFloat(fireTrapSpawn.getText()));
      house.setNumRooms(Integer.parseInt(houseRooms.getText()));
      house.setCols(Integer.parseInt(houseCols.getText()));
      house.setRows(Integer.parseInt(houseRows.getText()));
      Duration.PICKUP_TIME = Float.parseFloat(pickupTime.getText());
      Duration.BURN_DURATION = Float.parseFloat(burnDuration.getText());
      dispose();
    });

    cancelButton.addActionListener(e -> {
      dispose();
    });
  }
}
