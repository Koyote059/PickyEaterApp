package pickyeater.UI.RegisterPage;

import com.toedter.calendar.JCalendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class Register1 extends JFrame{
    private JPanel mainPanel;
    private JPanel panelZeroOne;
    private JTextField tfName;
    private JButton btMale;
    private JButton btFemale;
    private JButton btContinue;
    private JTextField tfWeight;
    private JTextField tfHeight;
    private JTextField tfBodyfat;
    private JCalendar jCalendar;

    String name;
    String sex;
    double weight;
    double height;
    LocalDate brithDay;
    double bodyFat;

    public Register1() {
        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        tfName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                name = actionEvent.getActionCommand();
            }
        });
        tfWeight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                weight = Double.parseDouble(actionEvent.getActionCommand());
            }
        });
        tfHeight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                height = Double.parseDouble(actionEvent.getActionCommand());
            }
        });

        tfBodyfat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                bodyFat = Double.parseDouble(actionEvent.getActionCommand());
            }
        });
        btMale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                sex = actionEvent.getActionCommand();
            }
        });
        btFemale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                sex = actionEvent.getActionCommand();
            }
        });
        btContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (name != null && sex != null && weight != 0 && height != 0 && brithDay != null){
                    System.out.println("OK!");
                } else {
                    JOptionPane.showMessageDialog(panelZeroOne, "Missing stuff");
                }
            }
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(Register1::new);
    }

}
