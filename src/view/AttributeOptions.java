package view;

import common.CharacterAttributes;
import common.Duration;
import common.Speed;
import model.House;
import model.Player;
import model.SuperZombie;
import model.Zombie;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 */
public class AttributeOptions extends JFrame
{
  private House house;

  public AttributeOptions(House house)
  {
    super("Zombie House Settings");
    this.house = house;
    initFrame();
  }

  private void initFrame()
  {
    Player player = house.getPlayer();
    SuperZombie superZombie = house.getSuperZombie();
    Array zombies = house.getZombies();

    JButton saveButton = new JButton("Save");
    JButton cancelButton = new JButton("Cancel");

    JTextField playerSight = new JTextField(String.valueOf(CharacterAttributes.SIGHT), 3);
    JTextField playerHearing = new JTextField(String.valueOf(CharacterAttributes.HEARING), 3);
    JTextField playerSpeed = new JTextField((String.valueOf(Speed.WALK)), 3);
    JTextField playerStamina = new JTextField(String.valueOf(CharacterAttributes.MAX_STAMINA), 3);
    JTextField playerRegen = new JTextField(String.valueOf(CharacterAttributes.STAMINA_REGEN), 3);
    JTextField zombieSpawn = new JTextField(String.valueOf(.01), 4);
    JTextField zombieSpeed = new JTextField(String.valueOf(Speed.STAGGER), 3);
    JTextField superZombieSpeed = new JTextField(String.valueOf(Speed.FAST_WALK), 3);
    JTextField zombieDecisionRate = new JTextField(String.valueOf(Duration.ZOMBIE_UPDATE), 3);
    JTextField superZombieDecisionRate = new JTextField(String.valueOf(Duration.SUPER_ZOMBIE_UPDATE), 3);
    JTextField zombieSmell = new JTextField(String.valueOf(CharacterAttributes.SMELL), 3);
    JTextField fireTrapSpawn = new JTextField(String.valueOf(.01));

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

  public static void main(String[] args)
  {
    AttributeOptions ao = new AttributeOptions(new House(new Player()));
  }
}
