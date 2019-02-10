package com.BorisV.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Modify_consignorGUI extends JFrame {
    private JPanel rootPanel;
    private JTextField oldNameText;
    private JTextField newNameText;
    private JTextField oldPhoneText;
    private JTextField newPhoneText;
    private JLabel oldName;
    private JLabel newName;
    private JLabel oldPhone;
    private JLabel newPhone;
    private JButton saveButton;
    private JButton cancelButton;

    public Modify_consignorGUI(Consignors_Model consignors_model) {
        setContentPane(rootPanel);
        setTitle("Modify Consignor");
        pack();
        setLocation(350, 250);
        setSize(300, 300);
        setVisible(true);
        newNameText.requestFocus();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newName = newNameText.getText();
                String newPhoneNo = newPhoneText.getText();
                if (newName == null || newName.trim().equals("")){
                    JOptionPane.showMessageDialog(rootPane, "Enter a new name");
                    newNameText.requestFocus();
                    return;
                }
                if (newPhoneNo == null || newPhoneNo.trim().equals("")){
                    JOptionPane.showMessageDialog(rootPane, "Enter a new phone number!");
                    newPhoneText.requestFocus();
                    return;
                }

                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

    }
}
