import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Menu {

    public static JPanel cardPanel;
    public static CardLayout cardLayout;


    public static void showMenu(DbFunctions DB, Connection conn) {

//        GunsPanel gunsPanel = new GunsPanel();
//        AddGunPanel addGunPanel = new AddGunPanel();
//        EmployeePanel employeePanel = new EmployeePanel();
//        CustomersPanel customersPanel = new CustomersPanel();

        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setSize(1920, 1080);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        JMenuBar menuBar = new JMenuBar();
        JMenu employeeMenu = new JMenu("Employee");
        JMenu gunsMenu = new JMenu("Guns");
        JMenu customersMenu = new JMenu("Customers");

        JMenuItem menuItem1 = new JMenuItem("Employee Table");
        JMenuItem menuItem2 = new JMenuItem("Modify Employees");
        JMenuItem menuItem3 = new JMenuItem("Gun Table ");
        JMenuItem menuItem4 = new JMenuItem("Modify Gun");
        JMenuItem menuItem5 = new JMenuItem("Customer Table");
        JMenuItem menuItem6 = new JMenuItem("Modify Customer");

        employeeMenu.add(menuItem1);
        employeeMenu.add(menuItem2);
        gunsMenu.add(menuItem3);
        gunsMenu.add(menuItem4);
        customersMenu.add(menuItem5);
        customersMenu.add(menuItem6);

        menuBar.add(employeeMenu);
        menuBar.add(gunsMenu);
        menuBar.add(customersMenu);

        Function<String,Void> showCard = (String cardName) -> {
            cardLayout.show(cardPanel, cardName);
            return null;
        };



        menuItem1.addActionListener(e -> {
            cardLayout.show(cardPanel, "Employees");
        });
        menuItem2.addActionListener(e -> {
            cardLayout.show(cardPanel, "AddEmployees");

            AddEmployeePanel.employeeNameField.setText("");
            AddEmployeePanel.employeeSurnameField.setText("");
            AddEmployeePanel.employeeSalaryField.setText("");
        });
        menuItem3.addActionListener(e -> {
            cardLayout.show(cardPanel, "Guns");
        });
        menuItem4.addActionListener(e -> {
            cardLayout.show(cardPanel, "AddGuns");

            GunsPanel.gunNameField.setText("");
            GunsPanel.gunPriceField.setText("");
            GunsPanel.gunHandlingField.setText("");
        });
        menuItem5.addActionListener(e -> {
            cardLayout.show(cardPanel, "Customers");
        });
        menuItem6.addActionListener(e -> {
            cardLayout.show(cardPanel, "AddCustomers");

            AddCustomersPanel.customerNameField.setText("");
            AddCustomersPanel.customerEmailField.setText("");
            AddCustomersPanel.customerPhoneField.setText("");
        });

        JPanel mainMenuPanel = createMainMenuPanel(DB, conn);
        JPanel gunPanel = GunsPanel.createGunsPanel(DB, conn,showCard);
        JPanel addInGunPanel = (JPanel) AddGunPanel.createAddGunPanel(DB, conn,showCard).get(0);
        JPanel employee = EmployeePanel.createEmployeePanel(DB, conn,showCard);
        JPanel addEmployee = (JPanel) AddEmployeePanel.createAddEmployeePanel(DB, conn,showCard).get(0);
        JPanel customer = CustomersPanel.createCustomersPanel(DB, conn,showCard);
        JPanel addCustomer = (JPanel) AddCustomersPanel.createAddCustomerPanel(DB, conn,showCard).get(0);

        cardPanel.add(mainMenuPanel, "MainMenu");
        cardPanel.add(gunPanel, "Guns");
        cardPanel.add(addInGunPanel, "AddGuns");
        cardPanel.add(employee, "Employees");
        cardPanel.add(addEmployee, "AddEmployees");
        cardPanel.add(customer, "Customers");
        cardPanel.add(addCustomer, "AddCustomers");


        // frame.add(cardPanel, BorderLayout.CENTER);
        frame.setContentPane(cardPanel);
        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
    }
    private static JPanel createMainMenuPanel(DbFunctions DB, Connection conn) {
        JPanel panel = new JPanel();

        return panel;
    }
}
