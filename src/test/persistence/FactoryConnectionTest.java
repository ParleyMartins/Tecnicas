package test.persistence;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import persistence.FactoryConnection;


public class FactoryConnectionTest {

	FactoryConnection instance;
	
	@Before
	public void setUp ( ) throws Exception {
		instance = FactoryConnection.getInstance();
	}

	@After
	public void tearDown ( ) throws Exception {
		instance = null;
	}

	@Test
	public void testGetInstance ( ) {
		assertNotNull("The object should have been initialized", instance);
	}

	@Test
	public void testSingleton ( ) {
		FactoryConnection instance2 = FactoryConnection.getInstance();
		assertSame("The objects should be the same.", instance, instance2);
	}
	
	@Test
	public void testGetConnection ( ) throws SQLException {
		Connection connection = null;
		connection = instance.getConnection();
		assertNotNull("The object should have been initialized", connection);
	}

}
