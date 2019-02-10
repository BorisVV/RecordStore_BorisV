package com.BorisV.java;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Add_New_Consignor_GUI extends JFrame{
    private JPanel rootPanel;
    private JTextField nameTextField;
    private JTextField phoneTextField;
    private JButton saveButton;

    public Add_New_Consignor_GUI(final Consignors_Model consignors_model) {
        setContentPane(rootPanel);
        setTitle("Add consignor");
        pack();
        setLocation(350, 200);
        setSize(400, 400);
        setVisible(true);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                if (name == null || name.trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Enter name");
                    return;
                }

                String phone = phoneTextField.getText();
                if (phone == null || phone.trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Missing phone number");
                    return;
                }

                boolean insertRow = consignors_model.insertRowCons(name, phone);
                if (!insertRow) {
                    JOptionPane.showMessageDialog(rootPane, "Error adding new data");
                }

                nameTextField.setText(null);
                phoneTextField.setText(null);

            }
        });
    }


}
