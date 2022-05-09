package pickyeater.UI.RegisterPage;

/**
 * @author Claudio Di Maio
 */

import pickyeater.basics.user.LifeStyle;
import pickyeater.builders.UserBuilder;
import pickyeater.database.UserDatabase;
import pickyeater.executors.ExecutorProvider;
import pickyeater.executors.RegisterExecutor;
import pickyeater.managers.EaterManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;import java.awt.event.ComponentAdapter;

public class Register2 extends JFrame {
    private JPanel panel1;
    private JButton btSedentary;
    private JButton btVeryActive;
    private JButton btSlightlyActive;
    private JButton btActive;
    private JPanel mainPanel;
    private JButton btBack;
    private JPanel buttonPanel;

    public Register2(EaterManager eaterManager, ExecutorProvider executorProvider, UserBuilder userBuilder) {
        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        btSedentary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                btSedentary.setBackground(Color.green);
                btSlightlyActive.setBackground(Color.white);
                btActive.setBackground(Color.white);
                btVeryActive.setBackground(Color.white);

                userBuilder.setLifeStyle(LifeStyle.SEDENTARY);
                System.out.println("LS " + LifeStyle.SEDENTARY);
                System.out.println("UB " + userBuilder.getLifeStyle());
                Continue(eaterManager, executorProvider, userBuilder);
            }
        });
        btSlightlyActive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                btSedentary.setBackground(Color.white);
                btSlightlyActive.setBackground(Color.green);
                btActive.setBackground(Color.white);
                btVeryActive.setBackground(Color.white);

                userBuilder.setLifeStyle(LifeStyle.LIGHTLY_ACTIVE);

                Continue(eaterManager, executorProvider, userBuilder);
            }
        });
        btActive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                btSedentary.setBackground(Color.white);
                btSlightlyActive.setBackground(Color.white);
                btActive.setBackground(Color.green);
                btVeryActive.setBackground(Color.white);

                userBuilder.setLifeStyle(LifeStyle.ACTIVE);

                Continue(eaterManager, executorProvider, userBuilder);
            }
        });
        btVeryActive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                btSedentary.setBackground(Color.white);
                btSlightlyActive.setBackground(Color.white);
                btActive.setBackground(Color.white);
                btVeryActive.setBackground(Color.green);

                userBuilder.setLifeStyle(LifeStyle.VERY_ACTIVE);

                Continue(eaterManager, executorProvider, userBuilder);
            }
        });
    btBack.addComponentListener(new ComponentAdapter() { } );
        btBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
                new Register1(eaterManager, executorProvider);
            }
        });
    }
    private void Continue(EaterManager eaterManager, ExecutorProvider executorProvider, UserBuilder userBuilder){
        if (userBuilder.getLifeStyle() != null){
            //JOptionPane.showMessageDialog(buttonPanel, "Lifestyle: " + lifeStyle);
            setVisible(false);
            new Register3(eaterManager, executorProvider, userBuilder);
        }
    }
}
