import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.function.Function;


public class DbFunctions {
    //CONNECT TO DATABASE FUNCTION

   public static String ConditionString;
    public Connection connect_to_db(String dbname,String user, String pass)
    {
        Connection conn= null;
        try
        {
            Class.forName("org.postgresql.Driver");
            conn= DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+dbname,user,pass);

            if(conn!=null)
            {
                System.out.println("Connection establised");
            }
            else
            {
                System.out.println("Connection failed");
            }

        }catch(Exception e)
        {
            System.out.println(e);
        }
        return conn;
    }
    // CREATE TABLE FUNCTION
    public void createTable(Connection conn, String tableName, String[] columnNames, String[] columnTypes, String primaryKey, String[] foreignKeys) {
        if (columnNames.length != columnTypes.length) {
            throw new IllegalArgumentException("Number of column names must be equal to the number of column types");
        }

        Statement statement;
        try {
            StringBuilder queryBuilder = new StringBuilder("CREATE TABLE " + tableName + " (");

            // Add an auto-incrementing primary key
            queryBuilder.append("id SERIAL PRIMARY KEY, ");

            for (int i = 0; i < columnNames.length; i++) {
                queryBuilder.append(columnNames[i]).append(" ").append(columnTypes[i]);
                if (i < columnNames.length - 1) {
                    queryBuilder.append(", ");
                }
            }

            if (primaryKey != null && !primaryKey.isEmpty()) {
                queryBuilder.append(", PRIMARY KEY (").append(primaryKey).append(")");
            }

            if (foreignKeys != null && foreignKeys.length > 0) {
                for (String foreignKey : foreignKeys) {
                    queryBuilder.append(", FOREIGN KEY (").append(foreignKey).append(") REFERENCES ").append(foreignKey).append("(id)");
                }
            }

            queryBuilder.append(")");

            statement = conn.createStatement();
            statement.executeUpdate(queryBuilder.toString());

            System.out.println("Table created");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //INSERT ROW FUNCTION
    public void insertRow(Connection conn, String table_name, String[] columnNames, String[] values) {
        if (columnNames.length != values.length) {
            throw new IllegalArgumentException("Number of column names must be equal to the number of values");
        }

        Statement statement;
        try {
            StringBuilder queryBuilder = new StringBuilder("INSERT INTO " + table_name + " (");
            for (int i = 0; i < columnNames.length; i++) {
                queryBuilder.append(columnNames[i]);
                if (i < columnNames.length - 1) {
                    queryBuilder.append(", ");
                }
            }

            queryBuilder.append(") VALUES (");

            for (int i = 0; i < values.length; i++) {
                queryBuilder.append("'").append(values[i]).append("'");
                if (i < values.length - 1) {
                    queryBuilder.append(", ");
                }
            }

            queryBuilder.append(")");

            statement = conn.createStatement();
            statement.executeUpdate(queryBuilder.toString());

            System.out.println("Row inserted");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //READ DATA FROM DATABASE AND PRIINT IN CONSOLE
    public JTable readData(Connection conn, String table_name, String[] columnNames, String excludeColumn, Function<String,Void> showCard) {
        try {
            StringBuilder queryBuilder = new StringBuilder("SELECT ");
            for (int i = 0; i < columnNames.length; i++) {
                if (!columnNames[i].equals(excludeColumn)) {
                    queryBuilder.append(columnNames[i]);
                    if (i < columnNames.length - 1) {
                        queryBuilder.append(", ");
                    }
                }
            }

            queryBuilder.append(" FROM ").append(table_name);

            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(queryBuilder.toString());

            // Create a DefaultTableModel to hold the data
            DefaultTableModel tableModel = new DefaultTableModel();

            // Add column names to the model (excluding the excluded column)
            for (String columnName : columnNames) {
                if (!columnName.equals(excludeColumn)) {
                    String modifiedColumnName = formatColumnName(columnName);

                    tableModel.addColumn(modifiedColumnName);
                }
            }

            tableModel.addColumn("Update");
            tableModel.addColumn("Delete");

            // Add data to the model
            while (resultSet.next()) {
                Object[] rowData = new Object[columnNames.length];
                int columnIndex = 0;
                for (int i = 0; i < columnNames.length; i++) {
//                    if(i==columnNames.length-2)
//                    {
//                        rowData[columnIndex++] = "Update";
//                    }
//                    else if(i==columnNames.length-1)
//                    {
//                        rowData[columnIndex++] = "Delete";
//                    }
                     if (!columnNames[i].equals(excludeColumn)) {
                        rowData[columnIndex++] = resultSet.getString(columnNames[i]);
                    }

                }

                tableModel.addRow(rowData);
            }

            // Create a JTable with the DefaultTableModel
            JTable table = new JTable(tableModel){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            table.setPreferredScrollableViewportSize(new Dimension(1800, 300));
            table.setRowHeight(30);
            resultSet.close();
            statement.close();
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    int row = table.rowAtPoint(evt.getPoint());
                    int col = table.columnAtPoint(evt.getPoint());
                    if (row >= 0 && col >= 0) {
                        if (col == columnNames.length) {

                            showCard.apply("Add"+table_name.substring(0,1).toUpperCase()+table_name.substring(1));
                            ConditionString= (String) table.getValueAt(row, 0);

                            GunsPanel.gunNameField.setText((String) table.getValueAt(row, 0));
                            GunsPanel.gunPriceField.setText((String) table.getValueAt(row, 1));
                            GunsPanel.gunHandlingField.setText((String) table.getValueAt(row, 2));

                            CustomersPanel.customerNameField.setText((String) table.getValueAt(row, 0));
                            CustomersPanel.customerEmailField.setText((String) table.getValueAt(row, 1));
                            CustomersPanel.customerPhoneField.setText((String) table.getValueAt(row, 2));

                            EmployeePanel.employeeNameField.setText((String) table.getValueAt(row, 0));
                            EmployeePanel.employeeSurnameField.setText((String) table.getValueAt(row, 1));
                            EmployeePanel.employeeSalaryField.setText((String) table.getValueAt(row, 2));



                            System.out.println(ConditionString);

                        }
                             if (col == columnNames.length + 1) {
                            int response = JOptionPane.showConfirmDialog(
                                    null,
                                    "Are you sure you want to delete?",
                                    "Confirmation",
                                    JOptionPane.YES_NO_OPTION
                            );

                            if (response == JOptionPane.YES_OPTION) {
                                String conditionColumn = columnNames[0];
                                String conditionValue = (String) table.getValueAt(row, 0);
                                deleteRow(conn, table_name, conditionColumn, conditionValue);
                                tableModel.removeRow(row);
                           //     System.out.println(OK_OPTION);
                            }
                        }
                    }
                }
            });
            return table;
        } catch (Exception e) {
            e.printStackTrace();
            return null; // or throw an exception based on your application's error handling strategy
        }
    }


    //UPDATE DATA FUNCTION
    public void updateData(Connection conn, String table_name, String[] columnNames, String[] newValues, String conditionColumn, String conditionValue) {
        if (columnNames.length != newValues.length) {
            System.out.println("Number of columns and values should be the same");
            return;
        }

        Statement statement;
        try {
            StringBuilder query = new StringBuilder("UPDATE " + table_name + " SET ");

            for (int i = 0; i < columnNames.length; i++) {
                query.append(columnNames[i]).append(" = '").append(newValues[i]).append("'");
                if (i < columnNames.length - 1) {
                    query.append(", ");
                }
            }

            query.append(" WHERE ").append(conditionColumn).append(" = '").append(conditionValue).append("'");

            statement = conn.createStatement();
            statement.executeUpdate(query.toString());

            System.out.println("Data updated");

            // Consider adding an ORDER BY clause when retrieving data
            String selectQuery = "SELECT * FROM " + table_name + " ORDER BY your_primary_key_column";
            // Execute your SELECT query and print or process the results as needed

        } catch (Exception e) {
            System.out.println(e);
        }
    }


    //DELETE ROW FUNCTION
    public void deleteRow(Connection conn, String table_name, String conditionColumn, String conditionValue) {
        Statement statement;
        try {
            String query = "DELETE FROM " + table_name + " WHERE " + conditionColumn + " = '" + conditionValue + "'";

            statement = conn.createStatement();
            int rowCount = statement.executeUpdate(query);

            if (rowCount > 0) {
                System.out.println("Row deleted successfully");
            } else {
                System.out.println("No rows deleted. Row with specified condition not found.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    private String formatColumnName(String columnName) {
        // Replace underscores with spaces
        String[] words = columnName.split("_");
        StringBuilder formattedName = new StringBuilder();
        for (String word : words) {
            if (word.length() > 0) {
                // Capitalize the first letter of each word
                formattedName.append(word.substring(0, 1).toUpperCase())
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        // Remove trailing space
        return formattedName.toString().trim();
    }
    public void updateData2(Connection conn, String table_name, String columnName, String newValue, String conditionColumn, String conditionValue) {
        Statement statement;
        try {
            String query = "UPDATE " + table_name + " SET " + columnName + " = '" + newValue + "' WHERE " + conditionColumn + " = '" + conditionValue + "'";

            statement = conn.createStatement();
            statement.executeUpdate(query);

            System.out.println("Data updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

