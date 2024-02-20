import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class AddCustomersPanel extends  CustomersPanel{

    public static List<JComponent> createAddCustomerPanel(DbFunctions DB, Connection conn, Function<String,Void> showCard) {
        List<JComponent> components= new ArrayList<>();

        JLabel TITLE = new JLabel("Modify Customer");
        TITLE.setFont(new Font("Arial", Font.PLAIN, 40));

        JPanel panel = new JPanel(new GridLayout(3, 1)); // 3 rows, 1 column

        // Inner Panel for Title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(TITLE);

        // Inner Panel for Text Labels
        JPanel labelsPanel = new JPanel(new GridLayout(6, 2));
        JLabel customerNameLabel = new JLabel("Customer Name");


        JLabel customerEmailLabel = new JLabel("Customer Email");


        JLabel customerPhoneLabel = new JLabel("Customer Phone");


        labelsPanel.add(customerNameLabel);
        labelsPanel.add(CustomersPanel.customerNameField);
        labelsPanel.add(customerEmailLabel);
        labelsPanel.add(CustomersPanel.customerEmailField);
        labelsPanel.add(customerPhoneLabel);
        labelsPanel.add(CustomersPanel.customerPhoneField);

        // Inner Panel for Button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addCustomerBtn = new JButton("Add Customer");
        JButton updateCustomerBtn = new JButton("Update Customer");


        updateCustomerBtn.addActionListener(e -> {
            String[] columnNames = {"customer_name", "customer_email", "customer_phone"};
            String[] columnTypes = {CustomersPanel.customerNameField.getText(), CustomersPanel.customerEmailField.getText(), CustomersPanel.customerPhoneField.getText()};
            String conditionColumn = "customer_name";
            String conditionValue=  DbFunctions.ConditionString;
            DB.updateData(conn, "customers", columnNames, columnTypes,conditionColumn,conditionValue);
            JPanel customersPanel = CustomersPanel.createCustomersPanel(DB, conn, showCard);
            Menu.cardPanel.add(customersPanel, "Customers");
            Menu.cardLayout.show(Menu.cardPanel, "Customers");
        });

        addCustomerBtn.addActionListener(e -> {
            String[] columnNames = {"customer_name", "customer_email", "customer_phone"};
            String[] columnTypes = {CustomersPanel.customerNameField.getText(), CustomersPanel.customerEmailField.getText(), CustomersPanel.customerPhoneField.getText()};
            DB.insertRow(conn, "customers", columnNames, columnTypes);
          //  JPanel employeePanel = EmployeePanel.createEmployeePanel(DB, conn, showCard);
            JPanel customersPanel = CustomersPanel.createCustomersPanel(DB, conn, showCard);

            CustomersPanel.customerNameField.setText("");
            CustomersPanel.customerEmailField.setText("");
            CustomersPanel.customerPhoneField.setText("");

            Menu.cardPanel.add(customersPanel, "Customers");
            Menu.cardLayout.show(Menu.cardPanel, "Customers");

            System.out.println(DbFunctions.ConditionString);

        });


        buttonPanel.add(addCustomerBtn);
        buttonPanel.add(updateCustomerBtn);

        // Add Inner Panels to Main Panel
        panel.add(titlePanel);
        panel.add(labelsPanel);
        panel.add(buttonPanel);




        components.addAll(List.of(panel, CustomersPanel.customerNameField,  CustomersPanel.customerEmailField,  CustomersPanel.customerPhoneField));

        return components;
    }


}
