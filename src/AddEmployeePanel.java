import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class AddEmployeePanel extends EmployeePanel {

    public static List<JComponent> createAddEmployeePanel(DbFunctions DB, Connection conn, Function<String,Void> showCard) {
        List<JComponent> components= new ArrayList<>();

        JLabel TITLE = new JLabel("Modify Employee");
        TITLE.setFont(new Font("Arial", Font.PLAIN, 40));

        JPanel panel = new JPanel(new GridLayout(3, 1)); // 3 rows, 1 column

        // Inner Panel for Title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(TITLE);

        // Inner Panel for Text Labels
        JPanel labelsPanel = new JPanel(new GridLayout(6, 2));
        JLabel employeeNameLabel = new JLabel("Employee Name");


        JLabel employeeSurnameLabel = new JLabel("Employee Surname");


        JLabel employeeSalaryLabel = new JLabel("Employee Salary");



        labelsPanel.add(employeeNameLabel);
        labelsPanel.add(EmployeePanel.employeeNameField);
        labelsPanel.add(employeeSurnameLabel);
        labelsPanel.add(EmployeePanel.employeeSurnameField);
        labelsPanel.add(employeeSalaryLabel);
        labelsPanel.add(EmployeePanel.employeeSalaryField);

        // Inner Panel for Button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addEmployeeBtn = new JButton("Add Employee");
        JButton updateEmployeeBtn = new JButton("Update Employee");


            updateEmployeeBtn.addActionListener(e -> {
                String[] columnNames = {"employee_name", "employee_surname", "employee_salary"};
                String[] columnTypes = {EmployeePanel.employeeNameField.getText(), EmployeePanel.employeeSurnameField.getText(), EmployeePanel.employeeSalaryField.getText()};
                String conditionColumn = "employee_name";
                String conditionValue=  DbFunctions.ConditionString;
                DB.updateData(conn, "employees", columnNames, columnTypes,conditionColumn,conditionValue);
                JPanel employeePanel = EmployeePanel.createEmployeePanel(DB, conn, showCard);
                Menu.cardPanel.add(employeePanel, "Employees");
                Menu.cardLayout.show(Menu.cardPanel, "Employees");
            });

            addEmployeeBtn.addActionListener(e -> {
                String[] columnNames = {"employee_name", "employee_surname", "employee_salary"};
                String[] columnTypes = {EmployeePanel.employeeNameField.getText(), EmployeePanel.employeeSurnameField.getText(), EmployeePanel.employeeSalaryField.getText()};
                DB.insertRow(conn, "employees", columnNames, columnTypes);
                JPanel employeePanel = EmployeePanel.createEmployeePanel(DB, conn, showCard);

                EmployeePanel.employeeNameField.setText("");
                EmployeePanel.employeeSurnameField.setText("");
                EmployeePanel.employeeSalaryField.setText("");

                Menu.cardPanel.add(employeePanel, "Employees");
                Menu.cardLayout.show(Menu.cardPanel, "Employees");

                System.out.println(DbFunctions.ConditionString);

            });


        buttonPanel.add(addEmployeeBtn);
        buttonPanel.add(updateEmployeeBtn);

        // Add Inner Panels to Main Panel
        panel.add(titlePanel);
        panel.add(labelsPanel);
        panel.add(buttonPanel);




        components.addAll(List.of(panel, EmployeePanel.employeeNameField,  EmployeePanel.employeeSurnameField,  EmployeePanel.employeeSalaryField));

        return components;
    }

}
