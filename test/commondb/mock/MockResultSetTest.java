package commondb.mock;

import static org.junit.Assert.*;

import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class MockResultSetTest {
	private ResultSet rs;
	
	@Before
	public void loadData() throws Exception {
		rs = new MockResultSet();
		((MockResultSet)rs).loadCSV(new InputStreamReader(MockResultSetTest.class.getResourceAsStream("resources/data.csv")));
	}
	
	@After
	public void afterTest() throws Exception {
		rs.close();
	}

	@Test
	public void testMoveFirst() throws Exception {
		assertTrue(rs.first());
		assertFalse(rs.previous());
	}

	@Test(expected=SQLException.class)
	public void testNoRow() throws Exception {
		rs.getString(1);
	}
	
	@Test
	public void testFirstRow() throws Exception {
		rs.first();
		assertEquals(1, rs.getInt("id"));
		assertEquals(-30, rs.getInt("balance"));
	}
	
	@Test
	public void testDoubleValue() throws Exception {
		rs.next(); rs.next(); rs.next();
		assertEquals(-1.4, rs.getDouble("balance"));
	}
	
	@Test
	public void testMoveLast() throws Exception {
		 assertTrue(rs.last());
		 assertFalse(rs.next());
	}

	@Test
	public void testLastRow() throws Exception {
		assertTrue(rs.last());
		assertEquals(0, rs.getDouble("balance"));
		assertFalse(rs.next());
	}
	
	@Test
	public void testFullIteration() throws Exception {
		int id = 0;
		while (rs.next()) {
			id++;
			assertEquals(id, rs.getInt("id"));
		}
		
		assertEquals(11, id);
	}
}
