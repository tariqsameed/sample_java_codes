package com.check24.books.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.check24.data.Books;
import com.check24.data.BooksDetail;
import com.check24.database.BooksSimilarityPopulator;
import com.check24.database.BooksDatabasePopulator;

@Service
public class BooksHelper {
	
	 private BooksDatabasePopulator daoLayer;
	    
	@Autowired
	public void setBooksDatabasePopulator(BooksDatabasePopulator daoLayer) {
		this.daoLayer = daoLayer;
	}
	    
	
	public List<Books> getAllBooks() {
	      //  return "Hello World From books!";
	    	List<Books> books = new ArrayList<Books>();
			books = daoLayer.getAllBooksRecords();
			return books;
	    }
	
	public BooksDetail getAllBooksDetail(String book) {
	      
    	List<Books> books = new ArrayList<Books>();
		Books book1 = new Books();
		book1 = daoLayer.getBooksRecords(book);
		
		List<String> similarBooks =  new ArrayList<String>();
		similarBooks = new BooksSimilarityPopulator().getSimilarBooks(book);
		
		for(String similarbook: similarBooks) {
			
			Books bookSim = new Books();
			bookSim = daoLayer.getBooksRecords(similarbook);
			books.add(bookSim);
			
		}
		
		BooksDetail booksDetail = new BooksDetail();
		
		booksDetail.setId(book1.getId());
		booksDetail.setDetails(book1.getDetails());
		booksDetail.setName(book1.getName());
		booksDetail.setPrice(book1.getPrice());
	
		booksDetail.setSimilar(books);
		
		return booksDetail;
    }
	
	public Map<String, Object>  auth(String email, String password) { 
	    
    	HashMap<String, Object> map = new HashMap<>();
        map.put("status", 200);
        String[] parts = email.split("@");
        if(!parts[1].contains("test.com")) {
        	   map.put("message", "valid credentials");
        }else {
        	  map.put("message", "invalid user credentials");
        }
        
        return map;
    }

}
