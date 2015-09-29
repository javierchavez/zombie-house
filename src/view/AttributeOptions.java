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

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import common.CharacterAttributes;
import common.Duration;
import common.Speed;
import model.House;
import model.Player;

/**
 * This class changes game settings from the menu
 */
public class AttributeOptions extends JFrame
{
  private final House house;
  private final Color BORDER_COLOR = new Color(122, 121, 120);

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
    setSize(500, 800);
    setLayout(new BorderLayout());

    Player player = house.getPlayer();

    // Player attributes
    JTextField playerSight = new JTextField(String.valueOf(player.getSight()), 3);
    JTextField playerHearing = new JTextField(String.valueOf(player.getHearing()), 3);
    JTextField playerSpeed = new JTextField((String.valueOf(Speed.WALK)), 3);
    JTextField playerStamina = new JTextField(String.valueOf(CharacterAttributes.MAX_STAMINA), 3);
    JTextField playerRegen = new JTextField(String.valueOf(player.getRegen()), 3);
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

    // Player attributes panel
    JPanel attrPanel = new JPanel(new GridLayout(24, 2));
    attrPanel.setBackground(new Color(228, 228, 228));
    attrPanel.setBorder(new EmptyBorder(5, 10, 10, 10));
    attrPanel.add(new JLabel("PLAYER SETTINGS:"));
    attrPanel.add(new JLabel());
    attrPanel.add(new JLabel("Sight:"));
    attrPanel.add(playerSight);
    attrPanel.add(new JLabel("Hearing:"));
    attrPanel.add(playerHearing);
    attrPanel.add(new JLabel("Speed:"));
    attrPanel.add(playerSpeed);
    attrPanel.add(new JLabel("Stamina:"));
    attrPanel.add(playerStamina);
    attrPanel.add(new JLabel("Stamina Regen:"));
    attrPanel.add(playerRegen);
    attrPanel.add(new JLabel("Trap Pick-up Time:"));
    attrPanel.add(pickupTime);

    attrPanel.add(new JLabel());
    attrPanel.add(new JLabel());

    attrPanel.add(new JLabel("ZOMBIE SETTINGS"));
    attrPanel.add(new JLabel());
    attrPanel.add(new JLabel("Zombie speed:"));
    attrPanel.add(zombieSpeed);
    attrPanel.add(new JLabel("Zombie smell:"));
    attrPanel.add(zombieSmell);
    attrPanel.add(new JLabel("Zombie Decision Rate (sec):"));
    attrPanel.add(zombieDecisionRate);

    attrPanel.add(new JLabel());
    attrPanel.add(new JLabel());

    attrPanel.add(new JLabel("SUPER ZOMBIE SETTINGS:"));
    attrPanel.add(new JLabel());
    attrPanel.add(new JLabel("Super Zombie Speed:"));
    attrPanel.add(superZombieSpeed);
    attrPanel.add(new JLabel("Super Zombie Decision Rate (sec):"));
    attrPanel.add(superZombieDecisionRate);

    attrPanel.add(new JLabel());
    attrPanel.add(new JLabel());

    attrPanel.add(new JLabel("HOUSE SETTINGS:"));
    attrPanel.add(new JLabel());
    attrPanel.add(new JLabel("Rows:"));
    attrPanel.add(houseRows);
    attrPanel.add(new JLabel("Columns:"));
    attrPanel.add(houseCols);
    attrPanel.add(new JLabel("Zombie Spawn:"));
    attrPanel.add(zombieSpawn);
    attrPanel.add(new JLabel("Fire Trap Spawn:"));
    attrPanel.add(fireTrapSpawn);
    attrPanel.add(new JLabel("Rooms:"));
    attrPanel.add(houseRooms);
    attrPanel.add(new JLabel("Trap Burn Duration:"));
    attrPanel.add(burnDuration);

    // Button panel
    JButton saveButton = new JButton("Save");
    JButton cancelButton = new JButton("Cancel");
    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(new Color(241, 241, 241));
    buttonPanel.add(saveButton);
    buttonPanel.add(cancelButton);

    // Bottom panel
    JPanel bottomPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.SOUTH;
    gbc.weighty = 1;
    bottomPanel.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
    bottomPanel.add(buttonPanel, gbc);

    // Main panel
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
    mainPanel.add(attrPanel, BorderLayout.CENTER);

    // Add JPanels to JFrame
    add(bottomPanel, BorderLayout.SOUTH);
    add(mainPanel);

    // Add action listeners to buttons
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

    setLocationRelativeTo(null);
    setVisible(true);
  }

}
