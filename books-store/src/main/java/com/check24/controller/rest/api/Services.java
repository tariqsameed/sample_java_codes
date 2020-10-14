package com.check24.controller.rest.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.check24.books.helper.BooksHelper;
import com.check24.data.Books;
import com.check24.data.BooksDetail;



@RestController
@EnableAutoConfiguration
@RequestMapping("api")
public class Services {

    @RequestMapping("/")
    String home() {
        return "Hello World From Rest Controller!";
    }

    private BooksHelper apiHelper;
    
    @Autowired
    public void setApiHelper(BooksHelper apiHelper) {
		this.apiHelper = apiHelper;
	}

	@RequestMapping("/books")
    public List<Books> getBooks() {
      //  return "Hello World From books!";
    	List<Books> books = new ArrayList<Books>();
		books = apiHelper.getAllBooks();
		return books;
    }
    
    @RequestMapping("/books/similarity")
    public BooksDetail getBooksDetail(@RequestParam(name = "book") String book) {
      
		BooksDetail booksDetail = new BooksDetail();
		booksDetail = apiHelper.getAllBooksDetail(book);
		return booksDetail;
    }
    
    @PostMapping(path="/validate", produces = "application/json")
    public Map<String, Object>  validate(@RequestParam(name = "email") String email, @RequestParam String password) { 
    
    	Map<String, Object> map = new HashMap<>();
        map = apiHelper.auth(email, password);
 
        return map;
    }

}


