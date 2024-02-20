import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static javax.swing.JOptionPane.OK_OPTION;

public class AddGunPanel extends GunsPanel{

    public static List<JComponent> createAddGunPanel(DbFunctions DB, Connection conn, Function<String,Void> showCard) {
        List<JComponent> components= new ArrayList<>();

        JLabel TITLE = new JLabel("Modify Gun");
        TITLE.setFont(new Font("Arial", Font.PLAIN, 40));

        JPanel panel = new JPanel(new GridLayout(3, 1)); // 3 rows, 1 column

        // Inner Panel for Title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(TITLE);

        // Inner Panel for Text Labels
        JPanel labelsPanel = new JPanel(new GridLayout(6, 2));
        JLabel gunNameLabel = new JLabel("Gun Name");


        JLabel gunPriceLabel = new JLabel("Gun Price");


        JLabel gunHandlingLabel = new JLabel("Gun Handling");



        labelsPanel.add(gunNameLabel);
        labelsPanel.add(gunNameField);
        labelsPanel.add(gunPriceLabel);
        labelsPanel.add(gunPriceField);
        labelsPanel.add(gunHandlingLabel);
        labelsPanel.add(gunHandlingField);

        // Inner Panel for Button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addGunBtn = new JButton("Add Gun");
        JButton updateGunBtn = new JButton("Update Gun");


            addGunBtn.addActionListener(e -> {
                String[] columnNames = {"gun_name", "gun_price", "gun_handling"};
                String[] columnTypes = {gunNameField.getText(), gunPriceField.getText(), gunHandlingField.getText()};
                DB.insertRow(conn, "guns", columnNames, columnTypes);
                JPanel gunsPanel = GunsPanel.createGunsPanel(DB, conn, showCard);

                gunNameField.setText("");
                gunPriceField.setText("");
                gunHandlingField.setText("");

                Menu.cardPanel.add(gunsPanel, "Guns");
                Menu.cardLayout.show(Menu.cardPanel, "Guns");

                System.out.println(DbFunctions.ConditionString);


            });



            updateGunBtn.addActionListener(e -> {
                String[] columnNames = {"gun_name", "gun_price", "gun_handling"};
                String[] columnTypes = {gunNameField.getText(), gunPriceField.getText(), gunHandlingField.getText()};
                String conditionColumn = "gun_name";
                String conditionValue=  DbFunctions.ConditionString;
                DB.updateData(conn, "guns", columnNames, columnTypes,conditionColumn,conditionValue);
                JPanel gunsPanel = GunsPanel.createGunsPanel(DB, conn, showCard);
                Menu.cardPanel.add(gunsPanel, "Guns");
                Menu.cardLayout.show(Menu.cardPanel, "Guns");


            });

        buttonPanel.add(addGunBtn);
        buttonPanel.add(updateGunBtn);

        // Add Inner Panels to Main Panel
        panel.add(titlePanel);
        panel.add(labelsPanel);
        panel.add(buttonPanel);
       // panel.add(addGunBtn, BorderLayout.SOUTH);

      //  panel.add(updateGunBtn, BorderLayout.SOUTH);



        components.addAll(List.of(panel,gunNameField,  gunPriceField,  gunHandlingField));

        return components;
    }



}
