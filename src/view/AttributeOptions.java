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
 * This is the interface for Combustible objects
 */


import common.Duration;
import common.Speed;
import model.House;
import model.Player;
import model.SuperZombie;
import model.Zombie;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
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
    Player player = house.getPlayer();
    SuperZombie superZombie = house.getSuperZombie();
    List<Zombie> zombies = house.getZombies();

    if (zombies.size() == 0)
    {
      zombies.add(new Zombie());
    }

    JButton saveButton = new JButton("Save");
    JButton cancelButton = new JButton("Cancel");

    JTextField playerSight = new JTextField(String.valueOf(player.getSight()), 3);
    JTextField playerHearing = new JTextField(String.valueOf(player.getHearing()), 3);
    JTextField playerSpeed = new JTextField((String.valueOf(Speed.WALK)), 3);
    JTextField playerStamina = new JTextField(String.valueOf(player.getStamina()), 3);
    JTextField playerRegen = new JTextField(String.valueOf(player.getRegen()), 3);
    JTextField zombieSpawn = new JTextField(String.valueOf(house.getZombieSpawn()), 4);
    JTextField zombieSpeed = new JTextField(String.valueOf(Speed.STAGGER), 3);
    JTextField superZombieSpeed = new JTextField(String.valueOf(Speed.FAST_WALK), 3);
    JTextField zombieDecisionRate = new JTextField(String.valueOf(Duration.ZOMBIE_UPDATE), 3);
    JTextField superZombieDecisionRate = new JTextField(String.valueOf(Duration.SUPER_ZOMBIE_UPDATE), 3);
    JTextField zombieSmell = new JTextField(String.valueOf(zombies.get(0).getSmell()), 3);
    JTextField fireTrapSpawn = new JTextField(String.valueOf(house.getTrapSpawn()));

    setSize(500, 600);
    setLayout(new GridLayout(13, 2, 5, 5));

    add(new JLabel("Player Sight:"));
    add(playerSight);
    add(new JLabel("Player Hearing:"));
    add(playerHearing);
    add(new JLabel("Player Speed:"));
    add(playerSpeed);
    add(new JLabel("Player Stamina:"));
    add(playerStamina);
    add(new JLabel("Player Stamina Regen:"));
    add(playerRegen);
    add(new JLabel("Zombie Spawn:"));
    add(zombieSpawn);
    add(new JLabel("Zombie Speed:"));
    add(zombieSpeed);
    add(new JLabel("Super Zombie Speed:"));
    add(superZombieSpeed);
    add(new JLabel("Zombie Decision Rate (sec):"));
    add(zombieDecisionRate);
    add(new JLabel("Super Zombie Decision Rate (sec):"));
    add(superZombieDecisionRate);
    add(new JLabel("Zombie Smell:"));
    add(zombieSmell);
    add(new JLabel("Fire Trap Spawn:"));
    add(fireTrapSpawn);
    add(saveButton);
    add(cancelButton);

    setLocationRelativeTo(null);
    setVisible(true);

    saveButton.addActionListener(e -> {
      player.setSight(Float.parseFloat(playerSight.getText()));
      player.setHearing(Float.parseFloat(playerHearing.getText()));
      float newSpeed = Float.parseFloat(playerSpeed.getText());
      Speed.WALK = newSpeed;
      Speed.RUN = 2*newSpeed;
      player.setStamina(Float.parseFloat(playerStamina.getText()));
      player.setRegen(Float.parseFloat(playerRegen.getText()));
      newSpeed = Float.parseFloat(zombieSpeed.getText());
      Speed.STAGGER = newSpeed;
      Speed.STAGGER_RUN = 1.25f*newSpeed;
      Speed.FAST_WALK = Float.parseFloat(superZombieSpeed.getText());
      Duration.ZOMBIE_UPDATE = Float.parseFloat(zombieDecisionRate.getText());
      Duration.SUPER_ZOMBIE_UPDATE = Float.parseFloat(superZombieDecisionRate.getText());
      float newSmell = Float.parseFloat(zombieSmell.getText());
      for (Zombie zombie : zombies)
      {
        zombie.setSmell(newSmell);
      }
      house.setZombieSpawn(Float.parseFloat(zombieSpawn.getText()));
      house.setTrapSpawn(Float.parseFloat(fireTrapSpawn.getText()));
      dispose();
    });

    cancelButton.addActionListener(e -> {
      dispose();
    });
  }

  private void showOptionFrame ()
  {



    JButton button = new JButton("Save");
    JTextField threadNumberTextField = new JTextField("4", 3);
    JTextField gridXTextField = new JTextField(String.valueOf(11),
                                               3);
    JTextField gridYTextField = new JTextField(String.valueOf(11),
                                               3);

    JComboBox presets = new JComboBox();

    final JFrame optionFrame = new JFrame("Options");
    optionFrame.setPreferredSize(new Dimension(300, 150));
    GridLayout experimentLayout = new GridLayout(0, 3, 5, 0);

    optionFrame.setLayout(experimentLayout);

    optionFrame.add(new JLabel("Threads: "));
    optionFrame.add(threadNumberTextField);
    optionFrame.add(new JLabel(" "));
    optionFrame.add(new JLabel("Grid size: "));
    optionFrame.add(gridXTextField);
    optionFrame.add(gridYTextField);
    optionFrame.add(new JLabel("Preset: "));
    optionFrame.add(presets);
    optionFrame.add(new JLabel(" "));
    optionFrame.add(button);
    optionFrame.pack();
    optionFrame.setVisible(true);
    optionFrame.setLocationRelativeTo(null); //center on screen

    button.addActionListener(e -> {


      // get the input
      String userThreadsInput = threadNumberTextField.getText();
      String userGridXInput = gridXTextField.getText();
      String userGridYInput = gridYTextField.getText();

      int gridCols = Integer.parseInt(userGridXInput);
      int gridRows = Integer.parseInt(userGridYInput);
      int itemIndex = presets.getSelectedIndex();

        optionFrame.setTitle("Conway Game of Life");


      // get rid of the dialog
      optionFrame.dispose();
    });
  }
}
