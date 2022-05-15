package pickyeater.UI.registerpage;

/**
 * @author Claudio Di Maio
 */

import pickyeater.basics.user.WeightGoal;
import pickyeater.executors.user.RegisterExecutor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Register3 extends JFrame {
    private JPanel panel1;
    private JButton btLoseWeight;
    private JButton btGainWeight;
    private JButton btMaintainWeight;
    private JPanel mainPanel;
    private JButton btBack;
    private JPanel buttonPanel;

    public Register3(RegisterExecutor registerExecutor) {
        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        btLoseWeight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                registerExecutor.getUserBuilder().setWeightVariationGoal(WeightGoal.LOSE_WEIGHT);

                next(registerExecutor);
            }
        });
        btGainWeight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                registerExecutor.getUserBuilder().setWeightVariationGoal(WeightGoal.INCREASE_WEIGHT);

                next(registerExecutor);
            }
        });
        btMaintainWeight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                registerExecutor.getUserBuilder().setWeightVariationGoal(WeightGoal.MANTAIN_WEIGHT);

                next(registerExecutor);
            }
        });

    btBack.addComponentListener(new ComponentAdapter() { } );
        btBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
                new Register2(registerExecutor);
            }
        });
        btLoseWeight.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btLoseWeight.setBackground(Color.green);
                btGainWeight.setBackground(Color.white);
                btMaintainWeight.setBackground(Color.white);
            }
        });
        btGainWeight.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btLoseWeight.setBackground(Color.white);
                btGainWeight.setBackground(Color.green);
                btMaintainWeight.setBackground(Color.white);
            }
        });
        btMaintainWeight.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btLoseWeight.setBackground(Color.white);
                btGainWeight.setBackground(Color.white);
                btMaintainWeight.setBackground(Color.green);
            }
        });
        MouseAdapter listener = new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                btLoseWeight.setBackground(Color.white);
                btGainWeight.setBackground(Color.white);
                btMaintainWeight.setBackground(Color.white);
            }
        };
        btGainWeight.addMouseListener(listener);
        btMaintainWeight.addMouseListener(listener);
        btLoseWeight.addMouseListener(listener);
    }
    private void next(RegisterExecutor registerExecutor){
        if (registerExecutor.getUserBuilder().getWeightVariationGoal() != null){
            setVisible(false);

            new Register4(registerExecutor);
        }
    }
}
