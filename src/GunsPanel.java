import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.function.Function;

public class GunsPanel {

    public static JTextField gunNameField= new JTextField(30);
    public static JTextField gunPriceField= new JTextField(30);
    public static JTextField gunHandlingField= new JTextField(30);

   public static int selectedRow;
    public static JPanel createGunsPanel(DbFunctions DB, Connection conn, Function<String,Void> showCard){
        JPanel panel = new JPanel(new FlowLayout());

        String[] columnNames = {"gun_name", "gun_price","gun_handling"};

        JTable table=  DB.readData(conn,  "guns", columnNames, "gunID",showCard);

        selectedRow = table.getSelectedRow();

        JPanel container = new JPanel(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setPreferredSize(new Dimension(1500, 1000));

        // Add the JScrollPane to the specified container
        panel.add(scrollPane);
        return panel;
    }
}
