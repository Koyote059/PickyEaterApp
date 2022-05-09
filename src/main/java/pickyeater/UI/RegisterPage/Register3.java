package pickyeater.UI.RegisterPage;

/**
 * @author Claudio Di Maio
 */

import pickyeater.basics.user.WeightGoal;
import pickyeater.builders.UserBuilder;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.RegisterExecutor;
import pickyeater.managers.EaterManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;

public class Register3 extends JFrame {
    private JPanel panel1;
    private JButton btLoseWeight;
    private JButton btGainWeight;
    private JButton btMaintainWeight;
    private JPanel mainPanel;
    private JButton btBack;
    private JPanel buttonPanel;

    public Register3(EaterManager eaterManager, ExecutorProvider executorProvider, UserBuilder userBuilder) {
        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        btLoseWeight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                btLoseWeight.setBackground(Color.green);
                btGainWeight.setBackground(Color.white);
                btMaintainWeight.setBackground(Color.white);
                userBuilder.setWeightVariationGoal(WeightGoal.LOSE_WEIGHT);

                Continue(eaterManager, executorProvider, userBuilder);
            }
        });
        btGainWeight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                btLoseWeight.setBackground(Color.white);
                btGainWeight.setBackground(Color.green);
                btMaintainWeight.setBackground(Color.white);
                userBuilder.setWeightVariationGoal(WeightGoal.INCREASE_WEIGHT);

                Continue(eaterManager, executorProvider, userBuilder);
            }
        });
        btMaintainWeight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                btLoseWeight.setBackground(Color.white);
                btGainWeight.setBackground(Color.white);
                btMaintainWeight.setBackground(Color.green);
                userBuilder.setWeightVariationGoal(WeightGoal.MANTAIN_WEIGHT);

                Continue(eaterManager, executorProvider, userBuilder);
            }
        });

    btBack.addComponentListener(new ComponentAdapter() { } );
        btBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
                new Register2(eaterManager, executorProvider, userBuilder);
            }
        });
    }
    private void Continue(EaterManager eaterManager,ExecutorProvider executorProvider, UserBuilder userBuilder){
        if (executorProvider.getRegisterExecutor().getUserBuilder().getWeightVariationGoal() != null){
            //JOptionPane.showMessageDialog(buttonPanel, "Goal: " + weightGoal);
            setVisible(false);

            new Register4(eaterManager, executorProvider, userBuilder);
        }
    }
}
