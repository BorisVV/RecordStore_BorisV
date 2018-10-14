package com.BorisV.java;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Record_Store_Data_Model  extends AbstractTableModel {

    //add row and  columns
    private int col_CountRec = 0;
    private int row_CountRec = 0;
    private ResultSet resultSetRec;

    Record_Store_Data_Model(ResultSet rs) {
        this.resultSetRec = rs;
        sepUp();
    }

    private void sepUp() {
        count_RowsRec();

        try {
            col_CountRec = resultSetRec.getMetaData().getColumnCount();

        } catch (SQLException e) {
            System.out.println("Error counting columns " + e);
        }

    }

    public void update_ResultSet(ResultSet new_ResultSet) {
        resultSetRec = new_ResultSet;
        sepUp();
    }

    public boolean deleteRowRecordSt(int rowRec){
        try {
            resultSetRec.absolute(rowRec + 1);
            resultSetRec.deleteRow();
            fireTableDataChanged();
            return true;
        }catch (SQLException se) {
            System.out.println("Delete row error " + se);
            return false;
        }
    }

    public boolean isCellEditable(int row, int col){
            return true;
        }

    public void count_RowsRec() {
        row_CountRec = 0;
        try {
            resultSetRec.beforeFirst();
            while ((resultSetRec.next())) {
                row_CountRec++;
            }
            resultSetRec.beforeFirst();
        } catch (SQLException e) {
            System.out.println("Error counting record rows " + e);
        }
    }




    @Override
    public int getRowCount() {
        count_RowsRec();
        return row_CountRec;
    }

    @Override
    public int getColumnCount() {
        return col_CountRec;
    }

    @Override
    public Object getValueAt(int rowRec, int colRec) {
        try {
            resultSetRec.absolute(rowRec+1);
            Object rec = resultSetRec.getObject(colRec+1);
            return rec.toString();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error Objet getValue");

            return e.toString();
        }
    }


    public boolean insertRow(String artist, String title, int consignor, double price) {

        try {
            //Move to insert row, insert the appropriate data in each column, insert the row, move cursor back to where it was before we started
            resultSetRec.moveToInsertRow();
            resultSetRec.updateString(Record_Store_Data_Base.ARTIST_NAME, artist);
            resultSetRec.updateString(Record_Store_Data_Base.ALBUM_NAME, title);
            resultSetRec.updateInt(Record_Store_Data_Base.CONSIGNOR_ID, consignor);
            resultSetRec.updateDouble(Record_Store_Data_Base.PRICE, price);
            resultSetRec.insertRow();
            resultSetRec.moveToCurrentRow();
            fireTableDataChanged();
            return true;

        } catch (SQLException e) {
            System.out.println("Error adding row");
            return false;
        }
    }

    @Override
    public String getColumnName(int col){
        try {
            return resultSetRec.getMetaData().getColumnName(col + 1);
        } catch (SQLException se) {
            System.out.println("Error fetching column names" + se);
            return "?";
        }
    }

    public void setValueAt(Object newValue, int row, int col) {
        int newID = Integer.parseInt(newValue.toString());

        try {
            resultSetRec.absolute(row + 1);
            resultSetRec.updateInt(Record_Store_Data_Base.CONSIGNOR_ID, newID);
            resultSetRec.updateRow();
            fireTableDataChanged();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}






