package pickyeater.UI.RegisterPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register2 extends JFrame {
    private JPanel panel1;
    private JButton btSedentary;
    private JButton btVeryActive;
    private JButton btSlightlyActive;
    private JButton btActive;
    private JPanel mainPanel;

    public Register2() {
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
            }
        });
        btSlightlyActive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                btSedentary.setBackground(Color.white);
                btSlightlyActive.setBackground(Color.green);
                btActive.setBackground(Color.white);
                btVeryActive.setBackground(Color.white);
            }
        });
        btActive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                btSedentary.setBackground(Color.white);
                btSlightlyActive.setBackground(Color.white);
                btActive.setBackground(Color.green);
                btVeryActive.setBackground(Color.white);
            }
        });
        btVeryActive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                btSedentary.setBackground(Color.white);
                btSlightlyActive.setBackground(Color.white);
                btActive.setBackground(Color.white);
                btVeryActive.setBackground(Color.green);
            }
        });
    }
}
