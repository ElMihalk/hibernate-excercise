package pl.edu.agh.mwo.hibernate;

import java.sql.*;

public class JDBCMain {
    public static void main(String[] args) {
        executeSQL();
    }
    
    private static void executeSQL() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:school.db", "", "");
            Statement statement = connection.createStatement();
            
            String query = "SELECT * FROM schools";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println("School name: " + resultSet.getString("name"));
                System.out.println("       address: " + resultSet.getString("address"));
            }
            resultSet.close();
            
            String query2 = "SELECT * FROM schoolClasses";
            ResultSet resultSet2 = statement.executeQuery(query2);
            while (resultSet2.next()) {
                System.out.println("Class profile: " + resultSet2.getString("profile"));
                System.out.println("    startYear: " + resultSet2.getInt("startYear"));
                System.out.println("  currentYear: " + resultSet2.getInt("currentYear"));
            }
            resultSet2.close();
            
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
