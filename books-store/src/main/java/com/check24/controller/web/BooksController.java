package com.check24.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("task")
public class BooksController {

	@RequestMapping("/login")
	public String home() {
		return "login.html";
	}

	@RequestMapping("/books")
	public String books() {
		return "books.html";
	}

	@RequestMapping("/details")
	public String bookSimilarity() {
		return "bookSimilarity.html";
	}

}
