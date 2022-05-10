package pickyeater.UI.App.UserPage;

/**
 * @author Claudio Di Maio
 */

import com.toedter.calendar.JDateChooser;
import pickyeater.UI.LeftButtons.MainButton;
import pickyeater.UI.LeftButtons.PanelButtons;
import pickyeater.UI.LeftButtons.PanelButtonsConverter;
import pickyeater.basics.user.User;
import pickyeater.database.Databases;
import pickyeater.managers.EaterManager;
import pickyeater.managers.PickyEaterManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

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
    private JTextField tfHeight;
    private JTextField tfWeight;
    private JTextField tfBodyfat;
    private JComboBox cbWeightGoal;
    private JTextField tfProteins;
    private JTextField tfCarbs;
    private JTextField tfFats;
    private JLabel txtCalories;
    private JPanel birthdayPanel;
    private JDateChooser jBirthdayChooser;

    public UserEditModePage(Databases databases) {
        EaterManager eaterManager = new PickyEaterManager(databases.getUserDatabase(),
                databases.getIngredientsDatabase(), databases.getMealsDatabase());


        Optional<User> userOptional = eaterManager.getUserManager().getUser();

        User user = userOptional.get();

        // User:
        tfName.setText(user.getName());
        tfSex.setText(user.getUserStatus().getSex().toString());
        // txtDateOfBirth.setText(user.getUserStatus().getDateOfBirth().toString());
        // TODO: Fix DoB
        tfHeight.setText(Double.toString(user.getUserStatus().getHeight()));
        tfWeight.setText(Double.toString(user.getUserStatus().getWeight()));
        tfBodyfat.setText(Double.toString(user.getUserStatus().getBodyFat()));
        //cbLifestyle
        //tfLifestyle.setText(user.getUserGoal().getLifeStyle().toString());
        //tfWeightGoal.setText(user.getUserGoal().getWeightVariationGoal().toString());

        // Nutrients:
        tfProteins.setText(Double.toString(user.getUserGoal().getRequiredNutrients().getProteins()));
        tfCarbs.setText(Double.toString(user.getUserGoal().getRequiredNutrients().getCarbs()));
        tfFats.setText(Double.toString(user.getUserGoal().getRequiredNutrients().getFats()));
        txtCalories.setText(Double.toString(user.getUserGoal().getRequiredNutrients().getCalories()));

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

    private void createUIComponents() {
        jBirthdayChooser = new JDateChooser();
    }
}
