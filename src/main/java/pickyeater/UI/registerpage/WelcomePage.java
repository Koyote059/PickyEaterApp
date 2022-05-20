package pickyeater.UI.registerpage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WelcomePage extends JFrame implements ActionListener {
    private JPanel mainPanel;
    private JPanel coloredPanel;
    private JLabel txtWelcome;
    Image logo;
    Timer timer;
    int xVelocity = 1;
    int yVelocity = 2;
    int x = 250;
    int y = 250;


    public WelcomePage(){
        txtWelcome.setFont(new Font("Bauhaus 93", Font.PLAIN, 50));
        txtWelcome.setForeground(Color.decode("#32AB5E"));

        setContentPane(mainPanel);
        setSize(677, 507);
        setResizable(false);
        coloredPanel.setBackground(Color.decode("#B1EA9D"));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        logo = new ImageIcon("res/images/PEL.png").getImage();
        timer = new Timer(1, this);
        timer.start();

        coloredPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                setVisible(false);
                new Register1();
            }
        });
    }

    public void paint(Graphics g){
        super.paint(g);

        Graphics2D g2D = (Graphics2D) g;

        g2D.drawImage(logo, x, y, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (x >= 677-logo.getWidth(null) | x <= 0){
            xVelocity = xVelocity * -1;
        }
        if (y >= 507-logo.getWidth(null) | y <= 0){
            yVelocity = yVelocity * -1;
        }
        x = x + xVelocity;
        y = y + yVelocity;
        repaint();
    }
}
