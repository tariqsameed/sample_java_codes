package com.check24.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.check24.data.Books;
import com.check24.database.BooksDatabasePopulator;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@Component
public class BooksParser {

	public void readCsvFile(String filename) throws Exception {
		
		Resource resource = new ClassPathResource(filename);

		InputStream input = resource.getInputStream();

		File file = resource.getFile();
		
		if(file == null) {
			throw new Exception("resource not found: " + filename);
		}else {
			
			  try {
				  FileReader reader = new FileReader(file);
                  BufferedReader br = new BufferedReader(reader);
				  br.readLine(); // skip the first line
				  CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
				  CSVReader csvReader = new CSVReaderBuilder(br).withCSVParser(parser).build();
				  
				// Read all data at once 
			        List<String[]> allData = csvReader.readAll(); 
			  
			        List<Books> books = new ArrayList<Books>();
			         
			        for (String[] row : allData) { 
			        	
			        	Books book = new Books(row [0], row[1], row[2], row[3], row[4]);

			        	books.add(book);
			        } 
				
			        BooksDatabasePopulator.insertBooksWithPreparedStatement(books);
			  } catch (IOException ex) {
				    ex.printStackTrace();
			  }
		}
		
	
	}
}
