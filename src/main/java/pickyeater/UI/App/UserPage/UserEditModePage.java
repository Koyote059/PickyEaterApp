package pickyeater.UI.App.UserPage;

/**
 * @author Claudio Di Maio
 */

import pickyeater.UI.LeftButtons.MainButton;
import pickyeater.UI.LeftButtons.PanelButtons;
import pickyeater.UI.LeftButtons.PanelButtonsConverter;
import pickyeater.database.Databases;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserEditModePage extends JFrame{
    private JPanel mainPanel;
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btFood;
    private JButton btDiet;
    private JTextField tfName;
    private JComboBox cbLifestyle;
    private JButton btSave;
    private JButton btUndo;
    private JTextField tfSex;
    private JTextField tfDoB;
    private JTextField tfHeight;
    private JTextField tfWeight;
    private JTextField tfBodyfat;
    private JComboBox cbWeightGoal;
    private JTextField tfProteins;
    private JTextField tfCarbs;
    private JTextField tfFats;
    private JLabel lbCalories;

    public UserEditModePage(Databases databases) {

        btDailyProgress.setBackground(Color.white);
        btDiet.setBackground(Color.white);
        btFood.setBackground(Color.white);
        btGroceries.setBackground(Color.white);
        btUser.setBackground(Color.green);
        btSettings.setBackground(Color.white);

        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cmd = e.getActionCommand();
                setVisible(false);
                new MainButton(databases, new PanelButtonsConverter(cmd).Convert());
            }
        };
        btSettings.addActionListener(listener);
        btDailyProgress.addActionListener(listener);
        btUser.addActionListener(listener);
        btGroceries.addActionListener(listener);
        btFood.addActionListener(listener);
        btDiet.addActionListener(listener);
        btUndo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new MainButton(databases, PanelButtons.USER);
            }
        });
        btSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new MainButton(databases, PanelButtons.USER);
            }
        });
    }
}
