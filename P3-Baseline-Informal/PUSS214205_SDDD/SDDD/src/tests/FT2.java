package tests;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import beans.UserBean;
import database.DataBase;

class FT2 {
	private DataBase malte;
	private UserBean malteBean;

	@BeforeEach
	void setUp() throws Exception {
		malte = new DataBase(); //Must change connection in DataBase class so that it connects
		malte.connect();
		malteBean = new UserBean();
		malteBean.populateBean("Malte", "123ABCde");
	}

	@AfterEach
	void tearDown() throws Exception {
		malte.disconnect();
	}

	@Test
	void UnsuccessfullLogin() {
		assertFalse(malte.checkLogin(malteBean), "You got logged in.");
	}

}

