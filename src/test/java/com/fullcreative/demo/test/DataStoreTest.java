package com.fullcreative.demo.test;

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
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;
import static org.junit.Assert.assertEquals;

public class DataStoreTest {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	@Before
	public void Init() {

		helper.setUp();

	}


	@Test
	public void usersTest() throws ServletException, IOException, EntityNotFoundException {

		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

		assertEquals(0, ds.prepare(new Query("Demo")).countEntities());
		ds.put(new Entity("Demo"));
		ds.put(new Entity("Demo"));
		assertEquals(2, ds.prepare(new Query("Demo")).countEntities());

		// System.out.println(userRegister.usersList());
		// System.out.println(userRegister.login(req, res));

	}

	
	@After
	public void tearDown() {
		helper.tearDown();
	}

	
}
