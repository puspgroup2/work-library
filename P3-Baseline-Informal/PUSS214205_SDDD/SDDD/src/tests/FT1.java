package tests;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import database.DataBase;

class FT1 {
	private DataBase alex;

	@BeforeEach
	void setUp() throws Exception {
		alex = new DataBase(); 
		alex.connect();		//Must change connection in DataBase class so that it connects	
	}

	@AfterEach
	void tearDown() throws Exception {
		alex.disconnect();
	}

	@Test
	void LoginSuccessfull() { 
		assertTrue(alex.checkLogin("Alex", "123ABCde"), "You did not get logged in.");
	}

}



