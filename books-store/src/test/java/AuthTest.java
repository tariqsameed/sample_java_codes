import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import com.check24.books.helper.BooksHelper;

public class AuthTest {
	
	@Test
	public void emailValidationTest() {
		
		BooksHelper booksHelper = new BooksHelper();
		Map<String, Object> map = booksHelper.auth("check24@test.com", "abc");
		assertEquals("invalid user credentials", map.get("message"));
	}

}
