package com.check24.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.check24.data.Books;
import com.check24.data.BooksViews;

@Repository
public class BooksDatabasePopulator {
	
	/* Database Configuration */
	    private static final String DB_DRIVER = "org.h2.Driver";
	    private static final String DB_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
	    private static final String DB_USER = "";
	    private static final String DB_PASSWORD = "";
	    
	/* Books File Records */ /* id;name;details;price;image from csv file*/
	    private static final String TABLE_NAME_BOOKS = "BOOKS";
	    private static final String ID = "id";
	    private static final String NAME = "name";
	    private static final String DETAILS = "details";
	    private static final String PRICE = "price";
	    private static final String IMAGE = "image";
	    
	 /* Books Views File Records */
	    private static final String TABLE_NAME_BOOKS_VIEWS = "BOOKSVIEWS";
	    private static final String BOOK = "book";
	    private static final String USER = "user";

	    
	    public static void insertBooksWithPreparedStatement(List<Books> books) throws SQLException {
		
	     Connection connection = getDBConnection();
		 PreparedStatement createPreparedStatement = null;
		 PreparedStatement insertPreparedStatement =null;
		 
		 String CreateQuery = "CREATE TABLE "+TABLE_NAME_BOOKS+"(id  varchar(255) primary key, "+NAME+" varchar(255), "+DETAILS+" varchar(255), "+PRICE+" varchar(255),"+IMAGE+" varchar(255))"; 
		 String InsertQuery = "INSERT INTO "+TABLE_NAME_BOOKS+ "(id, "+NAME+", "+DETAILS+", "+PRICE+", "+IMAGE+") values" + "(?,?,?,?,?)";
		 
		 try { 
			 connection.setAutoCommit(false);
		 
		  createPreparedStatement = connection.prepareStatement(CreateQuery);
		  createPreparedStatement.executeUpdate(); createPreparedStatement.close();
		  
		  for(Books book: books) {
		  insertPreparedStatement = connection.prepareStatement(InsertQuery);
		  
		  insertPreparedStatement.setString(1, book.getId()); 
		  insertPreparedStatement.setString(2, book.getName());
		  insertPreparedStatement.setString(3, book.getDetails()); 
		  insertPreparedStatement.setString(4, book.getPrice()); 
		  insertPreparedStatement.setString(5, book.getImage()); 
		  insertPreparedStatement.executeUpdate();
		  insertPreparedStatement.close();
		 }
		    
			connection.commit();
		} catch (SQLException e) {
			System.out.println("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		 
	    }
	    
	    public static void insertBooksViewsWithPreparedStatement(List<BooksViews> booksViews) throws SQLException {
			
			 Connection connection = getDBConnection();
			 PreparedStatement createPreparedStatement = null;
			 PreparedStatement insertPreparedStatement =null;
			 
			 String CreateQuery = "CREATE TABLE "+TABLE_NAME_BOOKS_VIEWS+"("+ BOOK+" varchar(255), "+USER+" varchar(255))"; 
			 String InsertQuery = "INSERT INTO "+TABLE_NAME_BOOKS_VIEWS+ "("+BOOK+", "+USER+") values" + "(?,?)";
			 
			 try { 
				 connection.setAutoCommit(false);
			 
			  createPreparedStatement = connection.prepareStatement(CreateQuery);
			  createPreparedStatement.executeUpdate(); createPreparedStatement.close();
			  
			  for(BooksViews bookView: booksViews) {
			  insertPreparedStatement = connection.prepareStatement(InsertQuery);
			  
			  insertPreparedStatement.setString(1, bookView.getBook()); 
			  insertPreparedStatement.setString(2, bookView.getUser()); 
			  insertPreparedStatement.executeUpdate();
			  insertPreparedStatement.close();
			 }
			    
				connection.commit();
			} catch (SQLException e) {
				System.out.println("Exception Message " + e.getLocalizedMessage());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				connection.close();
			}
			 
		    }
	    
	    public String[] getAllBooksNames() {
	    	
	    	try {
	            Connection connection = getDBConnection();
	            String SelectQuery = "select id from BOOKS";

	            PreparedStatement selectPreparedStatement = null;

	            selectPreparedStatement = connection.prepareStatement(SelectQuery);
	            ResultSet rs = selectPreparedStatement.executeQuery();
	            
	            List<String> books = new ArrayList<String>();
	            while (rs.next()) {
	   
	                books.add(rs.getString(ID));
	                
	            }
	            selectPreparedStatement.close();
	            
	            String[] allBooks = new String[books.size()];
	            
	            int i = 0;
	            for (String user : books) {
	            	allBooks[i] = user;
	                i++;
	            }
	            
	            return allBooks;
	    	} catch (Exception ex) {
	    		ex.printStackTrace();

	    	}
	  
			return null;
	    	
	    }
	    
	    public List<Books> getAllBooksRecords() {
	    	try {
	            Connection connection = getDBConnection();
	            String SelectQuery = "select * from BOOKS";

	            PreparedStatement selectPreparedStatement = null;

	            selectPreparedStatement = connection.prepareStatement(SelectQuery);
	            ResultSet rs = selectPreparedStatement.executeQuery();
	            
	            List<Books> books = new ArrayList<Books>();
	            while (rs.next()) {
	      
	                Books book = new Books();
	                book.setId(rs.getString(ID));
	                book.setName(rs.getString(NAME));
	                book.setDetails(rs.getString(DETAILS));
	                book.setPrice(rs.getString(PRICE));
	                book.setImage(rs.getString(IMAGE));
	                books.add(book);
	                
	            }
	            selectPreparedStatement.close();
	            return books;
	    	} catch (Exception ex) {
	    		ex.printStackTrace();
	    		return Collections.emptyList();
	    	}
	    }
	    
	    public Books getBooksRecords(String bookId) {
	    	try {
	            Connection connection = getDBConnection();
	            String SelectQuery = "SELECT * FROM BOOKS WHERE ID=?";
	            PreparedStatement selectPreparedStatement = null;

	            selectPreparedStatement = connection.prepareStatement(SelectQuery);
	            selectPreparedStatement.setString(1,bookId);
	            ResultSet rs = selectPreparedStatement.executeQuery();
	            
	            Books book = new Books();
	            while (rs.next()) {
	                
	                book.setId(rs.getString(ID));
	                book.setName(rs.getString(NAME));
	                book.setDetails(rs.getString(DETAILS));
	                book.setPrice(rs.getString(PRICE));
	                book.setImage(rs.getString(IMAGE));
	                    
	            }
	            selectPreparedStatement.close();
	            return book;
	    	} catch (Exception ex) {
	    		ex.printStackTrace();
	    		return new Books();
	    	}
	    }
	     
	    static Connection getDBConnection() {
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
