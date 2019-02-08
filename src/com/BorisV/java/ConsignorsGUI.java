package com.BorisV.java;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConsignorsGUI extends JFrame {

    private JTable consignorTable;
    private JButton addNewConsignorButton;
    private JPanel rootPanel;
    private JButton modifyConsig;

//    Consignors_Model consignors_model;
    Add_New_Consignor_GUI add_new_consignor_gui;
    Modify_consignorGUI modify_consignorGUI;

    public ConsignorsGUI(Consignors_Model consignors_model) {
        setContentPane(rootPanel);
        pack();
        setTitle("CONSIGNORS");
        setLocation(200, 200);
        setPreferredSize(new Dimension(300, 300));
//        setSize(300, 250);
        setVisible(true);
        consignorTable.setGridColor(Color.black);
        consignorTable.setModel(consignors_model);

        addNewConsignorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add_new_consignor_gui = new Add_New_Consignor_GUI(consignors_model);
            }
        });

        modifyConsig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentRow = consignorTable.getSelectedRow();

                // If no row is selected.
                if (currentRow == -1) {
                    JOptionPane.showMessageDialog(rootPane, "Please select a row!");
                    return;
                }

                modify_consignorGUI = new Modify_consignorGUI(consignors_model);
            }
        });

    }

}
