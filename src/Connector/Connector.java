package Connector;

import java.sql.*;
import java.util.ArrayList;

public class Connector {

    private static final String url = "jdbc:mysql://localhost:3306/runnablesql?useUnicode=true&characterEncoding=utf8";
    private static final String user = "root";
    private static final String password = "12345";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;
    private static ResultSetMetaData rsmd;

    public Connector() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
        } catch (SQLException ex) {
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getRs(String tableName) {
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM " + tableName);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSetMetaData getTableMetaData(String tableName) {
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM " + tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            return rs.getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> getHeader(String tableName) {
        ArrayList<String> header = new ArrayList<>();
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM " + tableName);
            rsmd = rs.getMetaData();
            for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
                header.add(rsmd.getColumnName(i));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return header;
    }

    public int insertIntoUser(String name, String age, String birthday) {
        try {
            stmt = con.createStatement();
            String query = String.format("INSERT INTO user(user.Name,user.age, user.birthday) " +
                    "values('%s',%s,'%s')",name,age,birthday);
            return stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 81;
    }

    public int insertIntoNote(String note, String priority, String time){
        try {
            stmt = con.createStatement();
            String query = String.format("INSERT INTO notes\n" +
                    "(" +
                    "node,\n" +
                    "priority,\n" +
                    "TIME)\n" +
                    "VALUES\n" +
                    "'%s',\n" +
                    "'%s',\n" +
                    "'%s'",note,priority,time);
            return stmt.executeUpdate(query);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 100;
    }

    public int insertIntoNode(String userId, String noteId,String percent){
        try {
            stmt = con.createStatement();
            String query = String.format("INSERT INTO `runnablesql`.`node`(`User_idUser`,`Notes_idNotes`,`percent`)VALUES\n" +
                    "(%s,%s,%s)",userId,noteId,percent);
            return stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 112;
    }
}
