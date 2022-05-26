package pickyeater.UI.app.groceriespage;

import pickyeater.UI.app.MainFrame;
import pickyeater.UI.app.PickyPage;
import pickyeater.UI.app.groceriespage.utils.WindowCloseListener;
import pickyeater.UI.leftbuttons.PanelButtonsConverter;
import pickyeater.basics.food.Ingredient;
import pickyeater.basics.food.Quantity;
import pickyeater.basics.groceries.Groceries;
import pickyeater.basics.groceries.GroceriesCheckList;
import pickyeater.executors.GroceriesExecutor;
import pickyeater.themes.ColorButtons;
import pickyeater.utils.MouseClickListener;
import pickyeater.utils.ValuesConverter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

public class GroceriesPage extends PickyPage  {
    private JPanel mainPanel;
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btDiet;
    private JLabel binLabel;
    private JTable ingredientsTable;
    private JLabel priceLabel;
    private GroceriesExecutor groceriesExecutor;
    private GroceriesCheckList groceriesCheckList;
    private Groceries groceries;
    private final List<Ingredient> ingredients = new ArrayList<>();


    public GroceriesPage(GroceriesExecutor groceriesExecutor, JFrame parent) {
        super(parent);
        this.groceriesExecutor = groceriesExecutor;
        binLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        try {
            BufferedImage binImage = ImageIO.read(new File("res/images/binIcon.png"));
            binLabel.setIcon(new ImageIcon(binImage.getScaledInstance(30,30,Image.SCALE_SMOOTH)));
        } catch (IOException | NullPointerException ignored) {

        }

        parent.addWindowListener(new WindowCloseListener() {
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
                    PickyPage page = new UnavailableGroceriesPage(parent);
                    page.showPage();
                }
            }
        });


        new ColorButtons().ColorLeftButtons(btGroceries, btDailyProgress, btSettings, btDiet, btUser);

        setNavigationMenuListeners();
        Optional<Groceries> groceriesOptional = groceriesExecutor.getGroceries();
        if(groceriesOptional.isEmpty()) throw new RuntimeException("Error in Groceries Database!");
        groceries = groceriesOptional.get();
        groceriesCheckList = groceries.generateCheckList();
        ingredientsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultTableModel model = new DefaultTableModel(){
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if(columnIndex==0) return Boolean.class;
                return super.getColumnClass(columnIndex);
            }
        };
        ingredientsTable.setModel(model);
        model.addTableModelListener(e -> {
            int column = e.getColumn();
            int row = e.getFirstRow();
            if(column!=0 || row<0) return;
            Boolean checked = (Boolean) model.getValueAt(row,column);
            if(checked){
                groceriesCheckList.checkIngredient(ingredients.get(row));
            } else {
                groceriesCheckList.unCheckIngredient(ingredients.get(row));
            }
        });

        priceLabel.setText("Price: " + ValuesConverter.convertFloat((float) groceries.getPrice()) + " €");
        setLayout(new BorderLayout());
        add(mainPanel,BorderLayout.CENTER);
    }

    private void draw() {
        DefaultTableModel model = (DefaultTableModel) ingredientsTable.getModel();
        model.addColumn("Taken");
        model.addColumn("Name");
        model.addColumn("Quantity");
        model.addColumn("");


        for (Ingredient ingredient : groceriesCheckList.getNeededIngredients()) {
            Quantity quantity = ingredient.getQuantity();
            Object[]  row= new Object[]{
                    false,ingredient.getName(), ValuesConverter.convertFloat(quantity.getAmount()), ValuesConverter.convertQuantityTypeValue(quantity.getQuantityType())
            };
            model.addRow(row);
            ingredients.add(ingredient);
        }
        for (Ingredient ingredient : groceriesCheckList.getTakenIngredients()) {
            Quantity quantity = ingredient.getQuantity();
            Object[]  row= new Object[]{
                    true,ingredient.getName(), ValuesConverter.convertFloat(quantity.getAmount()), ValuesConverter.convertQuantityTypeValue(quantity.getQuantityType())
            };
            model.addRow(row);
            ingredients.add(ingredient);
        }
        for (Ingredient ingredient : groceriesCheckList.getMissingIngredients()) {
            Quantity quantity = ingredient.getQuantity();
            Object[] row= new Object[]{
                    false,ingredient.getName(), ValuesConverter.convertFloat(quantity.getAmount()), ValuesConverter.convertQuantityTypeValue(quantity.getQuantityType())
            };
            model.addRow(row);
            ingredients.add(ingredient);
        }

        ingredientsTable.setModel(model);
    }

    private void setNavigationMenuListeners(){

        ActionListener listener = e -> {
            groceriesExecutor.saveGroceries(groceriesCheckList);
            String cmd = e.getActionCommand();
            setVisible(false);
            MainFrame.changePage(new PanelButtonsConverter(cmd).Convert());
        };
        btSettings.addActionListener(listener);
        btDailyProgress.addActionListener(listener);
        btUser.addActionListener(listener);
        btDiet.addActionListener(listener);
    }


    @Override
    public void showPage() {
        draw();
        super.showPage();
    }
}
