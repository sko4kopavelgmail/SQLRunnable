package GUI.Utils;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;


public class TableModel extends AbstractTableModel {

    private ArrayList<Row> rows;
    private ArrayList<String> header;

    public TableModel(ArrayList<Row> rows, ArrayList<String> header) {
        this.rows = rows;
        this.header = header;
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return header.size();
    }

    @Override
    public String getColumnName(int column) {
        if (!header.isEmpty())
            return header.get(column);
        return "<none>";
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return rows.get(rowIndex).getValue(columnIndex);
    }

}
