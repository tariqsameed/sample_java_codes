package com.check24.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.check24.cosine.InvalidDataException;
import com.check24.cosine.SimilarityCalculator;

@Repository
public class BooksSimilarityPopulator {
	
    private static SimilarityCalculator similarityCalculator;
    
    @Autowired
    public void setSimilarityCalculator(SimilarityCalculator similarityCalculator) {
    	BooksSimilarityPopulator.similarityCalculator = similarityCalculator;
    }
    
	
	 /* Books Similarity File Records */
    private static final String TABLE_NAME_BOOKS_SIMILARITY = "SimilarBooks";
    private static final String BOOK1 = "book1";
    private static final String BOOK2 = "book2";
    private static final String SIMILARITY = "similarity";
	
	private static Connection connection;
    static {
            connection = BooksDatabasePopulator.getDBConnection();
            
    }
    
    public static List<String> getDistinctUsers() {
    	try {
            String SelectQuery = "select DISTINCT user from BooksViews";

            PreparedStatement selectPreparedStatement = null;

            selectPreparedStatement = connection.prepareStatement(SelectQuery);
            ResultSet rs = selectPreparedStatement.executeQuery();
            
            List<String> users = new ArrayList<String>();
            while (rs.next()) {
                 users.add(rs.getString("user"));
                
            }
            selectPreparedStatement.close();
            return users;
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		return Collections.emptyList();
    	}
    }
    
    public static List<String> getDistinctBooks() {
    	try {
            String SelectQuery = "select DISTINCT book from BooksViews";

            PreparedStatement selectPreparedStatement = null;

            selectPreparedStatement = connection.prepareStatement(SelectQuery);
            ResultSet rs = selectPreparedStatement.executeQuery();
            
            List<String> books = new ArrayList<String>();
            while (rs.next()) {
                 books.add(rs.getString("book"));
                
            }
            selectPreparedStatement.close();
            return books;
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		return Collections.emptyList();
    	}
    }
    
    
    public static Integer[] getBookMapping(String bookId){
    	
    	List<String> users = new ArrayList<String>();
    	
    	try {
            String SelectQuery = "select user from BooksViews WHERE book=?";

            PreparedStatement selectPreparedStatement = null;

            selectPreparedStatement = connection.prepareStatement(SelectQuery);
            selectPreparedStatement.setString(1,bookId);
            ResultSet rs = selectPreparedStatement.executeQuery();
            
            while (rs.next()) {
                //  System.out.println(" User " + rs.getString("user"));
                 users.add(rs.getString("user"));
                
            }
            selectPreparedStatement.close();
 
    	} catch (Exception ex) {
    		ex.printStackTrace();
    
    	}
    	
        return getBookVector(users);
    }
    
    /**
    *
    * @param buyers the list of names of all the buyers that bought this book
    * @return
    */
   public static Integer[] getBookVector(List<String> buyers){
	   
	   List<String> uniqueUsers = getDistinctUsers();
	   
	   String[] allUsers = new String[uniqueUsers.size()];

       int i = 0;
       for (String user : uniqueUsers) {
           allUsers[i] = user;
           i++;
       }
	   
       Integer bookVector[] = new Integer[allUsers.length];

       for (int j = 0; j < bookVector.length; j++) {
           if (buyers.contains(allUsers[j])){
               bookVector[j] = 1;
           } else {
               bookVector[j] = 0;
           }
       }
       
       return bookVector;
   }
   
   public static void computeSimilarity() {
	   
	   List<String> userBook = getDistinctBooks();
	   
	   String[] books = new String[userBook.size()];
       
       int k = 0;
       for (String user : userBook) {
    	   books[k] = user;
           k++;
       }
	   
	   createBooksDetailSimilarityTable();
	   
		for (int i = 0; i < books.length; i++) {
			for (int j = 0; j < books.length; j++) {
				if (i != j) {
					try {
						double value = similarityCalculator.getSimilarity(getBookMapping(books[i]),
								getBookMapping(books[j]));
						insertSimilarityInDataBase(books[i], books[j], value);
					} catch (InvalidDataException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		   
   }
   
   public static void insertSimilarityInDataBase(String book1, String book2, double value) {
	    
		 PreparedStatement insertPreparedStatement = null; 
		 String InsertQuery = "INSERT INTO "+TABLE_NAME_BOOKS_SIMILARITY+ "("+BOOK1+", "+BOOK2+","+SIMILARITY+") values" + "(?,?,?)";
		 
		 try { 
			 connection.setAutoCommit(false);
		 
		  insertPreparedStatement = connection.prepareStatement(InsertQuery);
		  
		  insertPreparedStatement.setString(1, book1); 
		  insertPreparedStatement.setString(2, book2); 
		  insertPreparedStatement.setDouble(3, value);
		  insertPreparedStatement.executeUpdate();
		  insertPreparedStatement.close();
		 
			connection.commit();
		} catch (SQLException e) {
			System.out.println("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
   }
   
   public static void createBooksDetailSimilarityTable(){
	   
		PreparedStatement createPreparedStatement = null;
		String CreateQuery = "CREATE TABLE " + TABLE_NAME_BOOKS_SIMILARITY + "(" + BOOK1 + " varchar(255), " + BOOK2
				+ " varchar(255), " + SIMILARITY + " DOUBLE)";
		try {
			createPreparedStatement = connection.prepareStatement(CreateQuery);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
	}
   
   public List<String> getSimilarBooks(String bookId){
	   
	   List<String> similarBooks = new ArrayList<String>();
	   String SelectQuery = "SELECT * FROM SimilarBooks WHERE BOOK1=? order by similarity DESC";
       PreparedStatement selectPreparedStatement = null;

       try {
		selectPreparedStatement = connection.prepareStatement(SelectQuery);
		selectPreparedStatement.setString(1,bookId);
	    ResultSet rs = selectPreparedStatement.executeQuery();
	    
	    while (rs.next()) {
            //System.out.println(" Book " + rs.getString("book2"));
            similarBooks.add(rs.getString("book2"));
        }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      
	return similarBooks;
   }

}
