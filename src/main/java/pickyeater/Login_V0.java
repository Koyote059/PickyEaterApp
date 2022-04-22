package pickyeater;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Login_V0 extends JFrame {
    private JButton btS;
    private JButton btSA;
    private JButton btA;
    private JButton btVA;
    private JPanel mainPanel;
    private JPanel panelS;
    private JPanel panelSA;
    private JPanel panelA;
    private JPanel panelVA;
    private JPanel pPanelSA;
    private JPanel pPanelS;
    private JPanel pPanelA;
    private JPanel pPanelVA;

    public Login_V0() {
        setContentPane(mainPanel);
        setTitle("Picky Eater - Login Page");
        setSize(450, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        // IMAGE IN SEDENTARY
        try {
            BufferedImage myPicture = ImageIO.read(new File("C:\\Users\\claud\\Desktop\\Programazione a " +
                    "Oggetti\\Picky Eater\\Logo\\In-App Images\\Lifestyle\\074-Sedentary_(W).png"));

            myPicture = resize(myPicture, 100, 100);

            JLabel picLabel = new JLabel(new ImageIcon(myPicture));

            pPanelS.add(picLabel);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "something went wrong");
        }

        // IMAGE IN SLIGHTLY ACTIVE
        try {
            BufferedImage myPicture = ImageIO.read(new File("C:\\Users\\claud\\Desktop\\Programazione a " +
                    "Oggetti\\Picky Eater\\Logo\\In-App Images\\Lifestyle\\066-Slightly Active.png"));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            pPanelSA.add(picLabel);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "something went wrong");
        }

        // IMAGE IN ACTIVE
        try {
            BufferedImage myPicture = ImageIO.read(new File("C:\\Users\\claud\\Desktop\\Programazione a " +
                    "Oggetti\\Picky Eater\\Logo\\In-App Images\\Lifestyle\\027-Active.png"));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            picLabel.setSize(10, 10);
            pPanelA.add(picLabel);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "something went wrong");
        }

        // IMAGE IN VERY ACTIVE
        try {
            BufferedImage myPicture = ImageIO.read(new File("C:\\Users\\claud\\Desktop\\Programazione a " +
                    "Oggetti\\Picky Eater\\Logo\\In-App Images\\Lifestyle\\067-Very Active.png"));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            picLabel.setSize(10, 10);
            pPanelVA.add(picLabel);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "something went wrong");
        }

        setVisible(true);

        btS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Selected: Sedentary Lifestyle");

            }
        });

        btSA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Selected: Slightly Active Lifestyle");
            }
        });

        btA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Selected: Active Lifestyle");
            }
        });

        btVA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Selected: Very Active Lifestyle");
            }
        });
    }

    public static void main(String[] args) {
        new Login_V0();
    }

    // COPIED AND PASTED
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();

        //g2d.drawImage(tmp, 0, 0, dimg.getWidth(), dimg.getHeight(), null);

        g2d.drawImage(img.getScaledInstance(100, -1, Image.SCALE_SMOOTH), 0, 0, null);

        g2d.dispose();

        return dimg;
    }
}




// FAILED ATTEMPTS: (2 - Image)

/*
 // Work on panelS - frameS - labelS
 labelS = new JLabel();
 frameS = new JFrame();

 ImageIcon imageS= new ImageIcon("C:\\Users\\claud\\Desktop\\Programazzione a Oggetti\\Picky " +
 "Eater\\Logo\\In-App Images\\Lifestyle\\074-Sedentary_(W).png");

 labelS.setIcon(imageS);

 panelS.add(labelS);

 frameS.add(panelS);

 frameS.setTitle("PUT IMAGE_S");
 frameS.pack();
 frameS.setVisible((true));
 */

/*
        frameS.setTitle("Sedentary");
        frameS.add(new JLabel(imageS));

        frameS.add(panelS, BorderLayout.CENTER);
        frameS.pack();      // ?
        frameS.setVisible(true);

        frameS.add(labelS);
*/