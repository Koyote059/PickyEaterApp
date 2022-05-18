import javax.swing.*;
import javax.swing.table.TableCellRenderer;

public class CustomCell {
    public static void main( String [] args ) {
        Object [] columnNames = new Object[]{ "Id", "Quantity" };
        Object [][] data        = new Object[][]{ {"06", 1}, {"08", 2} };

        JTable table = new JTable( data, columnNames ) {
            public TableCellRenderer getCellRenderer(int row, int column ) {
                return new PlusMinusCellRenderer();
            }
        };

        table.setRowHeight( 32 );
        showFrame( table );
    }

    private static void showFrame( JTable table ) {
        JFrame f = new JFrame("Custom Cell Renderer sample" );
        f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        f.add( new JScrollPane( table ) );
        f.pack();
        f.setVisible( true );
    }
}