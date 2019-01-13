package com.BorisV.java;


import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Consignors_Model extends AbstractTableModel {

    private int row_CountCon = 0;
    private int col_CountCon = 0;
    ResultSet resultSetCon;

    Consignors_Model(ResultSet rs) {
        this.resultSetCon = rs;
        sepUp();
    }

    private void sepUp() {
        count_Rows();

        try {
            col_CountCon = resultSetCon.getMetaData().getColumnCount();

        } catch (SQLException e) {
            System.out.println("Error counting columns " + e);
        }

    }


    @Override
    public int getRowCount() {
         count_Rows();
        return row_CountCon;
    }

    @Override
    public int getColumnCount() {
        return col_CountCon;

    }

    @Override
    public Object getValueAt(int rowConsig, int colConsig) {
        try {
            resultSetCon.absolute(rowConsig + 1);
            Object conS = resultSetCon.getObject(colConsig + 1);
            return conS.toString();

        } catch (SQLException e) {
            e.printStackTrace();

            return e.toString();
        }
    }


    public void count_Rows() {
        row_CountCon = 0;
        //Move cursor to the start of the first row
        try {
            resultSetCon.beforeFirst();
            //moves cursor forward one row
            while ((resultSetCon.next())) {
                row_CountCon++;
            }
            resultSetCon.beforeFirst();
        } catch (SQLException e) {
            System.out.println("Error counting rows " + e);
        }
    }

    @Override
    public String getColumnName(int colName){
        try {
            return resultSetCon.getMetaData().getColumnName(colName + 1);
        } catch (SQLException se) {
            System.out.println("Error fetching column name" + se);
            return "?";
        }
    }

    public void update_ResultSet(ResultSet rsConsignors) {
        resultSetCon = rsConsignors;
        sepUp();

    }

    public boolean insertRowCons(String name, String phone) {
        try{
            resultSetCon.moveToInsertRow();
            resultSetCon.updateString(Record_Store_Data_Base.CONSIGNOR_NAME, name);
            resultSetCon.updateString(Record_Store_Data_Base.CONSIGNOR_PHONE, phone);
            resultSetCon.insertRow();
            resultSetCon.moveToCurrentRow();
            fireTableDataChanged();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



}

