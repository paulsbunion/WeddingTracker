package test.java.com.defrainphoto.weddingtracker.test;

import static org.junit.Assert.*;

import org.junit.Test;

public class simpleTest {
	@Test
	public void testAddSuccess() {
		assertEquals("Match", 5, 2 + 3);
	}
	
	@Test
	public void testAddFailure() {
		assertNotEquals("5 != " + (2+2), 5, 2 + 2);
	}
}
