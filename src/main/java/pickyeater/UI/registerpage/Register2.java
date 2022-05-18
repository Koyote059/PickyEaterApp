package pickyeater.UI.registerpage;

/**
 * @author Claudio Di Maio
 */
import pickyeater.basics.user.LifeStyle;
import pickyeater.executors.user.RegisterExecutor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Register2 extends JFrame {
    private JPanel panel1;
    private JButton btSedentary;
    private JButton btVeryActive;
    private JButton btSlightlyActive;
    private JButton btActive;
    private JPanel mainPanel;
    private JButton btBack;
    private JPanel buttonPanel;

    public Register2(RegisterExecutor registerExecutor) {
        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        btSedentary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                registerExecutor.getUserBuilder().setLifeStyle(LifeStyle.SEDENTARY);
                next(registerExecutor);
            }
        });
        btSlightlyActive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                registerExecutor.getUserBuilder().setLifeStyle(LifeStyle.LIGHTLY_ACTIVE);

                next(registerExecutor);
            }
        });
        btActive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                registerExecutor.getUserBuilder().setLifeStyle(LifeStyle.ACTIVE);

                next(registerExecutor);
            }
        });
        btVeryActive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                registerExecutor.getUserBuilder().setLifeStyle(LifeStyle.VERY_ACTIVE);

                next(registerExecutor);
            }
        });
    btBack.addComponentListener(new ComponentAdapter() { } );
        btBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
                new Register1();
            }
        });
        btSedentary.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btSedentary.setBackground(Color.green);
                btSlightlyActive.setBackground(Color.white);
                btActive.setBackground(Color.white);
                btVeryActive.setBackground(Color.white);
            }
        });
        btSlightlyActive.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btSedentary.setBackground(Color.white);
                btSlightlyActive.setBackground(Color.green);
                btActive.setBackground(Color.white);
                btVeryActive.setBackground(Color.white);
            }
        });
        btActive.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btSedentary.setBackground(Color.white);
                btSlightlyActive.setBackground(Color.white);
                btActive.setBackground(Color.green);
                btVeryActive.setBackground(Color.white);
            }
        });
        btVeryActive.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btSedentary.setBackground(Color.white);
                btSlightlyActive.setBackground(Color.white);
                btActive.setBackground(Color.white);
                btVeryActive.setBackground(Color.green);
            }
        });
        MouseAdapter listener = new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                btSedentary.setBackground(Color.white);
                btSlightlyActive.setBackground(Color.white);
                btActive.setBackground(Color.white);
                btVeryActive.setBackground(Color.white);
            }
        };
        btActive.addMouseListener(listener);
        btSlightlyActive.addMouseListener(listener);
        btSedentary.addMouseListener(listener);
        btVeryActive.addMouseListener(listener);
    }
    private void next(RegisterExecutor registerExecutor){
        if (registerExecutor.getUserBuilder().getLifeStyle() != null){
            //JOptionPane.showMessageDialog(buttonPanel, "Lifestyle: " + lifeStyle);
            setVisible(false);
            new Register3(registerExecutor);
        }
    }
}
