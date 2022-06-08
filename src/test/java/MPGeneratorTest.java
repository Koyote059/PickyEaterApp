import pickyeater.utils.pagesutils.DailyMealPlanColumn;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MPGeneratorTest extends JFrame {
    List<DailyMealPlanColumn> columns = new ArrayList<>();
    JPanel mainPanel = new JPanel(new BorderLayout());
    JPanel buttonsPanel = new JPanel(new GridLayout(1,2));
    JScrollPane scrollPane = new JScrollPane();
    JButton cancelButton = new JButton("Cancel");
    JButton doneButton = new JButton("Done");
    JPanel daysPanel;

    public MPGeneratorTest(){
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(doneButton);
        mainPanel.add(BorderLayout.PAGE_END,buttonsPanel);
        mainPanel.add(BorderLayout.CENTER,scrollPane);
        add(mainPanel);
        draw();
        pack();
    }

    public void draw(){
        mainPanel.removeAll();
        mainPanel.add(BorderLayout.CENTER,columns.get(0).getComponent());
        revalidate();
        System.out.println(mainPanel!=null);
        if(mainPanel!=null) return;
        scrollPane.removeAll();
        scrollPane.setLayout(new ScrollPaneLayout());


        for (int i = 0; i < columns.size(); i++) {
            DailyMealPlanColumn column = columns.get(i);
            Component component = column.getComponent();
            JPanel dailyMealPlanColumnPanel = new JPanel(new BorderLayout());
            JLabel dayLabel = new JLabel("Day " + (i + 1));
            dayLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            dayLabel.setHorizontalAlignment(SwingConstants.CENTER);
            int finalI = i;
            JFrame t = this;
            dayLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if(SwingUtilities.isRightMouseButton(e)){
                        Point point = MouseInfo.getPointerInfo().getLocation();
                        Point framePoint = getLocation();
                        Point realPoint = new Point(point.x - framePoint.x,point.y - framePoint.y);
                        JPopupMenu popupMenu = new JPopupMenu();
                        JMenuItem removeItem = new JMenuItem("Remove");
                        removeItem.addActionListener(e1 -> {
                            columns.remove(finalI);
                            draw();
                        });
                        popupMenu.add(removeItem);

                        popupMenu.show(t,realPoint.x, realPoint.y);

                    }
                }
            });

            JPanel buttonsPanel = new JPanel(new GridLayout(1,2));

            buttonsPanel.add(cancelButton);
            buttonsPanel.add(doneButton);

            mainPanel.add(BorderLayout.PAGE_END, buttonsPanel);

            Component leftArrowComponent;
            try {
                BufferedImage binImage = ImageIO.read(new File("res/images/leftsmallarrow.png"));
                JLabel leftArrow = new JLabel();
                leftArrow.setIcon(new ImageIcon(binImage.getScaledInstance(30,30,Image.SCALE_SMOOTH)));
                leftArrow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                leftArrowComponent = leftArrow;
            } catch (IOException | NullPointerException ignored) {
                leftArrowComponent = new JButton("<");
            }

            leftArrowComponent.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if(e.getClickCount()!=1 || finalI==0) return;
                    Collections.swap(columns,finalI,finalI-1);
                    draw();
                }
            });

            Component rightArrowComponent;
            try {
                BufferedImage binImage = ImageIO.read(new File("res/images/rightsmallarrow.png"));
                JLabel rightArrow = new JLabel();
                rightArrow.setIcon(new ImageIcon(binImage.getScaledInstance(30,30,Image.SCALE_SMOOTH)));
                rightArrow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                rightArrowComponent = rightArrow;
            } catch (IOException | NullPointerException ignored) {
                rightArrowComponent = new JButton("<");
            }

            rightArrowComponent.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if(e.getClickCount()!=1 || finalI==(columns.size()-1)) return;
                    Collections.swap(columns,finalI,finalI+1);
                    draw();
                }
            });

            JPanel dayNamePanel = new JPanel(new BorderLayout());
            dayNamePanel.add(BorderLayout.LINE_START,leftArrowComponent);
            dayNamePanel.add(BorderLayout.CENTER,dayLabel);
            dayNamePanel.add(BorderLayout.LINE_END,rightArrowComponent);

            dailyMealPlanColumnPanel.add(BorderLayout.PAGE_START,dayNamePanel);
            dailyMealPlanColumnPanel.add(BorderLayout.CENTER,component);
            dailyMealPlanColumnPanel.setSize(new Dimension(125,305));
            scrollPane.add(dailyMealPlanColumnPanel);
        }

        JButton addTableButton = new JButton();
        try {
            BufferedImage plusImage = ImageIO.read(new File("res/images/plusicon.png"));
            addTableButton.setIcon(new ImageIcon(plusImage.getScaledInstance(40,40,Image.SCALE_SMOOTH)));
        } catch (IOException | NullPointerException ignored) {
            addTableButton.setText("+");
        }

        addTableButton.addActionListener( e ->{
            columns.add(new DailyMealPlanColumn(this));
            draw();
            revalidate();
        });
        scrollPane.add(addTableButton);
    }

    public static void main(String... args){
        MPGeneratorTest t = new MPGeneratorTest();
        t.setVisible(true);
    }

}
