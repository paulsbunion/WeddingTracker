package test.java.com.defrainphoto.weddingtracker.test;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import main.java.com.defrainphoto.weddingtracker.model.HibernateUtil;
import main.java.com.defrainphoto.weddingtracker.model.TimeChunk;
import main.java.com.defrainphoto.weddingtracker.model.TimeChunkManager;

public class TimeChunkManagerTest {
	
	TimeChunkManager chunkManager;
	
	@Before
	public void setUp() {
		chunkManager = new TimeChunkManager();
	}
	
//	@Test
//	public void testAddTimeChunk() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void testGetChunkByDescriptionAndTimeline() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void testGetChunkByIdAndTimeline() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void testSetPhotographer() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void testSetClient() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void testSetDescription() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void testSetDuration() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void testSetLocation() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void testSetPosition() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void testSetStartTime() {
//		fail("Not yet implemented");
//	}

}
