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
    private JButton btLoseWeight;
    private JButton btGainWeight;
    private JButton btMaintainWeight;
    private JPanel mainPanel;
    private JButton btBack;

    public Register3(RegisterExecutor registerExecutor) {
        setContentPane(mainPanel);
        setSize(677, 507);    //pack();
        //setLocation(350,150);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        btLoseWeight.setBackground(Color.decode("#FFFFFF"));
        btGainWeight.setBackground(Color.decode("#FFFFFF"));
        btMaintainWeight.setBackground(Color.decode("#FFFFFF"));

        btLoseWeight.addActionListener(actionEvent -> {
            registerExecutor.getUserBuilder().setWeightVariationGoal(WeightGoal.LOSE_WEIGHT);

            next(registerExecutor);
        });
        btGainWeight.addActionListener(actionEvent -> {
            registerExecutor.getUserBuilder().setWeightVariationGoal(WeightGoal.INCREASE_WEIGHT);

            next(registerExecutor);
        });
        btMaintainWeight.addActionListener(actionEvent -> {
            registerExecutor.getUserBuilder().setWeightVariationGoal(WeightGoal.MAINTAIN_WEIGHT);

            next(registerExecutor);
        });

    btBack.addComponentListener(new ComponentAdapter() { } );
        btBack.addActionListener(actionEvent -> {
            setVisible(false);
            new Register2(registerExecutor);
        });
        btLoseWeight.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btLoseWeight.setBackground(Color.decode("#B1EA9D"));
                btGainWeight.setBackground(Color.decode("#FFFFFF"));
                btMaintainWeight.setBackground(Color.decode("#FFFFFF"));
            }
        });
        btGainWeight.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btLoseWeight.setBackground(Color.decode("#FFFFFF"));
                btGainWeight.setBackground(Color.decode("#B1EA9D"));
                btMaintainWeight.setBackground(Color.decode("#FFFFFF"));
            }
        });
        btMaintainWeight.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btLoseWeight.setBackground(Color.decode("#FFFFFF"));
                btGainWeight.setBackground(Color.decode("#FFFFFF"));
                btMaintainWeight.setBackground(Color.decode("#B1EA9D"));
            }
        });
        MouseAdapter listener = new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                btLoseWeight.setBackground(Color.decode("#FFFFFF"));
                btGainWeight.setBackground(Color.decode("#FFFFFF"));
                btMaintainWeight.setBackground(Color.decode("#FFFFFF"));
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
