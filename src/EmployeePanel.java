import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.function.Function;

public class EmployeePanel {

    public static JTextField employeeNameField= new JTextField(30);
    public static JTextField employeeSurnameField= new JTextField(30);
    public static JTextField employeeSalaryField= new JTextField(30);
  public static JPanel createEmployeePanel(DbFunctions DB, Connection conn, Function<String,Void> showCard) {
      JPanel panel = new JPanel(new FlowLayout());

      String[] columnNames = {"employee_name", "employee_surname", "employee_salary"};

      JTable table=  DB.readData(conn,  "employees", columnNames, null,showCard);

      JPanel container = new JPanel(new BorderLayout());

      JScrollPane scrollPane = new JScrollPane(table);

      scrollPane.setPreferredSize(new Dimension(1500, 1000));

      // Add the JScrollPane to the specified container
      panel.add(scrollPane);
      return panel;
    }
}
