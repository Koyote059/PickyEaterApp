package pickyeater.UI.app.groceriespage;

import pickyeater.UI.app.groceriespage.utils.WindowCloseListener;
import pickyeater.UI.leftbuttons.MainButton;
import pickyeater.UI.leftbuttons.PanelButtonsConverter;
import pickyeater.basics.food.Ingredient;
import pickyeater.basics.food.Quantity;
import pickyeater.basics.groceries.Groceries;
import pickyeater.basics.groceries.GroceriesCheckList;
import pickyeater.executors.GroceriesExecutor;
import pickyeater.utils.MouseClickListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class GroceriesPage extends JFrame{
    private JPanel mainPanel;
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btFood;
    private JButton btDiet;
    private JLabel binLabel;
    private JTable tableIngredients;

    private GroceriesExecutor groceriesExecutor;
    private GroceriesCheckList groceriesCheckList;


    public GroceriesPage(GroceriesExecutor groceriesExecutor) {
        this.groceriesExecutor = groceriesExecutor;
        binLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        try {
            BufferedImage binImage = ImageIO.read(new File("res/images/binIcon.png"));
            binLabel.setIcon(new ImageIcon(binImage.getScaledInstance(20,20,Image.SCALE_SMOOTH)));
        } catch (IOException | NullPointerException ignored) {

        }

        this.addWindowListener(new WindowCloseListener() {
            @Override
            public void windowClosing(WindowEvent e) {
                groceriesExecutor.saveGroceries(groceriesCheckList);
            }
        });
        binLabel.addMouseListener(new MouseClickListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int result = JOptionPane.showConfirmDialog(binLabel,"Are you sure you want to delete it?","Deleting  groceries",JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION){
                    groceriesExecutor.deleteGroceries();
                    setVisible(false);
                    new UnavailableGroceriesPage();
                }
            }
        });

        btDailyProgress.setBackground(Color.decode("#FFFFFF"));
        btDiet.setBackground(Color.decode("#FFFFFF"));
        btFood.setBackground(Color.decode("#FFFFFF"));
        btGroceries.setBackground(Color.decode("#B1EA9D"));
        btUser.setBackground(Color.decode("#FFFFFF"));
        btSettings.setBackground(Color.decode("#FFFFFF"));

        setContentPane(mainPanel);
        setSize(677, 507);    //pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Todo save before exiting
        setVisible(true);
        setNavigationMenuListeners();
        Optional<Groceries> groceriesOptional = groceriesExecutor.getGroceries();
        if(groceriesOptional.isEmpty()) throw new RuntimeException("Error in Groceries Database!");
        groceriesCheckList = groceriesOptional.get().generateCheckList();
        tableIngredients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        draw();
    }

    private void draw() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("");
        model.addColumn("Name");
        model.addColumn("Quantity");

        for (Ingredient ingredient : groceriesCheckList.getNeededIngredients()) {
            Quantity ingredientQuantity = ingredient.getQuantity();
            Object[]  row= new Object[]{
                    false,ingredient.getName(), ingredientQuantity.getAmount(),ingredientQuantity.getQuantityType()
            };
            model.addRow(row);
        }
        for (Ingredient ingredient : groceriesCheckList.getTakenIngredients()) {
            Quantity ingredientQuantity = ingredient.getQuantity();
            Object[]  row= new Object[]{
                    false,ingredient.getName(), ingredientQuantity.getAmount(),ingredientQuantity.getQuantityType()
            };
            model.addRow(row);
        }
        for (Ingredient ingredient : groceriesCheckList.getMissingIngredients()) {
            Quantity ingredientQuantity = ingredient.getQuantity();
            Object[]  row= new Object[]{
                    false,ingredient.getName(), ingredientQuantity.getAmount(),ingredientQuantity.getQuantityType()
            };
            model.addRow(row);
        }

        tableIngredients.setModel(model);
    }

    private void setNavigationMenuListeners(){
        ActionListener listener = e -> {
            groceriesExecutor.saveGroceries(groceriesCheckList);
            String cmd = e.getActionCommand();
            setVisible(false);
            new MainButton(new PanelButtonsConverter(cmd).Convert());
        };
        btSettings.addActionListener(listener);
        btDailyProgress.addActionListener(listener);
        btUser.addActionListener(listener);
        btDiet.addActionListener(listener);
        btFood.addActionListener(listener);
    }

}
