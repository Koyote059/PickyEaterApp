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
        setSize(677, 507);    //pack();
        setLocation(350,150);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        btSedentary.setBackground(Color.decode("#FFFFFF"));
        btSlightlyActive.setBackground(Color.decode("#FFFFFF"));
        btActive.setBackground(Color.decode("#FFFFFF"));
        btVeryActive.setBackground(Color.decode("#FFFFFF"));

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
                btSedentary.setBackground(Color.decode("#B1EA9D"));
                btSlightlyActive.setBackground(Color.decode("#FFFFFF"));
                btActive.setBackground(Color.decode("#FFFFFF"));
                btVeryActive.setBackground(Color.decode("#FFFFFF"));
            }
        });
        btSlightlyActive.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btSedentary.setBackground(Color.decode("#FFFFFF"));
                btSlightlyActive.setBackground(Color.decode("#B1EA9D"));
                btActive.setBackground(Color.decode("#FFFFFF"));
                btVeryActive.setBackground(Color.decode("#FFFFFF"));
            }
        });
        btActive.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btSedentary.setBackground(Color.decode("#FFFFFF"));
                btSlightlyActive.setBackground(Color.decode("#FFFFFF"));
                btActive.setBackground(Color.decode("#B1EA9D"));
                btVeryActive.setBackground(Color.decode("#FFFFFF"));
            }
        });
        btVeryActive.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btSedentary.setBackground(Color.decode("#FFFFFF"));
                btSlightlyActive.setBackground(Color.decode("#FFFFFF"));
                btActive.setBackground(Color.decode("#FFFFFF"));
                btVeryActive.setBackground(Color.decode("#B1EA9D"));
            }
        });
        MouseAdapter listener = new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                btSedentary.setBackground(Color.decode("#FFFFFF"));
                btSlightlyActive.setBackground(Color.decode("#FFFFFF"));
                btActive.setBackground(Color.decode("#FFFFFF"));
                btVeryActive.setBackground(Color.decode("#FFFFFF"));
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
