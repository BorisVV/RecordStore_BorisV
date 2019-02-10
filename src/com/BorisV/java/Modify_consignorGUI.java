package com.BorisV.java;

import javax.swing.*;
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

    public Modify_consignorGUI(final Consignors_Model consignors_model){
        setContentPane(rootPanel);
        setTitle("Modify Consignor");
        pack();
        setLocation(350, 200);
        setSize(300, 300);
        setVisible(true);
    }

}
