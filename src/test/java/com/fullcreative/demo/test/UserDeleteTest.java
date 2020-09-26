package com.fullcreative.demo.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import org.junit.*;
import org.mockito.BDDMockito.Then;
import org.mockito.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fullcreative.demo.UserDelete;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class UserDeleteTest {

	HttpServletRequest req;
	HttpServletResponse res;
	UserDelete userDelete;

	@Before
	public void mockInit() throws ServletException, IOException {

		req = Mockito.mock(HttpServletRequest.class);
		res = Mockito.mock(HttpServletResponse.class);
		userDelete = Mockito.mock(UserDelete.class);

	}

	@Test
	public void usersTest() throws ServletException, IOException, EntityNotFoundException {

		// assertEquals(true, userDelete.deleteUserDetail("Mallow", "Mallow@15"));

		Mockito.when(req.getParameter("userID")).thenReturn("Mallow");
		Mockito.when(req.getParameter("password")).thenReturn("Mallow@15");

		System.out.println(userDelete.deleteUser(req, res));
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Key key = KeyFactory.createKey("Users", req.getParameter("userID") + " " + req.getParameter("password"));
		Object authUser = datastore.get(key);
		System.out.println(authUser); // []

		// System.out.println(userRegister.usersList());

		// System.out.println(userRegister.login(req, res));

	}

}
