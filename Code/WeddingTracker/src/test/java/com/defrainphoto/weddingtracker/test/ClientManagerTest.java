package test.java.com.defrainphoto.weddingtracker.test;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import main.java.com.defrainphoto.weddingtracker.model.Client;
import main.java.com.defrainphoto.weddingtracker.model.ClientManager;

public class ClientManagerTest {
	Session session;
	
	Client client;
	ClientManager clientManager;	
	
	@Before
	public void setUp() {
		client = new Client(null, "Kevin", "Bacon", "123 Main St.", "1-614-322-5656", "kevin@Bacon.com", "n");
		clientManager = new ClientManager();
	}
	
	@Test
	public void testAddClient() {
		
	}
	
	public void testUpdateClient() {
		
	}
	
	public void testDeleteClient() {
		
	}
}
