package pickyeater.UI.pages.app.groceriespage;

import pickyeater.UI.pages.app.MainFrame;
import pickyeater.UI.pages.app.PickyPage;
import pickyeater.UI.themes.filehandler.ThemeHandler;
import pickyeater.UI.themes.filehandler.ThemesEnum;
import pickyeater.utils.Resources;
import pickyeater.utils.pagesutils.WindowCloseListener;
import pickyeater.UI.pages.leftbuttons.PanelButtonsConverter;
import pickyeater.UI.themes.ColorButtons;
import pickyeater.basics.food.Ingredient;
import pickyeater.basics.food.Quantity;
import pickyeater.basics.groceries.Groceries;
import pickyeater.basics.groceries.GroceriesCheckList;
import pickyeater.executors.GroceriesExecutor;
import pickyeater.utils.ValuesConverter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GroceriesPage extends PickyPage {
    private final List<Ingredient> ingredients = new ArrayList<>();
    private JPanel mainPanel;
    private JButton btSettings;
    private JButton btDailyProgress;
    private JButton btUser;
    private JButton btGroceries;
    private JButton btDiet;
    private JLabel binLabel;
    private JTable ingredientsTable;
    private JLabel priceLabel;
    private final GroceriesExecutor groceriesExecutor;
    private GroceriesCheckList groceriesCheckList;

    public GroceriesPage(GroceriesExecutor groceriesExecutor, JFrame parent) {
        super(parent);
        this.groceriesExecutor = groceriesExecutor;
        binLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        try {
            BufferedImage binImage;
            if (ThemeHandler.ReadTheme() == ThemesEnum.LIGHT_THEME) {
                binImage = ImageIO.read(new File(Resources.getLTBinIcon()));
            } else {
                binImage = ImageIO.read(new File(Resources.getDTBinIcon()));
            }
            binLabel.setIcon(new ImageIcon(binImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        } catch (IOException | NullPointerException ignored) {
            binLabel.setText("X");
        }
        parent.addWindowListener(new WindowCloseListener() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(groceriesCheckList!=null) groceriesExecutor.saveGroceries(groceriesCheckList);
            }
        });
        binLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int result = JOptionPane.showConfirmDialog(mainPanel, "Are you sure you want to delete it?", "Deleting  " + "groceries", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    groceriesExecutor.deleteGroceries();
                    groceriesCheckList=null;
                    PickyPage page = new UnavailableGroceriesPage(parent);
                    page.showPage();
                }
            }
        });
        ColorButtons.ColorLeftButtons(btGroceries, btDailyProgress, btSettings, btDiet, btUser);
        setNavigationMenuListeners();
        Optional<Groceries> groceriesOptional = groceriesExecutor.getGroceries();
        if (groceriesOptional.isEmpty())
            throw new RuntimeException("Error in Groceries Database!");
        Groceries groceries = groceriesOptional.get();
        groceriesCheckList = groceries.generateCheckList();
        ingredientsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0)
                    return Boolean.class;
                return super.getColumnClass(columnIndex);
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }
        };

        ingredientsTable.getTableHeader().setReorderingAllowed(false);
        ingredientsTable.setModel(model);
        model.addTableModelListener(e -> {
            int column = e.getColumn();
            int row = e.getFirstRow();
            if (column != 0 || row < 0)
                return;
            Boolean checked = (Boolean) model.getValueAt(row, column);
            if (checked) {
                groceriesCheckList.checkIngredient(ingredients.get(row));
            } else {
                groceriesCheckList.unCheckIngredient(ingredients.get(row));
            }
        });
        priceLabel.setText("Price: " + ValuesConverter.convertFloat((float) groceries.getPrice()) + " €");
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
    }

    private void setNavigationMenuListeners() {
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

    private void draw() {
        DefaultTableModel model = (DefaultTableModel) ingredientsTable.getModel();
        model.addColumn("Taken");
        model.addColumn("Name");
        model.addColumn("Quantity");
        for (Ingredient ingredient : groceriesCheckList.getNeededIngredients()) {
            Quantity quantity = ingredient.getQuantity();
            Object[] row = new Object[]{false, ingredient.getName(), ValuesConverter.convertFloat(quantity.getAmount()) + " " + ValuesConverter.convertQuantityTypeValue(quantity.getQuantityType())};
            model.addRow(row);
            ingredients.add(ingredient);
        }
        for (Ingredient ingredient : groceriesCheckList.getTakenIngredients()) {
            Quantity quantity = ingredient.getQuantity();
            Object[] row = new Object[]{true, ingredient.getName(), ValuesConverter.convertFloat(quantity.getAmount()), ValuesConverter.convertQuantityTypeValue(quantity.getQuantityType())};
            model.addRow(row);
            ingredients.add(ingredient);
        }
        for (Ingredient ingredient : groceriesCheckList.getMissingIngredients()) {
            Quantity quantity = ingredient.getQuantity();
            Object[] row = new Object[]{false, ingredient.getName(), ValuesConverter.convertFloat(quantity.getAmount()), ValuesConverter.convertQuantityTypeValue(quantity.getQuantityType())};
            model.addRow(row);
            ingredients.add(ingredient);
        }
        ingredientsTable.setModel(model);
    }
}
