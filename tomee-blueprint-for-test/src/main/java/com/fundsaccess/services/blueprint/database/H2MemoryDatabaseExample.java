package com.fundsaccess.services.blueprint.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fundsaccess.services.blueprint.data.Person;

// H2 In-Memory Database Example shows about storing the database contents into memory.

public class H2MemoryDatabaseExample {

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

    
    
    
    static {
        try {
//            insertWithStatement();
            insertWithPreparedStatement();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//    public static void main(String[] args) throws Exception {
//        try {
////            insertWithStatement();
//            insertWithPreparedStatement();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    
    public List<Person> getAllRecords() {
    	try {
            Connection connection = getDBConnection();
            String SelectQuery = "select * from PERSON";

            PreparedStatement selectPreparedStatement = null;

            selectPreparedStatement = connection.prepareStatement(SelectQuery);
            ResultSet rs = selectPreparedStatement.executeQuery();
            System.out.println("H2 In-Memory Database inserted through PreparedStatement");
            
            List<Person> people = new ArrayList<Person>();
            while (rs.next()) {
                System.out.println("Id " + rs.getInt("id") + " Name " + rs.getString("name"));
                people.add(new Person(rs.getInt("id"),rs.getString("name")));
            }
            selectPreparedStatement.close();
            return people;
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		return Collections.emptyList();
    	}
    }

    private static void insertWithPreparedStatement() throws SQLException {
        Connection connection = getDBConnection();
        PreparedStatement createPreparedStatement = null;
        PreparedStatement insertPreparedStatement = null;
        PreparedStatement selectPreparedStatement = null;

        String CreateQuery = "CREATE TABLE PERSON(id int primary key, name varchar(255))";
        String InsertQuery = "INSERT INTO PERSON" + "(id, name) values" + "(?,?)";
        String SelectQuery = "select * from PERSON";

        try {
            connection.setAutoCommit(false);

            createPreparedStatement = connection.prepareStatement(CreateQuery);
            createPreparedStatement.executeUpdate();
            createPreparedStatement.close();

            insertPreparedStatement = connection.prepareStatement(InsertQuery);
            insertPreparedStatement.setInt(1, 1);
            insertPreparedStatement.setString(2, "Jose");
            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();

            selectPreparedStatement = connection.prepareStatement(SelectQuery);
            ResultSet rs = selectPreparedStatement.executeQuery();
            System.out.println("H2 In-Memory Database inserted through PreparedStatement");
            while (rs.next()) {
                System.out.println("Id " + rs.getInt("id") + " Name " + rs.getString("name"));
            }
            selectPreparedStatement.close();

            connection.commit();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }
//
//    private static void insertWithStatement() throws SQLException {
//        Connection connection = getDBConnection();
//        Statement stmt = null;
//        try {
//            connection.setAutoCommit(false);
//            stmt = connection.createStatement();
//            stmt.execute("CREATE TABLE PERSON(id int primary key, name varchar(255))");
//            stmt.execute("INSERT INTO PERSON(id, name) VALUES(1, 'Anju')");
//            stmt.execute("INSERT INTO PERSON(id, name) VALUES(2, 'Sonia')");
//            stmt.execute("INSERT INTO PERSON(id, name) VALUES(3, 'Asha')");
//
//            ResultSet rs = stmt.executeQuery("select * from PERSON");
//            System.out.println("H2 In-Memory Database inserted through Statement");
//            while (rs.next()) {
//                System.out.println("Id " + rs.getInt("id") + " Name " + rs.getString("name"));
//            }
//
//            stmt.execute("DROP TABLE PERSON");
//            stmt.close();
//            connection.commit();
//        } catch (SQLException e) {
//            System.out.println("Exception Message " + e.getLocalizedMessage());
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            connection.close();
//        }
//    }

    private static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }
}