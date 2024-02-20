import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.function.Function;

public class CustomersPanel {

    public static JTextField customerNameField= new JTextField(30);
    public static JTextField customerEmailField= new JTextField(30);
    public static JTextField customerPhoneField= new JTextField(30);
    public static JPanel createCustomersPanel(DbFunctions DB, Connection conn, Function<String,Void> showCard){
        JPanel panel = new JPanel(new FlowLayout());

        String[] columnNames = {"customer_name", "customer_email", "customer_phone"};

        JTable table=  DB.readData(conn,  "customers", columnNames, null,showCard);

        JPanel container = new JPanel(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setPreferredSize(new Dimension(1500, 1000));

        // Add the JScrollPane to the specified container
        panel.add(scrollPane);
        return panel;
    }
}
