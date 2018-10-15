package com.BorisV.java;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Add_New_AlbumsGUI extends JFrame {
    private JTextField artistTextField;
    private JTextField titleTextField;
    private JPanel rootPanel;
    private JTextField priceTextField;
    private JButton addAlbumButton;

    private JLabel picLabel;

    public Add_New_AlbumsGUI(final Record_Store_Data_Model record_store_data_model, Consignors_Model consignors_model) {
        setContentPane(rootPanel);
        setTitle("Add albums");
        pack();

        setLocation(350, 200);
        setSize(400, 300);
        setVisible(true);

        picLabel.setIcon(new ImageIcon("src/Pictures/Record-Player-icon-72.png"));

        addAlbumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String artist = artistTextField.getText();
                if (artist == null || artist.trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Enter name");
                    return;
                }

                String title = titleTextField.getText();
                if (title == null || title.trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Missing title");
                    return;
                }

                int consignor = 0;


                double price;

                try {
                    price = Double.parseDouble(priceTextField.getText());
                    if (price < 0.0) {
                        throw new NumberFormatException("Enter numeric value greater than 0");
                    }
                } catch (NumberFormatException ne) {
                    JOptionPane.showMessageDialog(rootPane,"Enter album price");
                    priceTextField.setText(null);
                    return;

                }

                boolean insertRow = record_store_data_model.insertRow(artist, title, consignor, price);
                if (!insertRow) {
                    JOptionPane.showMessageDialog(rootPane, "Error adding new data");
                    return;
                }

                artistTextField.setText(null);
                titleTextField.setText(null);
                priceTextField.setText(null);

                if (JOptionPane.YES_OPTION != JOptionPane.showConfirmDialog(rootPane, "Album was added successfully, \nAdd more albums?", "Add more?", JOptionPane.YES_NO_OPTION)) {
                        setVisible(false);
                }
            }
        });
    }
}
