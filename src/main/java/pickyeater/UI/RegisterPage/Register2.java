package pickyeater.UI.RegisterPage;

import pickyeater.basics.user.LifeStyle;
import pickyeater.executors.RegisterExecutor;

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

    LifeStyle lifeStyle;

    public Register2(RegisterExecutor registerExecutor) {
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
                lifeStyle = LifeStyle.SEDENTARY;

                Continue(registerExecutor);
            }
        });
        btSlightlyActive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                btSedentary.setBackground(Color.white);
                btSlightlyActive.setBackground(Color.green);
                btActive.setBackground(Color.white);
                btVeryActive.setBackground(Color.white);
                lifeStyle = LifeStyle.LIGHTLY_ACTIVE;

                Continue(registerExecutor);
            }
        });
        btActive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                btSedentary.setBackground(Color.white);
                btSlightlyActive.setBackground(Color.white);
                btActive.setBackground(Color.green);
                btVeryActive.setBackground(Color.white);
                lifeStyle = LifeStyle.ACTIVE;

                Continue(registerExecutor);
            }
        });
        btVeryActive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                btSedentary.setBackground(Color.white);
                btSlightlyActive.setBackground(Color.white);
                btActive.setBackground(Color.white);
                btVeryActive.setBackground(Color.green);
                lifeStyle = LifeStyle.VERY_ACTIVE;

                Continue(registerExecutor);
            }
        });
    btBack.addComponentListener(new ComponentAdapter() { } );
        btBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
                new Register1(registerExecutor);
            }
        });
    }
    private void Continue(RegisterExecutor registerExecutor){
        if (lifeStyle != null){
            //JOptionPane.showMessageDialog(buttonPanel, "Lifestyle: " + lifeStyle);

            setVisible(false);
//            EventQueue.invokeLater(Register3::new);
            new Register3(registerExecutor);     // todo: remove
        }
    }
}
