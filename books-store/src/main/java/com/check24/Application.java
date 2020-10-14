package com.check24;//import org.springframework.boot.*;
//import org.springframework.boot.autoconfigure.*;
//import org.springframework.web.bind.annotation.*;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.check24.database.BooksSimilarityPopulator;
import com.check24.parser.BooksParser;
import com.check24.parser.BooksViewsParser;

@SpringBootApplication
public class Application {

    static final Logger logger = Logger.getLogger(Application.class);
    
	private static BooksSimilarityPopulator bookSimilarityDatabase;
	
	
	@Autowired
    public static void setBookSimilarityDatabase(BooksSimilarityPopulator bookSimilarityDatabase) {
		Application.bookSimilarityDatabase = bookSimilarityDatabase;
	}

	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        init();
    }
    
    public static void init() {
		/* read all csv files and save in h2 database */
      	logger.info("Data Loading to H2 Database");
      	
      	try {
			
			/* load books data */
			new BooksParser().readCsvFile("books.csv");
			/* load books views */
		    new BooksViewsParser().readCsvFile("books-views.csv");
		    /* create similarity database */
		    bookSimilarityDatabase.computeSimilarity();
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
    	logger.info("Data Loaded to H2 Database");
  
    }

}