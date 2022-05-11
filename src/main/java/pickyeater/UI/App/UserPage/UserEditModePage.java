package pickyeater.UI.App.UserPage;

/**
 * @author Claudio Di Maio
 */

import com.toedter.calendar.JDateChooser;
import pickyeater.UI.LeftButtons.MainButton;
import pickyeater.UI.LeftButtons.PanelButtons;
import pickyeater.UI.LeftButtons.PanelButtonsConverter;
import pickyeater.basics.user.LifeStyle;
import pickyeater.basics.user.Sex;
import pickyeater.basics.user.User;
import pickyeater.basics.user.WeightGoal;
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
    private JComboBox cbSex;

    public UserEditModePage(Databases databases) {
        EaterManager eaterManager = new PickyEaterManager(databases.getUserDatabase(),
                databases.getIngredientsDatabase(), databases.getMealsDatabase());


        Optional<User> userOptional = eaterManager.getUserManager().getUser();

        User user = userOptional.get();

        // User:
        tfName.setText(user.getName());
        // txtDateOfBirth.setText(user.getUserStatus().getDateOfBirth().toString());
        // TODO: Fix DoB
        tfHeight.setText(Double.toString(user.getUserStatus().getHeight()));
        tfWeight.setText(Double.toString(user.getUserStatus().getWeight()));
        tfBodyfat.setText(Double.toString(user.getUserStatus().getBodyFat()));

        // tfSex.setText(user.getUserStatus().getSex().toString());
        if (user.getUserStatus().getSex() == Sex.MALE) {
            cbSex.setSelectedIndex(0);
        } else if (user.getUserStatus().getSex() == Sex.FEMALE){
            cbSex.setSelectedIndex(1);
        }

        if (user.getUserGoal().getLifeStyle() == LifeStyle.SEDENTARY) {
            cbLifestyle.setSelectedIndex(0);
        } else if (user.getUserGoal().getLifeStyle() == LifeStyle.LIGHTLY_ACTIVE){
            cbLifestyle.setSelectedIndex(1);
        } else if (user.getUserGoal().getLifeStyle() == LifeStyle.ACTIVE){
            cbLifestyle.setSelectedIndex(2);
        } else if (user.getUserGoal().getLifeStyle() == LifeStyle.VERY_ACTIVE){
            cbLifestyle.setSelectedIndex(3);
        }

        if (user.getUserGoal().getWeightVariationGoal() == WeightGoal.LOSE_WEIGHT) {
            cbWeightGoal.setSelectedIndex(0);
        } else if (user.getUserGoal().getWeightVariationGoal() == WeightGoal.MANTAIN_WEIGHT){
            cbWeightGoal.setSelectedIndex(1);
        } else if (user.getUserGoal().getWeightVariationGoal() == WeightGoal.INCREASE_WEIGHT){
            cbWeightGoal.setSelectedIndex(2);
        }

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
        cbLifestyle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                String item = cb.getSelectedItem().toString();
                if (item.equals("Sedentary")){
                    //TODO: Set lifestyle as sedentary (after save)
                } else if (item.equals("Slightly Active")){

                } else if (item.equals("Active")){

                } else if (item.equals("Very Active")){

                } else {
                    System.out.println("Error in UserEditModePage - 1");
                }
            }
        });
        cbWeightGoal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                String item = cb.getSelectedItem().toString();
                if (item.equals("Decrease Weight")){
                    //TODO: Set weightGoal as Decrease Weight (after save)
                } else if (item.equals("Maintain Weight")){

                } else if (item.equals("Increase Weight")) {

                } else {
                    System.out.println("Error in UserEditModePage - 2");
                }
            }
        });
        cbSex.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                String item = cb.getSelectedItem().toString();
                if (item.equals("Male")){
                    //TODO: Set sex as male (after save)
                } else if (item.equals("Female")){

                } else {
                    System.out.println("Error in UserEditModePage - 3");
                }
            }
        });
    }

    private void createUIComponents() {
        jBirthdayChooser = new JDateChooser();
    }
}
