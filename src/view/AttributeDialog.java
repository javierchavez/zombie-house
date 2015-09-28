package view;

import io.Serializer;
import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class AttributeDialog
{
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
