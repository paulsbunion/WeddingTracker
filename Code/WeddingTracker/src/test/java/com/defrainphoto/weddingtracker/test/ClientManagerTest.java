package com.defrainphoto.weddingtracker.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import com.defrainphoto.weddingtracker.model.Client;
import com.defrainphoto.weddingtracker.model.ClientManager;
import com.defrainphoto.weddingtracker.model.HibernateUtil;

public class ClientManagerTest {
	Session session;
	Client client;
	ClientManager clientManager;	
	
	@Before
	public void setUp() {
		clientManager = new ClientManager();
	}
	
	@Test
	public void testAddClient() {
		List<Client> clientList = new LinkedList<Client>();
		client = new Client(null, "Kevin", "Bacon", "123 Main St.", "1-614-322-5656", "kevin@Bacon.com", "n");
		clientList.add(client);
		
		clientManager.addClient(client);
		
		Client retreived;
		retreived = clientManager.getClientByName(client);
		
		assertEquals("did not add Client", retreived, client);
		
		// clean up
		cleanUp(clientList);
	}

	@Test
	public void testUpdateClientById() {
		
	}
	
	@Test
	public void testUpdateClientFirstName() {
		List<Client> clientList = new ArrayList<Client>();
		
		try {
			client = new Client(null, "Kevin", "Bacon", "123 Main St.", "1-614-322-5656", "kevin@Bacon.com", "n");
			clientList.add(client);
			
			clientManager.addClient(client);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Client updatedClient = null;

		try {
			updatedClient = clientManager.setClientFirstName(client, "no longer Kevin");
			clientList.add(updatedClient);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Client newClient = clientManager.getClientById(client);
		
		assertTrue("Did not update First Name", updatedClient != null);
		assertEquals("Entities are not the same" + client.toString() + " vs. " + newClient.toString(),
				client.getFirstName(), newClient.getFirstName());
		
		// clean up
		cleanUp(clientList);
	}
	
	@Test
	public void testUpdateClientLastName() {
		List<Client> clientList = new ArrayList<Client>();
		
		try {
			client = new Client(null, "Kevin", "Bacon", "123 Main St.", "1-614-322-5656", "kevin@Bacon.com", "n");
			clientList.add(client);
			
			clientManager.addClient(client);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Client updatedClient = null;

		try {
			updatedClient = clientManager.setClientLastName(client, "no longer Bacon");
			clientList.add(updatedClient);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Client newClient = clientManager.getClientById(client);
		
		assertTrue("Did not update Last Name", updatedClient != null);
		assertEquals("Entities are not the same" + client.toString() + " vs. " + newClient.toString(),
				client.getLastName(), newClient.getLastName());
		
		// clean up
		cleanUp(clientList);
	}
	
	@Test
	public void testUpdateClientByName() {
		List<Client> clientList = new ArrayList<Client>();
		
		try {
			client = new Client(null, "Kevin", "Bacon", "123 Main St.", "1-614-322-5656", "kevin@Bacon.com", "n");
			clientList.add(client);
			
			clientManager.addClient(client);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Client updatedClient = null;

		try {
			updatedClient = clientManager.setClientFullName(client, "no longer Kevin", "no longer Bacon");
			clientList.add(updatedClient);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Client newClient = clientManager.getClientById(client);
		
		assertTrue("Did not update Full Name", updatedClient != null);
		assertEquals("Entities are not the same" + client.toString() + " vs. " + newClient.toString(),
				client.getFirstName(), newClient.getFirstName());
		assertEquals("Entities are not the same" + client.toString() + " vs. " + newClient.toString(),
				client.getLastName(), newClient.getLastName());
		
		// clean up
		cleanUp(clientList);
	}
	
	@Test
	public void testUpdateClientAddress() {
		List<Client> clientList = new ArrayList<Client>();
		
		try {
			client = new Client(null, "Kevin", "Bacon", "123 Main St.", "1-614-322-5656", "kevin@Bacon.com", "n");
			clientList.add(client);
			
			clientManager.addClient(client);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Client updatedClient = null;

		try {
			updatedClient = clientManager.setClientAddress(client, "new Address!");
			clientList.add(updatedClient);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Client newClient = clientManager.getClientById(client);
		
		assertTrue("Did not update Address", updatedClient != null);
		assertEquals("Entities are not the same" + client.toString() + " vs. " + newClient.toString(),
				client.getAddress(), newClient.getAddress());
		
		// clean up
		cleanUp(clientList);
	}
	
	@Test
	public void testUpdateClientPhone() {
		List<Client> clientList = new ArrayList<Client>();
		
		try {
			client = new Client(null, "Kevin", "Bacon", "123 Main St.", "1-614-322-5656", "kevin@Bacon.com", "n");
			clientList.add(client);
			
			clientManager.addClient(client);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Client updatedClient = null;

		try {
			updatedClient = clientManager.setClientPhoneNumber(client, "1-111-111-1111");
			clientList.add(updatedClient);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Client newClient = clientManager.getClientById(client);
		
		assertTrue("Did not update Phone", updatedClient != null);
		assertEquals("Entities are not the same" + client.toString() + " vs. " + newClient.toString(),
				client.getPhoneNumber(), newClient.getPhoneNumber());
		
		// clean up
		cleanUp(clientList);
	}
	
	@Test
	public void testUpdateClientEmail() {
		List<Client> clientList = new ArrayList<Client>();
		
		try {
			client = new Client(null, "Kevin", "Bacon", "123 Main St.", "1-614-322-5656", "kevin@Bacon.com", "n");
			clientList.add(client);
			
			clientManager.addClient(client);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Client updatedClient = null;

		try {
			updatedClient = clientManager.setClientEmail(client, "new Email!!!");
			clientList.add(updatedClient);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Client newClient = clientManager.getClientById(client);
		
		assertTrue("Did not update Email", updatedClient != null);
		assertEquals("Entities are not the same" + client.toString() + " vs. " + newClient.toString(),
				client.getEmail(), newClient.getEmail());
		
		// clean up
		cleanUp(clientList);
	}
	
	@Test
	public void testUpdateClientAutoRemind() {
		List<Client> clientList = new ArrayList<Client>();
		
		try {
			client = new Client(null, "Kevin", "Bacon", "123 Main St.", "1-614-322-5656", "kevin@Bacon.com", "n");
			clientList.add(client);
			
			clientManager.addClient(client);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Client updatedClient = null;

		try {
			updatedClient = clientManager.setClientAutoRemind(client, "Y");
			clientList.add(updatedClient);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Client newClient = clientManager.getClientById(client);
		
		assertTrue("Did not update autoRemind", updatedClient != null);
		assertEquals("Entities are not the same" + client.toString() + " vs. " + newClient.toString(),
				client.getAutoRemind(), newClient.getAutoRemind());
		
		// clean up
		cleanUp(clientList);
	}
	
	@Test
	public void testUpdateClientAutoRemindInvalid() {
		List<Client> clientList = new ArrayList<Client>();
		
		try {
			client = new Client(null, "Kevin", "Bacon", "123 Main St.", "1-614-322-5656", "kevin@Bacon.com", "n");
			clientList.add(client);
			
			clientManager.addClient(client);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Client updatedClient = null;

		boolean exceptionCaught = false;
		try {
			updatedClient = clientManager.setClientAutoRemind(client, "Bad Data");
			clientList.add(updatedClient);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			exceptionCaught = true;
		}
		
		Client newClient = clientManager.getClientById(client);
		
		assertTrue("Did not fail bad data autoRemind", exceptionCaught);
		
		// clean up
		cleanUp(clientList);
	}
	
	@Test
	public void testFindClientByPhoneNumber() {
		List<Client> clientList = new ArrayList<Client>();
		
		try {
			client = new Client(null, "Kevin", "Bacon", "123 Main St.", "1-614-322-5656", "kevin@Bacon.com", "n");
			clientList.add(client);
			
			clientManager.addClient(client);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Client foundClient = null;

		try {
			foundClient = clientManager.getClientByPhoneNumber(client);
			clientList.add(foundClient);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		assertEquals("Did not find client by phone number", client, foundClient);
		
		// clean up
		cleanUp(clientList);
	}
	
	@Test
	public void testFindClientByEmail() {
		List<Client> clientList = new ArrayList<Client>();
		
		try {
			client = new Client(null, "Kevin", "Bacon", "123 Main St.", "1-614-322-5656", "kevin@Bacon.com", "n");
			clientList.add(client);
			
			clientManager.addClient(client);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Client foundClient = null;

		try {
			foundClient = clientManager.getClientByEmail(client);
			clientList.add(foundClient);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		assertEquals("Did not find client by email", client, foundClient);
		
		// clean up
		cleanUp(clientList);
	}
	
	
	
	@Test
	public void testDeleteClientByName() {
		List<Client> clientList = new ArrayList<Client>();
		
		Client deleteMe1 = new Client(null, "Kevin", "Bacon", "123 Main St.", "1-614-322-5656", "kevin@Bacon.com", "n");
		clientList.add(deleteMe1);
		clientManager.addClient(deleteMe1);
		
		Client deletedClient = null;
		try {
			deleteMe1.setClientId(clientManager.getClientByName(deleteMe1).getClientId());
			deletedClient = clientManager.DeleteClientByName(deleteMe1);
			clientList.add(deletedClient);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}
		
		// try to get deleted photographer
		Client newClient = clientManager.getClientByName(deleteMe1);
		if (newClient != null) {
			clientList.add(newClient);
		}
		
		assertTrue("Did not Delete Client ", newClient == null);
		assertEquals("Wrong return from delete", deleteMe1, deletedClient);
		
		// clean up
		cleanUp(clientList);
	}
	
	@Test
	public void testDeleteClientById() {
		List<Client> clientList = new ArrayList<Client>();
		
		Client deleteMe1 = new Client(null, "Kevin", "Bacon", "123 Main St.", "1-614-322-5656", "kevin@Bacon.com", "n");
		clientList.add(deleteMe1);
		clientManager.addClient(deleteMe1);
		
		Client deletedClient = null;
		try {
			deleteMe1.setClientId(clientManager.getClientById(deleteMe1).getClientId());
			deletedClient = clientManager.DeleteClientById(deleteMe1);
			clientList.add(deletedClient);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}
		
		// try to get deleted photographer
		Client newClient = clientManager.getClientById(deleteMe1);
		if (newClient != null) {
			clientList.add(newClient);
		}
		
		assertTrue("Did not Delete Client ", newClient == null);
		assertEquals("Wrong return from delete", deleteMe1, deletedClient);
		
		// clean up
		cleanUp(clientList);
	}
	
	private void openSession() {
		session = HibernateUtil.getSessionFactory().openSession();
	}
	
	private void closeSession() {
		session.close();
	}
	
	private void cleanUp(List<Client> clientList) {
		// TODO Auto-generated method stub
		Iterator<Client> clientIterator = clientList.iterator();
		Client temp;
		while (clientIterator.hasNext()) {
			temp = clientIterator.next();
			try {
				clientManager.DeleteClientByName(temp);
			}
			catch (Exception e) {
				// do nothing
			}
		}
	}
}
