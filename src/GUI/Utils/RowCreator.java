package GUI.Utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class RowCreator {

    private ArrayList<Row> rows;

    public ArrayList<Row> getRows() {
        return rows;
    }

    public void setRows(ArrayList<Row> rows) {
        this.rows = rows;
    }

    public RowCreator(ResultSet rs) throws SQLException {
        rows = new ArrayList<>();
        while (rs.next()){
            ArrayList<Object> tmpRow = new ArrayList<>();
            ResultSetMetaData rsmd = rs.getMetaData();
            for (int i = 1; i < rsmd.getColumnCount()+1;i++){
                switch (rsmd.getColumnType(i)){
                    case Types.DOUBLE:
                        tmpRow.add(rs.getDouble(i));
                        break;
                    case Types.INTEGER:
                        tmpRow.add(rs.getInt(i));
                        break;
                    case Types.VARCHAR:
                        tmpRow.add(rs.getString(i));
                        break;
                    case Types.DATE:
                        tmpRow.add(rs.getDate(i));
                        break;
                    case Types.TIME:
                        tmpRow.add(rs.getTime(i));
                        break;
                }

            }
            rows.add(new Row(collectionToMass(tmpRow)));
        }
    }

    private Object[] collectionToMass(ArrayList<Object> strings){
        Object[] objects = new Object[strings.size()];
        for (int i = 0; i < strings.size(); i ++)
            objects[i] = strings.get(i);
        return objects;
    }
}
